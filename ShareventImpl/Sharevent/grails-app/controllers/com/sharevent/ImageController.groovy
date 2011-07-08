package com.sharevent

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.MemoryCacheImageOutputStream
import grails.plugins.springsecurity.Secured

class ImageController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def index = {
        redirect(action: "list", params: params)
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [imageInstanceList: Image.list(params), imageInstanceTotal: Image.count()]
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def create = {
        def imageInstance = new Image()
        imageInstance.properties = params
        return [imageInstance: imageInstance]
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def save = {
        def imageInstance = new Image(params)
        if (imageInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'image.label', default: 'Image'), imageInstance.id])}"
            redirect(action: "show", id: imageInstance.id)
        }
        else {
            render(view: "create", model: [imageInstance: imageInstance])
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def show = {
        def imageInstance = Image.get(params.id)
        if (!imageInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'image.label', default: 'Image'), params.id])}"
            redirect(action: "list")
        }
        else {
            [imageInstance: imageInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def edit = {
        def imageInstance = Image.get(params.id)
        if (!imageInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'image.label', default: 'Image'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [imageInstance: imageInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def update = {
        def imageInstance = Image.get(params.id)
        if (imageInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (imageInstance.version > version) {
                    
                    imageInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'image.label', default: 'Image')] as Object[], "Another user has updated this Image while you were editing")
                    render(view: "edit", model: [imageInstance: imageInstance])
                    return
                }
            }
            imageInstance.properties = params
            if (!imageInstance.hasErrors() && imageInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'image.label', default: 'Image'), imageInstance.id])}"
                redirect(action: "show", id: imageInstance.id)
            }
            else {
                render(view: "edit", model: [imageInstance: imageInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'image.label', default: 'Image'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def delete = {
        def imageInstance = Image.get(params.id)
        if (imageInstance) {
            try {
                imageInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'image.label', default: 'Image'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'image.label', default: 'Image'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'image.label', default: 'Image'), params.id])}"
            redirect(action: "list")
        }
    }

    // *************** CUSTOM ACTIONS FOLLOW *******************
	def viewImageThumbnail = {

		synchronized(this.getClass()) {	
			def image = Image.get(params.id)
				try {
					// synchronizing because else this lead to problems when many images were loaded concurrently
					// TODO analyse this code properly to minimize the synchronized block
					// XXX this might be another bottleneck
					BufferedImage bufferedImage = null
						File file = new File("")
						// XXX use the thumbnail path instead of the image path
						String imageDBPath = "${grailsApplication.config.sharevent.imageDBPath}"
						String imagePath = imageDBPath + image.imageSet.galleryUser.id + "/" + Long.toString(image.id) + ".jpg"
						// TODO lose assumption that we are dealing with JPEGs!

						// TODO use asceticImages for high-quality scaling
						// TODO scale and cache images on upload or with an asynchronous job
						// scale the images before sending them
						def maxImageHeight = grailsApplication.config.sharevent.maxImageHeight as int

						def imageFile = new File(imagePath.toString())
						BufferedImage bsrc = ImageIO.read(imageFile)

						// the image could not be read. probably a wrong type to begin with.
						// delete it
						if(bsrc == null) {
							log.error "Could not read an image. Deleting it. Image.id== " + image.id
								if(imageFile.exists()) {
									if(imageFile.canWrite()) {
										if(!imageFile.isDirectory()) {
											imageFile.delete()
										}
									}
								}
							image.delete(flush: true)
								return
						}


					if(bsrc.getHeight() > maxImageHeight) {
						int height = maxImageHeight 
							int width = ((double)bsrc.getWidth()) * ((double)height)/((double)bsrc.getHeight())
							BufferedImage bdest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
						Graphics2D g = bdest.createGraphics();
						AffineTransform at = AffineTransform.getScaleInstance((double)width/bsrc.getWidth(), (double)height/bsrc.getHeight());
						g.drawRenderedImage(bsrc,at);
						ImageIO.write(bdest, "JPG", new MemoryCacheImageOutputStream(response.outputStream))
							return
					}
					else {
						ImageIO.write(bsrc, "JPG", new MemoryCacheImageOutputStream(response.outputStream))
							return
					}
				}
			catch(javax.imageio.IIOException ioEx) {
				// delete the image if the file cannot be read
				image.delete(flush: true)
					log.error "Some images could not be read and have been deleted from the database: Image.id " + image.id + " User.id == " + image.imageSet.galleryUser.id
			}
			catch(IOException e) {
				log.error "Caught an IOException: " + e.toString()
			}
			catch(Exception e) {
				log.error "Caught an exception: " + e.toString()
			}
			catch(Throwable t) {
				log.error "Caught a throwable: " + t
			}
			catch(e) {
				log.error "Caught an e: " + e.toString()
			}
		}
	}
}


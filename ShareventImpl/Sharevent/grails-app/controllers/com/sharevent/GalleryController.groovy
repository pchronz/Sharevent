package com.sharevent

import java.util.zip.ZipOutputStream
import org.apache.tools.ant.taskdefs.Zip
import java.util.zip.ZipEntry
import org.springframework.http.HttpStatus
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest
import uk.co.desirableobjects.ajaxuploader.AjaxUploaderService
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import javax.imageio.ImageIO 
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.MemoryCacheImageOutputStream
import grails.plugins.springsecurity.Secured


class GalleryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    // TODO get rid of the GPL code for imageUpload
	def imageDBService


    @Secured(['IS_AUTHENTICATED_FULLY'])
    def index = {
        redirect(action: "list", params: params)
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def list = {
	params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [galleryInstanceList: Gallery.list(params), galleryInstanceTotal: Gallery.count()]
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def create = {
        def galleryInstance = new Gallery()
        galleryInstance.properties = params
        return [galleryInstance: galleryInstance]
    }

	@Secured(['IS_AUTHENTICATED_FULLY'])
    def save = {
        def galleryInstance = new Gallery(params)
        if (galleryInstance.save(flush: true)) {
		flash.message = "${message(code: 'default.created.message', args: [message(code: 'gallery.label', default: 'Gallery'), galleryInstance.id])}"
		redirect(action: "show", id: galleryInstance.id)
        }
        else {
            render(view: "create", model: [galleryInstance: galleryInstance])
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def show = {
        def galleryInstance = Gallery.get(params.id)
        if (!galleryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'gallery.label', default: 'Gallery'), params.id])}"
            redirect(action: "list")
        }
        else {
            [galleryInstance: galleryInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def edit = {
        def galleryInstance = Gallery.get(params.id)
        if (!galleryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'gallery.label', default: 'Gallery'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [galleryInstance: galleryInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def update = {
        def galleryInstance = Gallery.get(params.id)
        if (galleryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (galleryInstance.version > version) {
                    
                    galleryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'gallery.label', default: 'Gallery')] as Object[], "${message(code: 'userDef.parallelAccess')}")
                    render(view: "edit", model: [galleryInstance: galleryInstance])
            "${message(code: 'default.created.message', args: [message(code: 'gallery.label', default: 'Gallery'), galleryInstance.id])}"
                    return
                }
            }
            galleryInstance.properties = params
            if (!galleryInstance.hasErrors() && galleryInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'gallery.label', default: 'Gallery'), galleryInstance.id])}"
                redirect(action: "show", id: galleryInstance.id)
            }
            else {
                render(view: "edit", model: [galleryInstance: galleryInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'gallery.label', default: 'Gallery'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def delete = {
        def galleryInstance = Gallery.get(params.id)
        if (galleryInstance) {
            try {
                galleryInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'gallery.label', default: 'Gallery'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'gallery.label', default: 'Gallery'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'gallery.label', default: 'Gallery'), params.id])}"
            redirect(action: "list")
        }
    }

    // ********* OWN ACTIONS BELOW *******

    def view = {
		println params
        def galleryInstance = Gallery.get(params.id)

		if(galleryInstance == null) {
			redirect(controller: 'main')
			return
		}

		if(params.containsKey('key')) {
			if(galleryInstance.adminKey == params.key) {
				def user = GalleryUser.get(params.key)
				if(user) {
					session.user = user
				}
			}
		}
		
		// since we cannot use the AWS service in the gsp, we need to create the links
		// to the images in S3 here and associate the images with those
		def urls = [:]
		galleryInstance.contributors?.each { contributor ->
			contributor.imageSet.images?.each{ image ->
				// TODO use the config for reading the bucket name
				def url = imageDBService.getImageURL(image)
				urls[image.id.toString()] = url
			}
		}

		println 'About to render following images: ' + urls

        if(galleryInstance)
            render(view:"view", model:[galleryInstance:galleryInstance, urls: urls])
        else
            redirect(controller:"main")
    }

    def viewExample = {
	
    }

    def createFree = {
        render(view: 'createFree', model: [galleryInstance: params.galleryInstance])
    }

	def share = {
		def gallery = new Gallery(params)
		gallery.adminKey = 0


		if(!gallery.validate()) {
			render(view: 'createFree', model: [galleryInstance: gallery])
			log.error 'Could not validate the gallery'
			return
		}

		// actually also create a user
		def user = new GalleryUser()
		user.firstName = params.creatorFirstName
		user.lastName = params.creatorLastName
		user.email = params.creatorEmail

		user.imageSet = new ImageSet()
		user.imageSet.galleryUser = user
		gallery.addToContributors(user)
		user.contributedGallery = gallery
		if(!gallery.save(flush:true)) {
			log.error "Could not create a new gallery. Errors follow."
			gallery.errors.each{
				log.error it
			}
			redirect(action: 'createFree', params: [galleryInstance: gallery])
			return
		}

		// set the gallery's key to the user's id
		gallery.adminKey = user.id

		session.user = user

		render(view:"share", model:[galleryInstance:gallery])
	}

    def download = {
    	// what happens if someone tries to upload and someone else tries to download at the same time?
		// might not happen ever; even then only images would not be uploaded properly. no big deal!...?

        // first get the selected image ids
        def imageIds = []
        params.each {
            if(it.key instanceof java.lang.String) {
                if(it.key.startsWith("image")) {
                    // Assuming that Grails is providing only selected checkboxes!
                    String imageId = it.key
                    imageIds.add(imageId.split("_")[1])
                }
            }
        }

			if(imageIds.size() == 0) {
				redirect(action: 'view', params: [id: params.id])
				return
			}
			else {
				// get the corresponding images
				def images = []
				imageIds.each { imageId ->
					images.add(Image.get(imageId))
			}

            // now zip the selected images to the response stream
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(response.outputStream))
            def data = new byte[2048]
            def galleryInstance = Gallery.get(params.id)
            images.each { image ->
				def inputStream = imageDBService.getImageInputStream(image)

				if (inputStream == null) {
					log.error "S3InputStream is null"
				}

				BufferedInputStream origin = new BufferedInputStream(inputStream, 2048)
				String zipPath = image.imageSet.galleryUser.contributedGallery.title + '/' + image.id + '.jpg'
				ZipEntry entry = new ZipEntry(zipPath)
				zos.putNextEntry(entry)
				int count
				while((count = origin.read(data, 0, 2048)) != -1) {
				   zos.write(data, 0, count);
				}
				origin.close();
            }
            zos.close()
		}
    }

    def deleteImages = {
		// TODO secure this action for the admin user
        // first get the selected image ids
        def images = []
		log.error 'getting ids for images to delete'
        params.each {
            if(it.key instanceof java.lang.String) {
                if(it.key.startsWith("image")) {
                    // Assuming that Grails is providing only selected checkboxes!
                    String imageId = it.key
                    images.add(imageId.split("_")[1])
                }
            }
        }

		// then delete the images from storage
		log.error 'deleting images from storage'
		images.each {
            Image image = Image.get(it.toString().toLong())
			imageDBService.delete(image)
		}

        // remove the corresponding entries in the database
		log.error 'removing image instances from db'
        images.each {
            Image image = Image.get(it.toString().toLong())
            image.imageSet.removeFromImages(image)
        }

        // if some user does not have anymore images, delete the user as well
		log.error 'deleting users who do not have any more images'
        def galleryInstance = Gallery.get(params.id)
        def contributorsCopy = []
        contributorsCopy += galleryInstance.contributors
        contributorsCopy.each {
            if(it.imageSet.images.size() == 0 ) {
				if(it.id != galleryInstance.adminKey) {
					log.debug 'Removing user after deleting all of his images. user.id==' + it.id
					galleryInstance.removeFromContributors(it)
					it.delete(flush:true)
				}
				else {
					log.debug 'Keeping user since it is the gallery admin'
				}
            }
        }

        flash.message = "${message(code: 'userDef.deletedImages', args: [images.size()])}"


        // show the gallery again
        redirect(controller: "gallery", action: "view", params: [id: params.id])
    }

    def deleteGallery = {
		// also delete all stored images and thumbnails
		Gallery.get(params.id).contributors.each {
			it.imageSet.images.each {
				imageDBService.delete(it)
			}
		}

    	// TODO secure this action for the admin user
        Gallery.get(params.id).delete(flush: true)



        flash.message = "${message(code: 'userDef.galleryDeleted')}"

        redirect(controller: "main", action: "index")
    }

    def contributeImages = {

		// check if the user is logged in
		if(!session.user) {
			log.error "User not logged in! Redirecting to user creation."
			redirect(controller: 'galleryUser', action: 'createNew', params: [id: params.id])
			return
		}
		else {
			def loggedInUser = session.user
			if(loggedInUser.contributedGallery.id != params.id) {
				session.user = null
				redirect(controller: 'galleryUser', action: 'createNew', params: [id: params.id])
				return
			}
		}

		def gallery = Gallery.get(params.id)
		render(view: 'contributeImages', model: [galleryInstance: gallery])
    }

    def uploadImage = {
	synchronized(this.getClass()) {
		if(session.user == null) {
			flash.message = "${message(code: 'userDef.expiredSession')}"
			redirect(controller: 'main')
			println 'No user to upload image for. Redirecting.'
			return
		}

		def user = session.user.merge(flush: true)
    	try {
			println 'Creating new Image instance' 
			def image = new Image()
			// locking image set to prevent conflicts due to optimistic locking
			// when upload multiple files at once
			// HSQLDB does not support pessimistic locking ==> synchronizing the whole procedure
			// this might be one of the first bottlenecks!
			// XXX BOTTLENECK!
	        Gallery galleryInstance = user.contributedGallery
			user.imageSet.addToImages(image)
	        if(!galleryInstance.contributors.contains(user)) {
                	galleryInstance.addToContributors(user)
	        }
			if(!galleryInstance.save(flush: true)) {
				log.error 'Errors while saving gallerInstance'
				println 'Error while saving Gallery with new image.' 
				galleryInstance.errors.each {
					// TODO use log4j for logging everywhere
					log.error it
					println it 
				}
			}

			InputStream inputStream = null
			if (request instanceof MultipartHttpServletRequest) {
					MultipartFile uploadedFile = ((MultipartHttpServletRequest) request).getFile('qqfile')
					inputStream = uploadedFile.inputStream
			} 
			else {
				// need to do this for integration tests
				try {
					MultipartFile uploadedFile = request.getFile('qqfile')
					inputStream = uploadedFile.inputStream
				}
				catch(e) {
					inputStream = request.inputStream
				}
			}


			// read the image from inputstream
			// if it does not work, post a flash message, log it and remove the image domain class instance

			BufferedImage bsrc = ImageIO.read(inputStream)

			// the image could not be read. probably a wrong type to begin with.
			// delete it
			if(bsrc == null) {
				log.error "Could not read an image. Deleting it. Image.id== " + image.id
				image.imageSet.removeFromImages(image)
				flash.message = "${message(code: 'userdef.couldNotReadImage')}"
				render(text: [success: false] as JSON, contentType: 'text/JSON')
				return
			}

			int maxImageHeight = grailsApplication.config.sharevent.maxImageHeight
			int maxImageWidth = grailsApplication.config.sharevent.maxImageWidth

			// resize the images
			def bais = null
			if(bsrc.getHeight() > maxImageHeight) {
				int height = maxImageHeight 
				int width = ((double)bsrc.getWidth()) * ((double)height)/((double)bsrc.getHeight())
				BufferedImage bdest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = bdest.createGraphics();
				AffineTransform at = AffineTransform.getScaleInstance((double)width/bsrc.getWidth(), (double)height/bsrc.getHeight());
				g.drawRenderedImage(bsrc,at);
				def baos = new ByteArrayOutputStream()
				ImageIO.write(bdest, "JPG", new MemoryCacheImageOutputStream(baos))
				def byteArray = baos.toByteArray()
				bais = new ByteArrayInputStream(byteArray)
			}
			else {
				def baos = new ByteArrayOutputStream()
				ImageIO.write(bsrc, "JPG", new MemoryCacheImageOutputStream(baos))
				def byteArray = baos.toByteArray()
				bais = new ByteArrayInputStream(byteArray)
			}

			// uploading the scaled image
			imageDBService.storeImageThumbnail(bais, image, user)

			// uploading the original image
			def baos = new ByteArrayOutputStream()
			ImageIO.write(bsrc, "JPG", new MemoryCacheImageOutputStream(baos))
			def byteArray = baos.toByteArray()
			bais = new ByteArrayInputStream(byteArray)
			imageDBService.storeImage(bais, image, user)
		}
		catch(Exception e) {
			e.printStackTrace()
			log.error e.toString()
			render(text: [success: false] as JSON, contentType: 'text/JSON')
			return
		}
		catch(e) {
			log.error e.toString()
			render(text: [success: false] as JSON, contentType: 'text/JSON')
			return
		}
		render(text: [success: true] as JSON, contentType: 'text/JSON')
		return
		}
    }

    def logout ={
		session.user = null
		redirect(action: "view", params: params)
    }

}

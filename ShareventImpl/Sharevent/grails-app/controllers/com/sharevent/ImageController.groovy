package com.sharevent

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.MemoryCacheImageOutputStream

class ImageController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [imageInstanceList: Image.list(params), imageInstanceTotal: Image.count()]
    }

    def create = {
        def imageInstance = new Image()
        imageInstance.properties = params
        return [imageInstance: imageInstance]
    }

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
    def viewImage = {
        def image = Image.get(params.id)

        BufferedImage bufferedImage = null
        try {
            File file = new File("")
            // TODO read image-path from configuration file
            String imageDBPath = "${grailsApplication.config.sharevent.imageDBPath}"
            String imagePath = imageDBPath + Long.toString(image.imageSet.id) + "/" + Long.toString(image.id) + ".jpg"
            // TODO lose assumption that we are dealing with JPEGs!

	    // TODO use asceticImages for high-quality scaling
	    // TODO scale and cache images on upload or with an asynchronous job
	    // scale the images before sending them
	    def maxImageHeight = grailsApplication.config.sharevent.maxImageHeight as int

	    BufferedImage bsrc = ImageIO.read(new File(imagePath.toString()));
	    
	    if(bsrc.getHeight() > maxImageHeight) {
		    int height = maxImageHeight 
		    int width = ((double)bsrc.getWidth()) * ((double)height)/((double)bsrc.getHeight())
		    BufferedImage bdest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		    Graphics2D g = bdest.createGraphics();
		    AffineTransform at = AffineTransform.getScaleInstance((double)width/bsrc.getWidth(), (double)height/bsrc.getHeight());
		    g.drawRenderedImage(bsrc,at);
		    ImageIO.write(bdest, "JPG", new MemoryCacheImageOutputStream(response.outputStream))
	    }
	    else {
	    	ImageIO.write(bsrc, "JPG", new MemoryCacheImageOutputStream(response.outputStream))
	    }
        }
	catch(javax.imageio.IIOException ioEx) {
		// delete the image if the file cannot be read
		image.delete(flush: true)
		log.error "Some images could not be read and have been deleted from the database: Image.id " + image.id + " User.id == " + image.imageSet.galleryUser.id
	}
        catch(IOException e) {
             log.error e.printStackTrace()
        }
        finally {
            // TODO show a standard error picture
            // TODO log the error, for later analysis
        }
    }
}

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

class GalleryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    // TODO get rid of the GPL code for imageUpload
    AjaxUploaderService ajaxUploaderService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [galleryInstanceList: Gallery.list(params), galleryInstanceTotal: Gallery.count()]
    }

    def create = {
        def galleryInstance = new Gallery()
        galleryInstance.properties = params
        return [galleryInstance: galleryInstance]
    }

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

    def update = {
        def galleryInstance = Gallery.get(params.id)
        if (galleryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (galleryInstance.version > version) {
                    
                    galleryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'gallery.label', default: 'Gallery')] as Object[], "Another user has updated this Gallery while you were editing")
                    render(view: "edit", model: [galleryInstance: galleryInstance])
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
        // TODO provide an error message if a gallery does not exist

        def galleryInstance = Gallery.get(params.id)
        if(params.containsKey("key")) {
            try {
                if(params.key.toString().toInteger() == galleryInstance.adminKey) {
                    session.isAdmin = true
                    session.galleryId = galleryInstance.id
		    session.isLoggedIn = true
                }
            }
            catch(Exception e) {
                e.printStackTrace()
            }
        }

        if(galleryInstance)
            render(view:"view", model:[galleryInstance:galleryInstance])
        else
            redirect(controller:"main")
    }

    def createFree = {

    }

    def share = {
        // TODO the creator user does not exist anymore, read the properties directly
        def gallery = new Gallery(params)
        gallery.adminKey = new Random(System.currentTimeMillis()).nextInt()
        gallery.creatorFirstName = params.firstName
        gallery.creatorLastName = params.lastName
        gallery.creatorEmail = params.email
        gallery.save(flush:true)

	// login the creator as admin
	session.isAdmin = true
	session.galleryId = gallery.id
	session.isLoggedIn = true

	// actually also create a user
	def user = new GalleryUser(params)
	user.imageSet = new ImageSet()
	user.imageSet.galleryUser = user
	gallery.addToContributors(user)
	user.contributedGallery = gallery
	if(!gallery.save(flush:true)) {
		gallery.errors.each{
			println it
		}
	}

	session.userId = user.id

        render(view:"share", model:[galleryInstance:gallery])
    }

    def download = {

        // first get the selected image ids
        def images = []
        params.each {
            if(it.key instanceof java.lang.String) {
                if(it.key.startsWith("image")) {
                    // Assuming that Grails is providing only selected checkboxes!
                    String imageId = it.key
                    images.add(imageId.split("_")[1])
                }
            }
        }


        // then build the paths for the image ids
        def imagePaths = []
        images.each {
	    def userId = Image.get(it.toLong()).imageSet.galleryUser.id
            String imagePath = "${grailsApplication.config.sharevent.imageDBPath}" + userId + "/" + it + ".jpg"
            imagePaths.add(imagePath)
        }

        // now zip the selected images to the response stream
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(response.outputStream))
        def data = new byte[2048]
        def galleryInstance = Gallery.get(params.id)
        imagePaths.each { imagePath ->
            // check whether the gallery's path also works on windows
            String imageId = imagePath.split("/")[-1]
            String zipPath = galleryInstance.title + "/" + imageId
            FileInputStream fi = new FileInputStream(imagePath)
            BufferedInputStream origin = new BufferedInputStream(fi, 2048)
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

    def deleteImages = {
        // first get the selected image ids
        def images = []
        params.each {
            if(it.key instanceof java.lang.String) {
                if(it.key.startsWith("image")) {
                    // Assuming that Grails is providing only selected checkboxes!
                    String imageId = it.key
                    images.add(imageId.split("_")[1])
                }
            }
        }

        // remove the corresponding entries in the database
        images.each {
            Image image = Image.get(it.toString().toLong())
            image.imageSet.removeFromImages(image)
        }

        // if some user does not have anymore images, delete the user as well
        def galleryInstance = Gallery.get(params.id)
        def contributorsCopy = []
        contributorsCopy += galleryInstance.contributors
        contributorsCopy.each {
            if(it.imageSet.images.size() == 0) {
                galleryInstance.removeFromContributors(it)
                it.delete(flush:true)
            }
        }


        // then build the paths for the image ids
        def imagePaths = []
        images.each {
            String imagePath = "${grailsApplication.config.sharevent.imageDBPath}" + params.id + "/" + it + ".jpg"
            imagePaths.add(imagePath)
        }

        // TODO finally delete the images from disk

        flash.message = images.size() + " images have been deleted."


        // show the gallery again
        redirect(controller: "gallery", action: "view", params: [id: params.id])
    }

    def deleteGallery = {
        Gallery.get(params.id).delete(flush: true)

        flash.message = "The gallery has been deleted. Would you maybe like to create a new one?"

        redirect(controller: "main", action: "index")
    }

    def contributeImages = {
        def galleryInstance = Gallery.get(params.id)
        render(view: 'contributeImages', model: [galleryInstance: galleryInstance])
    }

    def uploadImage = {
	def user = GalleryUser.get(session.userId)
	Gallery galleryInstance = user.contributedGallery
	def image = new Image()
        user.imageSet.addToImages(image)
	if(!galleryInstance.contributors.contains(user)) {
        	galleryInstance.addToContributors(user)
	}
        if(!galleryInstance.save(flush: true)) {
	    log.error 'Errors while saving gallerInstance'
            galleryInstance.errors.each {
                // TODO use log4j for logging everywhere
                log.error it
            }
        }

	// create the directory for this user in our image database if it does not exist yet
        if(!grailsApplication.config.sharevent.containsKey('imageDBPath')) {
		// TODO throw an exception
		log.error "Path to image DB not found in configuration!"
		render(text: [success: false] as JSON, contentType: 'text/JSON')
	}
        File dir = new File("${grailsApplication.config.sharevent.imageDBPath}" + user.id + "/" )
        if(!dir.exists()) {
	    log.info 'Directory for user ${user.id} does not yet exist. Creating.'
            dir.mkdir()
        }

	// create the image file handle itself
	File imageFile = new File("${grailsApplication.config.sharevent.imageDBPath}" + user.id + "/" + image.id + ".jpg")

	InputStream inputStream = null
	if (request instanceof MultipartHttpServletRequest) {
            MultipartFile uploadedFile = ((MultipartHttpServletRequest) request).getFile('qqfile')
            inputStream = uploadedFile.inputStream
        }
        else {
		inputStream = request.inputStream
	}

        ajaxUploaderService.upload(inputStream, imageFile)

	render(text: [success: true] as JSON, contentType: 'text/JSON')
    }

}

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


class GalleryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    // TODO get rid of the GPL code for imageUpload
    AjaxUploaderService ajaxUploaderService

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
        def galleryInstance = Gallery.get(params.id)

	if(params.containsKey('key')) {
	    if(galleryInstance.adminKey == params.key) {
		def user = GalleryUser.get(params.key)
		if(user) {
		    session.user = user
		}
            }
	}

        if(galleryInstance)
            render(view:"view", model:[galleryInstance:galleryInstance])
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

	if(images.size() == 0) {
		redirect(action: 'view', params: [id: params.id])
		return
	}
	else {

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
    }

    def deleteImages = {
	// TODO secure this action for the admin user
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
    	// TODO secure this action for the admin user
        Gallery.get(params.id).delete(flush: true)

        flash.message = "The gallery has been deleted. Would you maybe like to create a new one?"

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
	def user = session.user.merge(flush: true)
    	try {
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
		return
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

package com.sharevent

import java.util.zip.ZipOutputStream
import org.apache.tools.ant.taskdefs.Zip
import java.util.zip.ZipEntry
import org.springframework.http.HttpStatus
import uk.co.desirableobjects.ajaxuploader.AjaxUploaderService
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.Secured


class GalleryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    // TODO get rid of the GPL code for imageUpload
	def imageDBService
	def imageService
	def galleryService

    // ********* OWN ACTIONS BELOW *******


    //def createFree = {
	//	println  'create free'
    //    render(view: 'createFree', model: [galleryInstance: params.galleryInstance])
    //}

	//def share = {
	//	println  'share'
	//	
	//	// actually create a user
	//	def user = new GalleryUser()
	//	user.firstName = params.creatorFirstName
	//	user.lastName = params.creatorLastName
	//	user.email = params.creatorEmail
	//	if(!user.save(flush:true)) {
	//		println "Could not create a new user. Errors follow."
	//		user.errors.each{
	//			println it
	//		}
	//		redirect(action: 'createFree', params: [galleryInstance: gallery])
	//		return
	//	}

	//	// TODO check whether the user is already logged in
	//	// this makes sense once it is possible to create multiple galleries

	//	def gallery = new Gallery(params)
	//	gallery.creatorId = user.id
	//	gallery.addToUsers(user)
	//	user.addToGalleries gallery

	//	if(!gallery.save(flush:true)) {
	//		println "Could not create a new gallery. Errors follow."
	//		gallery.errors.each{
	//			println it
	//		}
	//		redirect(action: 'createFree', params: [galleryInstance: gallery])
	//		return
	//	}
	//	if(!user.save(flush:true)) {
	//		println "Could not create a new user. Errors follow."
	//		user.errors.each{
	//			println it
	//		}
	//		redirect(action: 'createFree', params: [galleryInstance: gallery])
	//		return
	//	}

	//	session.user = user

	//	render(view:"share", model:[galleryInstance:gallery])
	//}

    //def download = {
	//	println  'DOWNLOAD'
    //	// what happens if someone tries to upload and someone else tries to download at the same time?
	//	// might not happen ever; even then only images would not be uploaded properly. no big deal!...?

    //    // first get the selected image ids
    //    def imageIds = []
    //    params.each {
    //        if(it.key instanceof java.lang.String) {
    //            if(it.key.startsWith("image")) {
    //                // Assuming that Grails is providing only selected checkboxes!
    //                String imageId = it.key
    //                imageIds.add(imageId.split("_")[1])
    //            }
    //        }
    //    }

	//		if(imageIds.size() == 0) {
	//			redirect(action: 'view', params: [id: params.id])
	//			return
	//		}
	//		else {
	//			// get the corresponding images
	//			def images = []
	//			imageIds.each { imageId ->
	//				images.add(Image.get(imageId))
	//		}

    //        // now zip the selected images to the response stream
    //        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(response.outputStream))
    //        def data = new byte[2048]
    //        def galleryInstance = Gallery.get(params.id)
    //        images.each { image ->
	//			def inputStream = imageDBService.getImageInputStream(image)

	//			if (inputStream == null) {
	//				log.error "S3InputStream is null"
	//			}

	//			BufferedInputStream origin = new BufferedInputStream(inputStream, 2048)
	//			String zipPath = image.gallery.title + '/' + image.id + '.jpg'
	//			ZipEntry entry = new ZipEntry(zipPath)
	//			zos.putNextEntry(entry)
	//			int count
	//			while((count = origin.read(data, 0, 2048)) != -1) {
	//			   zos.write(data, 0, count);
	//			}
	//			origin.close();
    //        }
    //        zos.close()
	//	}
    //}

    //def deleteImages = {
	//	println  'DELETE IMAGES'
	//	// TODO secure this action for the admin user
    //    // first get the selected image ids
    //    def images = []
	//	log.error 'getting ids for images to delete'
    //    params.each {
    //        if(it.key instanceof java.lang.String) {
    //            if(it.key.startsWith("image")) {
    //                // Assuming that Grails is providing only selected checkboxes!
    //                String imageId = it.key
    //                images.add(imageId.split("_")[1])
    //            }
    //        }
    //    }

	//	// then delete the images from storage
	//	log.error 'deleting images from storage'
	//	images.each {
    //        Image image = Image.get(it.toString().toLong())
	//		imageDBService.delete(image)
	//	}

    //    // remove the corresponding entries in the database
	//	log.error 'removing image instances from db'
    //    images.each { image ->
    //        imageService.deleteImage(Image.get(image.toString().toLong()))
    //    }

    //    flash.message = "${message(code: 'userDef.deletedImages', args: [images.size()])}"


    //    // show the gallery again
    //    redirect(controller: "gallery", action: "view", params: [id: params.id])
    //}

    //def deleteGallery = {
	//	println  'DELETE GALLERY'
	//	galleryService.deleteGallery(params.id)

    //    flash.message = "${message(code: 'userDef.galleryDeleted')}"

    //    redirect(controller: "main", action: "index")
    //}

    //def contributeImages = {
	//	println  'CONTRIBUTE IMAGES'
	//	def gallery = Gallery.get(params.id)

	//	// check if the user is logged in
	//	if(!session.user) {
	//		log.error "User not logged in! Redirecting to user creation."
	//		redirect(controller: 'galleryUser', action: 'createNew', params: [id: params.id])
	//		return
	//	}
	//	else {
	//		def loggedInUser = session.user
	//		if(gallery.creatorId != loggedInUser.id) {
	//			session.user = null
	//			redirect(controller: 'galleryUser', action: 'createNew', params: [id: params.id])
	//			return
	//		}
	//	}

	//	render(view: 'contributeImages', model: [galleryInstance: gallery])
    //}

    //def uploadImage = {
	//	println  'UPLOAD IMAGES'
	//synchronized(this.getClass()) {
	//	if(session.user == null) {
	//		flash.message = "${message(code: 'userDef.expiredSession')}"
	//		redirect(controller: 'main')
	//		println 'No user to upload image for. Redirecting.'
	//		return
	//	}

	//	def user = session.user.merge(flush: true)
    //	try {
	//		println 'Creating new Image instance' 
	//		def image = new Image()
	//		// locking image set to prevent conflicts due to optimistic locking
	//		// when upload multiple files at once
	//		// HSQLDB does not support pessimistic locking ==> synchronizing the whole procedure
	//		// this might be one of the first bottlenecks!
	//		// XXX BOTTLENECK!
	//		// TODO update once multiple contributions are allowed
	//		if(user.galleries.size() > 1)
	//			throw new Exception('multiple galleries per user not yet implemented')
	//        Gallery galleryInstance = null
	//		user.galleries.each {
	//			galleryInstance = it
	//		}
	//		user.addToImages(image)
	//		galleryInstance.addToImages image
	//        if(!galleryInstance.users.contains(user)) {
    //            	galleryInstance.addToUsers(user)
	//        }
	//		if(!galleryInstance.save(flush: true)) {
	//			log.error 'Errors while saving gallerInstance'
	//			println 'Error while saving Gallery with new image.' 
	//			galleryInstance.errors.each {
	//				// TODO use log4j for logging everywhere
	//				log.error it
	//				println it 
	//			}
	//		}

	//		InputStream inputStream = null
	//		if (request instanceof MultipartHttpServletRequest) {
	//				MultipartFile uploadedFile = ((MultipartHttpServletRequest) request).getFile('qqfile')
	//				inputStream = uploadedFile.inputStream
	//		} 
	//		else {
	//			// need to do this for integration tests
	//			try {
	//				MultipartFile uploadedFile = request.getFile('qqfile')
	//				inputStream = uploadedFile.inputStream
	//			}
	//			catch(e) {
	//				inputStream = request.inputStream
	//			}
	//		}


	//		// read the image from inputstream
	//		// if it does not work, post a flash message, log it and remove the image domain class instance

	//		BufferedImage bsrc = ImageIO.read(inputStream)

	//		// the image could not be read. probably a wrong type to begin with.
	//		// delete it
	//		if(bsrc == null) {
	//			log.error "Could not read an image. Deleting it. Image.id== " + image.id
	//			imageService.deleteImage(image)
	//			flash.message = "${message(code: 'userdef.couldNotReadImage')}"
	//			render(text: [success: false] as JSON, contentType: 'text/JSON')
	//			return
	//		}

	//		int maxImageHeight = grailsApplication.config.sharevent.maxImageHeight
	//		int maxImageWidth = grailsApplication.config.sharevent.maxImageWidth

	//		// resize the images
	//		def bais = null
	//		if(bsrc.getHeight() > maxImageHeight) {
	//			int height = maxImageHeight 
	//			int width = ((double)bsrc.getWidth()) * ((double)height)/((double)bsrc.getHeight())
	//			BufferedImage bdest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	//			Graphics2D g = bdest.createGraphics();
	//			AffineTransform at = AffineTransform.getScaleInstance((double)width/bsrc.getWidth(), (double)height/bsrc.getHeight());
	//			g.drawRenderedImage(bsrc,at);
	//			def baos = new ByteArrayOutputStream()
	//			ImageIO.write(bdest, "JPG", new MemoryCacheImageOutputStream(baos))
	//			def byteArray = baos.toByteArray()
	//			bais = new ByteArrayInputStream(byteArray)
	//		}
	//		else {
	//			def baos = new ByteArrayOutputStream()
	//			ImageIO.write(bsrc, "JPG", new MemoryCacheImageOutputStream(baos))
	//			def byteArray = baos.toByteArray()
	//			bais = new ByteArrayInputStream(byteArray)
	//		}

	//		// uploading the scaled image
	//		imageDBService.storeImageThumbnail(bais, image, user)

	//		// uploading the original image
	//		def baos = new ByteArrayOutputStream()
	//		ImageIO.write(bsrc, "JPG", new MemoryCacheImageOutputStream(baos))
	//		def byteArray = baos.toByteArray()
	//		bais = new ByteArrayInputStream(byteArray)
	//		imageDBService.storeImage(bais, image, user)
	//	}
	//	catch(Exception e) {
	//		e.printStackTrace()
	//		log.error e.toString()
	//		render(text: [success: false] as JSON, contentType: 'text/JSON')
	//		return
	//	}
	//	catch(e) {
	//		log.error e.toString()
	//		render(text: [success: false] as JSON, contentType: 'text/JSON')
	//		return
	//	}
	//	render(text: [success: true] as JSON, contentType: 'text/JSON')
	//	return
	//	}
    //}

    //def logout = {
	//	println  'LOGOUT'
	//	session.user = null
	//	redirect(action: "view", params: params)
    //}

	//def createDirect = {
	//	// TODO check whether a user is logged in
	//	def user = null
	//	GalleryUser.findByFirstName('direct').each { gUser ->
	//		if(user != null)
	//			println 'There exist more than one incognito users!'
	//		user = gUser
	//	}
	//	if(!user.save(flush: true)) {
	//		user.errors.each { error ->
	//			println  error
	//		}
	//		flash.message = 'Something went wrong when you we tried to create a new gallery'
	//		redirect(controller: 'main')
	//		return
	//	}
	//	def gallery = new Gallery(date: new Date(), title: params['gallery_title'], location: 'direct', creatorId: user.id)
	//	user.addToGalleries gallery
	//	gallery.addToUsers user

	//	if(!user.save(flush: true)) {
	//		user.errors.each { error ->
	//			println  error
	//		}
	//		flash.message = 'Something went wrong when you we tried to create a new gallery'
	//		redirect(controller: 'main')
	//		return
	//	}

	//	if(!gallery.save(flush: true)) {
	//		gallery.errors.each { error ->
	//			println  error
	//		}
	//		flash.message = 'Something went wrong when you we tried to create a new gallery'
	//		redirect(controller: 'main')
	//		return
	//	}

	//	redirect(action: 'viewDirect', params: [id: gallery.id])
	//}

	//def viewDirect = {
	//	println  'VIEWDIRECT'
    //    def galleryInstance = Gallery.get(params.id)

	//	if(galleryInstance == null) {
	//		flash.message = 'Something went wrong when we tried to access your gallery'
	//		redirect(controller: 'main')
	//		return
	//	}

	//	// TODO check whether the key matches the creator id
	//	if(params.containsKey('key')) {
	//		if(galleryInstance.creatorId == params.key) {
	//			def user = GalleryUser.get(params.key)
	//			if(user) {
	//				session.user = user
	//			}
	//		}
	//	}
	//	
	//	// since we cannot use the AWS service in the gsp, we need to create the links
	//	// to the images in S3 here and associate the images with those
	//	def urls = [:]
	//	galleryInstance.images?.each { image ->
	//		def url = imageDBService.getImageURL(image)
	//		urls[image.id.toString()] = url
	//	}

	//	println 'About to render following images: ' + urls


    //    if(galleryInstance)
    //        render(view:"viewDirect", model:[galleryInstance:galleryInstance, urls: urls])
    //    else {
	//		flash.message = 'Something went wrong while we tried to access your gallery'
    //        redirect(controller:"main")
	//	}
	//}

	//def uploadImageDirect = {
	//	println  'UPLOAD IMAGES DIRECT'
	//synchronized(this.getClass()) {
	//	def image = null
	//	// TODO check whether the user is logged in

    //	try {
	//		println 'Creating new Image instance' 
	//		image = new Image()
	//		// locking image set to prevent conflicts due to optimistic locking
	//		// when upload multiple files at once
	//		// HSQLDB does not support pessimistic locking ==> synchronizing the whole procedure
	//		// this might be one of the first bottlenecks!
	//		// XXX BOTTLENECK!
	//		// TODO update once multiple contributions are allowed
	//        Gallery galleryInstance = Gallery.get(params.id)
	//		def user = null
	//		GalleryUser.findByFirstName('direct').each { gUser ->
	//			if(user != null)
	//				println  'There are more than one incognito users!'
	//			user = gUser
	//		}
	//		user.addToGalleries galleryInstance
	//		galleryInstance.addToUsers user

	//		//if(user.galleries.size() > 1)
	//		//	throw new Exception('multiple galleries per user not yet implemented')

	//		user.addToImages(image)
	//		galleryInstance.addToImages image

	//		if(!user.save(flush: true)) {
	//			user.errors.each {
	//				println  it
	//			}
	//			flash.message = 'something went wrong while uploading your images'
	//			redirect(controller:'main')
	//			return
	//		}
	//		if(!galleryInstance.save(flush: true)) {
	//			println 'Error while saving Gallery with new image.' 
	//			galleryInstance.errors.each {
	//				println it 
	//			}
	//			flash.message = 'something went wrong while uploading your images'
	//			redirect(controller:'main')
	//			return
	//		}

	//		InputStream inputStream = null
	//		if (request instanceof MultipartHttpServletRequest) {
	//				MultipartFile uploadedFile = ((MultipartHttpServletRequest) request).getFile('qqfile')
	//				inputStream = uploadedFile.inputStream
	//		} 
	//		else {
	//			// need to do this for integration tests
	//			try {
	//				MultipartFile uploadedFile = request.getFile('qqfile')
	//				inputStream = uploadedFile.inputStream
	//			}
	//			catch(e) {
	//				inputStream = request.inputStream
	//			}
	//		}


	//		// read the image from inputstream
	//		// if it does not work, post a flash message, log it and remove the image domain class instance

	//		BufferedImage bsrc = ImageIO.read(inputStream)

	//		// the image could not be read. probably a wrong type to begin with.
	//		// delete it
	//		if(bsrc == null) {
	//			log.error "Could not read an image. Deleting it. Image.id== " + image.id
	//			imageService.deleteImage(image)
	//			flash.message = "${message(code: 'userdef.couldNotReadImage')}"
	//			render(text: [success: false] as JSON, contentType: 'text/JSON')
	//			return
	//		}

	//		int maxImageHeight = grailsApplication.config.sharevent.maxImageHeight
	//		int maxImageWidth = grailsApplication.config.sharevent.maxImageWidth

	//		// resize the images
	//		def bais = null
	//		if(bsrc.getHeight() > maxImageHeight) {
	//			int height = maxImageHeight 
	//			int width = ((double)bsrc.getWidth()) * ((double)height)/((double)bsrc.getHeight())
	//			BufferedImage bdest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	//			Graphics2D g = bdest.createGraphics();
	//			AffineTransform at = AffineTransform.getScaleInstance((double)width/bsrc.getWidth(), (double)height/bsrc.getHeight());
	//			g.drawRenderedImage(bsrc,at);
	//			def baos = new ByteArrayOutputStream()
	//			ImageIO.write(bdest, "JPG", new MemoryCacheImageOutputStream(baos))
	//			def byteArray = baos.toByteArray()
	//			bais = new ByteArrayInputStream(byteArray)
	//		}
	//		else {
	//			def baos = new ByteArrayOutputStream()
	//			ImageIO.write(bsrc, "JPG", new MemoryCacheImageOutputStream(baos))
	//			def byteArray = baos.toByteArray()
	//			bais = new ByteArrayInputStream(byteArray)
	//		}

	//		// uploading the scaled image
	//		imageDBService.storeImageThumbnail(bais, image, user)

	//		// uploading the original image
	//		def baos = new ByteArrayOutputStream()
	//		ImageIO.write(bsrc, "JPG", new MemoryCacheImageOutputStream(baos))
	//		def byteArray = baos.toByteArray()
	//		bais = new ByteArrayInputStream(byteArray)
	//		imageDBService.storeImage(bais, image, user)
	//		println 'image upload successfull'
	//	}
	//	catch(Exception e) {
	//		e.printStackTrace()
	//		log.error e.toString()
	//		render(text: [success: false] as JSON, contentType: 'text/JSON')
	//		return
	//	}
	//	catch(e) {
	//		log.error e.toString()
	//		render(text: [success: false] as JSON, contentType: 'text/JSON')
	//		return
	//	}
	//	def imageURL = imageDBService.getImageURL(image)
	//	println  'imageURL == ' + imageURL
	//	render(text: [success: true, imageURL: imageURL] as JSON, contentType: 'text/JSON')
	//	return
	//	}
	//}

    def viewExample = {
    }

	def createNew = {
		if(!params.gallery_title) {
			flash.error = "Please provide a title to create a new gallery"
			redirect controller: 'main'
			return
		}

		// get/create the creating user
		// TODO check whether the user is signed in
		def user = new GalleryUser(firstName: GalleryUser.INCOGNITO, lastName: GalleryUser.INCOGNITO, email: GalleryUser.INCOGNITO_MAIL)
		if(!user.save(flush: true)) {
			user.errors.each { error ->
				println  error
			}
			flash.message = "Error while creating the new gallery"
			redirect controller: 'main'
			return
		}

		// create the gallery
		def gallery = new Gallery(date: new Date(), title: params.gallery_title, location: 'Somewhere over the rainbow', creatorId: user.id)
		gallery.addToUsers user
		if(!gallery.save(flush: true)) {
			gallery.errors.each { error -> 
				println  error
			}
			flash.message = "Error while creating the new gallery"
			redirect controller: 'main'
			return
		}

		// save the user with the new gallery added
		user.addToGalleries gallery
		if(!user.save(flush: true)) {
			user.errors.each { error ->
				println  error
			}
			flash.message = "Error while creating the new gallery"
			redirect controller: 'main'
			return
		}

		// entering creatorId as params.key, so that the administration link will be shown
		redirect action: 'view', params: [id: gallery.id, key: gallery.creatorId]
	}

    def view = {
        def gallery = Gallery.get(params.id)

		if(!gallery) {
			flash.message = "Error while loading the gallery"
			redirect controller: 'main'
			return
		}
		
		boolean isAdmin = params.key == gallery.creatorId ? true : false
		log.info "Viewing gallery ${gallery.id} as admin: " + isAdmin

		// TODO check whether the user is logged in
		
		// since we cannot use the AWS service in the gsp, we need to create the links
		// to the images in the image DB here and associate the images with those
		def urls = [:]
		def urlsFull = [:]

		def images = Image.findAllByGallery(gallery,[sort:'dateCreated'])
		images?.each { image ->
			def url = imageDBService.getImageThumbURL(image)
			def urlFull = imageDBService.getImageURL(image)
			urls[image.id.toString()] = url
			urlsFull[image.id.toString()] = urlFull
		}


		log.info 'About to render following images: ' + urls

        if(gallery) {
			// log that we are displaying the gallery
			def galleryLog = GalleryLog.findByGallery(gallery)
			if(!galleryLog) {
				log.info "No galleryLog found, creating one..."
				galleryLog = new GalleryLog(gallery: gallery)
				if(!galleryLog.save(flush: true)) {
					log.error "Error while saving new galleryLog"
					galleryLog.errors.each { error ->
						log.error error
					}
				}
			}
			// TODO get the real IP from the request
			galleryLog?.addClickLogEntry(new Date(), "cookie monster")

			def serverUrl = grailsApplication.config.grails.serverURL ?: ""
			def shortUrl = gallery.urlMap.shortUrl
			shortUrl = serverUrl.toString() + "/" + shortUrl
			def shortAdminUrl = gallery.urlMap.shortAdminUrl
			shortAdminUrl = serverUrl.toString() + "/" + shortAdminUrl

            render view:"view", model:[galleryInstance:gallery, urls: urls, urlsFull: urlsFull, isAdmin: isAdmin, shortUrl: shortUrl, shortAdminUrl: shortAdminUrl, showImage: params.showImage ?: null] 
		}
        else {
			flash.message = "Error while loading the gallery"
            redirect controller:"main" 
		}
    }

	def uploadImage= {
			def image = null
			// TODO check whether the user is logged in

			try {
				log.info 'Creating new Image instance' 
				image = new Image()
				// locking image set to prevent conflicts due to optimistic locking
				// when upload multiple files at once
				// HSQLDB does not support pessimistic locking ==> synchronizing the whole procedure
				// this might be one of the first bottlenecks!
				// XXX BOTTLENECK!
				// TODO update once multiple contributions are allowed
				Gallery galleryInstance = Gallery.get(params.id)

				log.info "galleryInstance loaded locked"

				if(!galleryInstance) {
					log.info 'Could not retrieve gallery to upload image to'
					render status: 500
					return
				}

				def c = GalleryUser.createCriteria()
				def user = c.get {
					eq('firstName', GalleryUser.INCOGNITO)
					galleries {
						eq('id', galleryInstance.id)
					}
					lock true
				}


				if(user == null) {
					log.info "Did not find any incognito users for gallery ${galleryInstance.id}"
					render status: 500
					return
				}

				if(!user.galleries.contains(galleryInstance))
					user.addToGalleries galleryInstance
				if(!galleryInstance.users.contains(user))
					galleryInstance.addToUsers user

				user.addToImages(image)
				galleryInstance.addToImages image

				if(user.isDirty())user.merge()
				if(!user.save(flush: true)) {
					user.errors.each {
						log.info  it
					}
					flash.message = 'something went wrong while uploading your images'
					redirect(controller:'main')
					return
				}

				if(!galleryInstance.save(flush: true)) {
					log.info 'Error while saving Gallery with new image.' 
					galleryInstance.errors.each {
						log.info it 
					}
					flash.message = 'something went wrong while uploading your images'
					redirect(controller:'main')
					return
				}
				
				imageService.uploadImage(request, image.id, user.id)
			}
			catch(Exception e) {
				e.printStackTrace()
				log.error e.toString()
				flash.message = e.getMessage()
				render(text: [success: false] as JSON, contentType: 'text/JSON')
				return
			}
			catch(e) {
				log.error e.toString()
				render(text: [success: false] as JSON, contentType: 'text/JSON')
				return
			}
			def imageURL = imageDBService.getImageThumbURL(image)
			log.info  'imageURL == ' + imageURL
			render(text: [success: true, imageURL: imageURL] as JSON, contentType: 'text/JSON')
	}

	def downloadImage = {
		def image = Image.get(params.imageId)
		def inputStream = imageDBService.getImageInputStream(image)

		if (inputStream == null) {
			log.error "ImageDB is null"
			flash.message = "Some of the selected images could not be downloaded."
			return
		}

		BufferedInputStream origin = new BufferedInputStream(inputStream, 2048)

		response.setContentType("application/octet-stream")
		response.setHeader("Content-disposition", "attachment;filename=${image.id}.jpg")

		response.outputStream << origin
	}

    def download = {
    	// TODO what happens if someone tries to upload and someone else tries to download at the same time?
		// might not happen ever; even then only images would not be uploaded properly. no big deal!...?

        // first get the selected image ids
		def imageIds = []
		params.each {
		    if(it.key instanceof java.lang.String) {
			if(it.key.startsWith("selected_img_")) {
			    // Assuming that Grails is providing only selected checkboxes!
			    String imageId = it.value
				if(imageId.size()>0)
			    		imageIds.add(imageId.split("_")[-1])
			}
		    }
		}

		def images = []

		if(imageIds.size() == 0) {

			def gallery = Gallery.findById(params.id)
			gallery.images.each{
				images.add it
			}

			if(!images){
				log.info "No image to show, redirecting to gallery with id ${params.id}"
				flash.message = "There are no images selected to download."
				redirect(action: 'view', params: [id: params.id])
				return
			}
		}
		else {
			// get the corresponding images
			images = []
			imageIds.each { imageId ->
				try {
					def image = Image.get(imageId.toLong())
					if(!image) {
						log.error "Could not retrieve image w/ id ${imageId} for downloading."
						flash.message = "Some of the selected images could not be downloaded."
					}
					else 
						images.add(image)
				}
				catch(e) {
					log.error "Could not parse imagedId ${imageId} to integer"
				}
			}
		}

		// now zip the selected images to the response stream
		ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(response.outputStream))
		def data = new byte[2048]
		def galleryInstance = Gallery.get(params.id)
		images.each { image ->
			def inputStream = imageDBService.getImageInputStream(image)

			if (inputStream == null) {
				log.error "ImageDB is null"
				flash.message = "Some of the selected images could not be downloaded."
				return
			}

			BufferedInputStream origin = new BufferedInputStream(inputStream, 2048)
			String zipPath = image.gallery.title + '/' + image.id + '.jpg'
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
		def gallery = Gallery.get(params?.id)
		if(!gallery) {
			log.error "Could not find requested gallery with id == " + params?.id
			flash.message = "Oooops, we could not find the requested gallery."
			redirect controller: "main"
			return
		}

		if(params.key != gallery.creatorId) {
			log.info "Unauthorized user tried to delete images from gallery with id ${gallery.id}"
			flash.message = "Only the gallery's creator may delete images."
			redirect controller: "gallery", action: "view", params: [id: gallery.id]
			return
		}

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
		
		// remove the images
		images.each { imageId ->
			try {
				def image = Image.get(imageId)
				def galleryUser = image.galleryUser
				if(imageService.deleteImage(image)) {
					log.error "Error while deleting image == ${imageId}"
				}
				galleryUser.save(flush: true, failOnError: true)
			}
			catch(e) {
				log.error "Error while deleting image == ${imageId}"
				log.error e.toString()
			}
		}

		redirect action: "view", params: [key: gallery.creatorId, id: gallery.id]
	}

	def deleteImage = {
		def gallery = Gallery.get(params?.id)
		if(!gallery) {
			log.error "Could not find requested gallery with id == " + params?.id
			flash.message = "Oooops, we could not find the requested gallery."
			redirect controller: "main"
			return
		}

		if(params.key != gallery.creatorId) {
			log.info "Unauthorized user tried to delete images from gallery with id ${gallery.id}"
			flash.message = "Only the gallery's creator may delete images."
			redirect controller: "gallery", action: "view", params: [id: gallery.id]
			return
		}
		def imageId = Long.valueOf(params.imageId.split('_')[1])
		imageService.deleteImage(imageId)	
	}

    def deleteGallery = {
		def gallery = Gallery.get(params.id)
		if(!gallery) {
			log.error "Error while retrieving gallery with id ${params.id}"
			flash.message = "Error while deleting the gallery."
			redirect controller: "main"
			return
		}
		if(params.key != gallery.creatorId) {
			log.info "Non-creator tried to remove gallery ${gallery.id}"
			flash.message = "Only the gallery's creator may delete it"
			redirect action: "view", params: [id: params.id]
			return
		}
		
		try {
			galleryService.deleteGallery(params.id)
		}
		catch(e) {
			flash.message = "Something went wrong while we tried to delete your gallery."
			redirect controller: "main"
			return
		}

        flash.message = "${message(code: 'userDef.galleryDeleted')}"

        redirect(controller: "main", action: "index")
    }

	def viewShortUrl = {
		// get the URL
		def shortUrl = params.shortUrl
		
		// get the corresponding gallery
		def urlMap = UrlMap.findByShortUrl(shortUrl)

		if(urlMap) {
			redirect action: "view", id: urlMap.gallery.id
		}
		else {
			urlMap = UrlMap.findByShortAdminUrl(shortUrl)
			if(urlMap) {
				redirect action: "view", id: urlMap.gallery.id, params: [key: urlMap.gallery.creatorId]
			}
			else {
				redirect action: "view"
			}
		}
	}
}

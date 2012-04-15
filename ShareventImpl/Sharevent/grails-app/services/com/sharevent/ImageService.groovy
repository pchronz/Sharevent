package com.sharevent

import org.springframework.orm.hibernate3.SessionFactoryUtils
import org.hibernate.FlushMode
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest
import groovy.transform.Synchronized
import groovyx.net.http.RESTClient
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.ContentType.URLENC

class ImageService {

	def imageDBService
	def grailsApplication

    static transactional = true

	def deleteImage(imageId) {
		def image = Image.findById(imageId,[lock:true])			
		def galleryUser = GalleryUser.findById(image.galleryUser.id, [lock:true])
		def gallery = Gallery.findById(image.gallery.id, [lock:true])

		def galUserId = image.galleryUser.id

		galleryUser.removeFromImages(image)
		gallery.removeFromImages(image)

		image.delete(flush: true)
		
		log.info "Deleted image.id == ${imageId} from imageDB."
		imageDBService.delete(imageId, galUserId)

	}

	def uploadImage(def request, def imageId, def userId) {
		// directly streaming through the input to S3
		if (request instanceof MultipartHttpServletRequest) {
				MultipartFile requestFile = ((MultipartHttpServletRequest) request).getFile('qqfile')
				requestFile.inputStream.s3upload(imageId + '.jpg') {
					bucket 'com.sharevent.images'
					path userId + '/'
				}
				log.info 'Handling MultipartHttpServletRequest'
		} 
		else {
			// TODO check whether this gets called anytime at all
			// need to do this for integration tests
			log.info 'Handling MultiPartHttpServerRequestMock'
			try {
				MultipartFile requestFile = request.getFile('qqfile')
				requestFile.inputStream.s3upload(imageId + '.jpg') {
					bucket 'com.sharevent.images'
					path userId + '/'
				}
			}
			catch(e) {
				request.inputStream.s3upload(imageId + '.jpg') {
					bucket 'com.sharevent.images'
					path userId + '/'
				}
			}
		}

		// call the worker node to process the image
		def worker = new HTTPBuilder("http://23.23.198.70/")
		try {
			def resp = worker.post(path:"image", requestContentType: URLENC, body: [userId: userId, imageId: imageId])

			//if(resp.status == 200) log.info "Image processing for image ${imageId} for user ${userId} delegated successfully to worker node"
			//else log.error "Something went wrong while delegating image ${imageId} for user ${userId} to worker node"
		}
		catch(e) {
			log.error "Error while calling the worker node"
			log.error e
		}
	}
}


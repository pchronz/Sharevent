package com.sharevent

import org.springframework.orm.hibernate3.SessionFactoryUtils
import org.hibernate.FlushMode
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest
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

class ImageService {

	def sessionFactory
	def imageDBService
	def grailsApplication
	
    static transactional = true

	def deleteImage(imageId) {
		// TODO refrain from deleting data, mark as deleted instead
		//def session = SessionFactoryUtils.getSession(sessionFactory, true);
		//session.setFlushMode(FlushMode.COMMIT)

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
		InputStream inputStream = null
		if (request instanceof MultipartHttpServletRequest) {
				MultipartFile uploadedFile = ((MultipartHttpServletRequest) request).getFile('qqfile')
				inputStream = uploadedFile.inputStream
				log.info 'Handling MultipartHttpServletRequest'
		} 
		else {
			// TODO check whether this gets called anytime at all
			// need to do this for integration tests
			log.info 'Handling MultiPartHttpServerRequestMock'
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
		// using Grails Executor to run the scaling and upload asynchronously
		BufferedImage bsrc = ImageIO.read(inputStream)
		runAsync {

			// the image could not be read. probably a wrong type to begin with.
			// delete it
			if(bsrc == null) {
				log.error "Could not read an image. Deleting it. Image.id== " + imageId
				imageDBService.deleteImage(imageId, userId)
				throw new Exception("${message(code: 'userdef.couldNotReadImage')}")
			}

			int maxImageHeight = grailsApplication.config.sharevent.maxImageHeight
			int maxImageWidth = grailsApplication.config.sharevent.maxImageWidth

			// uploading the original image
			def baos = new ByteArrayOutputStream()
			def byteArray = baos.toByteArray()
			def bais = new ByteArrayInputStream(byteArray)
			ImageIO.write(bsrc, "JPG", new MemoryCacheImageOutputStream(baos))
			byteArray = baos.toByteArray()
			bais = new ByteArrayInputStream(byteArray)
			imageDBService.storeImage(bais, imageId, userId)

			// resize the images
			// first crop to save performance when scaling
			if(bsrc.width < bsrc.height) {
				bais = cropImage(bsrc.width, bsrc.width, bsrc)
			}
			else {
				bais = cropImage(bsrc.height, bsrc.height, bsrc)
			}
			bsrc = ImageIO.read(bais)
			bais = scaleImage(maxImageHeight, maxImageWidth, bsrc)

			// uploading the scaled image
			imageDBService.storeImageThumbnail(bais, imageId, userId)

		}
		log.info 'image upload successfull'
	}

	// sets the image so that the smaller of width and height is at least big enough
	def scaleImage(int maxImageHeight, int maxImageWidth, BufferedImage bsrc) {
		int width = 0
		int height = 0
		if(bsrc.width < bsrc.height) {
			width = maxImageWidth
			height = ((double)bsrc.getHeight()) * ((double)width)/((double)bsrc.getWidth())
		}
		else {
			height = maxImageHeight
			width = ((double)bsrc.getWidth()) * ((double)height)/((double)bsrc.getHeight())
		}
		BufferedImage bdest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bdest.createGraphics();
		AffineTransform at = AffineTransform.getScaleInstance((double)width/bsrc.getWidth(), (double)height/bsrc.getHeight());
		g.drawRenderedImage(bsrc,at);
		def baos = new ByteArrayOutputStream()
		ImageIO.write(bdest, "JPG", new MemoryCacheImageOutputStream(baos))
		def byteArray = baos.toByteArray()
		new ByteArrayInputStream(byteArray)
	}

	def cropImage(int maxImageHeight, int maxImageWidth, BufferedImage bsrc) {
		def height = maxImageHeight > bsrc.height ? bsrc.height : maxImageHeight
		def width = maxImageWidth > bsrc.width ? bsrc.width : maxImageWidth
		def x = (bsrc.width - width)/2 as int
		def y = (bsrc.height - height)/2 as int
		BufferedImage bdest = bsrc.getSubimage(x, y, width, height)	
		def baos = new ByteArrayOutputStream()
		ImageIO.write(bdest, "JPG", new MemoryCacheImageOutputStream(baos))
		def byteArray = baos.toByteArray()
		new ByteArrayInputStream(byteArray)
	}
}


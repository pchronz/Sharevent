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

	def deleteImage(Image image) {
		// TODO refrain from deleting data, mark as deleted instead
		def session = SessionFactoryUtils.getSession(sessionFactory, true);
		session.setFlushMode(FlushMode.COMMIT)

		def galleryUser = image.galleryUser
		def gallery = image.gallery
		galleryUser.removeFromImages(image)
		gallery.removeFromImages(image)

		imageDBService.delete(image)

		image.delete(flush: true)
	}

	def uploadImage(def request, def image, def user) {
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

		BufferedImage bsrc = ImageIO.read(inputStream)

		// the image could not be read. probably a wrong type to begin with.
		// delete it
		if(bsrc == null) {
			log.error "Could not read an image. Deleting it. Image.id== " + image.id
			imageDBService.deleteImage(image)
			throw new Exception("${message(code: 'userdef.couldNotReadImage')}")
		}

		int maxImageHeight = grailsApplication.config.sharevent.maxImageHeight
		int maxImageWidth = grailsApplication.config.sharevent.maxImageWidth

		// resize the images
		def bais = scaleImage(maxImageHeight, maxImageWidth, bsrc)

		// uploading the scaled image
		imageDBService.storeImageThumbnail(bais, image, user)

		// uploading the original image
		def baos = new ByteArrayOutputStream()
		ImageIO.write(bsrc, "JPG", new MemoryCacheImageOutputStream(baos))
		def byteArray = baos.toByteArray()
		bais = new ByteArrayInputStream(byteArray)
		imageDBService.storeImage(bais, image, user)
		log.info 'image upload successfull'
	}

	def scaleImage(int maxImageHeight, int maxImageWidth, BufferedImage bsrc) {
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
			new ByteArrayInputStream(byteArray)
		}
		else {
			def baos = new ByteArrayOutputStream()
			ImageIO.write(bsrc, "JPG", new MemoryCacheImageOutputStream(baos))
			def byteArray = baos.toByteArray()
			return new ByteArrayInputStream(byteArray)
		}
	}
}


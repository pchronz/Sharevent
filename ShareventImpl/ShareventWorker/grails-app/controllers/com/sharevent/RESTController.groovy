package com.sharevent

import grails.converters.JSON
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

class RESTController {

	def aws

	private static final staticLock = new Object[0]

    def index() { 
		println "index called"
		render status: 200
	}

	def image = {
		println "image service called with params..."
		println params
		def userId = params.userId
		def imageId = params.imageId
		if(!userId || !imageId) {
			render status: 400, text: [message: "userId and imageId not set."]
			return
		}
		println "userId == " + userId
		println "imageId == " + imageId

		def t = new Thread({
			processImage(userId, imageId)
		})
		t.start()
		println 'image upload successfull'

		render status: 200, text: [message: "image upload successfull"] as JSON
	}

	private def processImage(userId, imageId) {
		// to save spare resources (mostly memory) on the host, only load one image at a time into memory
		println "Waiting for image processing lock"
		synchronized(staticLock) {
			try {
				println("Starting to process image")
				println "Downloading image..."
				def imageFile = aws.s3().on('com.sharevent.images').get(imageId + '.jpg', userId + '/')
				println "Image downloaded: " + imageFile
				BufferedImage bsrc = ImageIO.read(imageFile.dataInputStream)
				// the image could not be read. probably a wrong type to begin with.
				// delete it
				if(bsrc == null) {
					println "Could not read an image. Deleting it. Image.id== " + imageId
					// TODO also delete it on S3
					// TODO delete the image in the DB
					throw new Exception("${message(code: 'userdef.couldNotReadImage')}")
				}

				println "Successfully read image"

				int maxImageHeight = grailsApplication.config.sharevent.maxImageHeight
				int maxImageWidth = grailsApplication.config.sharevent.maxImageWidth

				// resize the images
				// first crop to save performance when scaling
				def bais = null
				if(bsrc.width < bsrc.height) {
					bais = cropImage(bsrc.width, bsrc.width, bsrc)
				}
				else {
					bais = cropImage(bsrc.height, bsrc.height, bsrc)
				}
				bsrc = ImageIO.read(bais)
				bais = scaleImage(maxImageHeight, maxImageWidth, bsrc)

				// uploading the scaled image
				bais.s3upload(imageId + '.jpg') {
					bucket 'com.sharevent.imagethumbs'
					path userId + '/'
				}
				println("Processing image done")
			}
			catch(e) {
				println e
			}
		}
	}

	// sets the image so that the smaller of width and height is at least big enough
	private def scaleImage(int maxImageHeight, int maxImageWidth, BufferedImage bsrc) {
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

	private def cropImage(int maxImageHeight, int maxImageWidth, BufferedImage bsrc) {
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

package com.sharevent

import grails.converters.JSON
import grails.util.GrailsUtil
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.MemoryCacheImageOutputStream
import grails.plugins.springsecurity.Secured
import org.springframework.beans.factory.InitializingBean
import grails.converters.*
import org.codehaus.groovy.grails.web.json.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import grails.util.Environment

class ImageDBService {

    static transactional = true

	def grailsApplication
	def ajaxUploaderService
	def db
	def aws


    def getImageThumbURL(image) {
		return aws.s3().on('com.sharevent.imagethumbs').url(image.id + '.jpg', image.galleryUser.id + '/')

    }

    def getImageURL(image) {

		return aws.s3().on('com.sharevent.images').url(image.id + '.jpg', image.galleryUser.id + '/')
		
    }

	def delete(image) {

		log.info "Attempting to delete image.id == ${image.id} from S3"
		aws.s3().on('com.sharevent.images').delete(image.id.toString(), image.galleryUser.id)
		aws.s3().on('com.sharevent.imagethumbs').delete(image.id.toString(), image.galleryUser.id)
		log.info "Done deleting image.id == ${image.id} from S3"

	}


	def getImageThumbInputStream(image) {

		def s3Image = aws.s3().on('com.sharevent.imagethumbs').get(image.id + ".jpg", image.galleryUser.id + "/")
		def s3InputStream = s3Image.getDataInpuStream()
		if(!s3InputStream) {
			log.error "Could not retrieve inputStream for downloading image.id == " + image.id
			throw new Exception("Could not retrieve inputStream for downloading image.id == " + image.id)
		}
		return BufferedInpuStream(s3InputStream, 2048)
		
	}

	def getImageInputStream(image) {

		def s3Image = aws.s3().on('com.sharevent.images').get(image.id + ".jpg", image.galleryUser.id + "/")
		def s3InputStream = s3Image.getDataInputStream()
		if(!s3InputStream) {
			log.error "Could not retrieve inputStream for downloading image.id == " + image.id
			throw new Exception("Could not retrieve inputStream for downloading image.id == " + image.id)
		}

	}

	def storeImageThumbnail(bais, image, user) {
		//shure you are forwarding an ByteArrayOutputShit ?
		bais.s3upload(image.id + '.jpg') {
			bucket 'com.sharevent.imagethumbs'
			path user.id + '/'
		}

	}

	def storeImage(bais, image, user) {

		bais.s3upload(image.id + '.jpg') {
			bucket 'com.sharevent.images'
			path user.id + '/'
		}
		bais.close()

	}


	// remove all entries from imageDB, which have no counterpart in the main data source
	def synchronizeImageDB() {
			// TODO S3?
	}

}


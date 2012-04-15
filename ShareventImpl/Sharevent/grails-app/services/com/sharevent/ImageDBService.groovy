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

	def delete(imageId,galleryUserId) {
		log.info "Attempting to delete image.id == ${imageId} from S3"
		aws.s3().on('com.sharevent.images').delete(imageId.toString() + ".jpg", galleryUserId.toString() + "/")
		aws.s3().on('com.sharevent.imagethumbs').delete(imageId.toString() + ".jpg", galleryUserId.toString() + "/")
		log.info "Done deleting image.id == ${imageId} from S3"
	}


	def getImageThumbInputStream(image) {
		try {
			def s3Image = aws.s3().on('com.sharevent.imagethumbs').get(image.id + ".jpg", image.galleryUser.id + "/")
			def s3InputStream = s3Image.getDataInputStream()
			if(!s3InputStream) {
				log.error "Could not retrieve inputStream for downloading image.id == " + image.id
				throw new Exception("Could not retrieve inputStream for downloading image.id == " + image.id)
			}
			return new BufferedInputStream(s3InputStream, 2048)
		}
		catch(e) {
			return null
		}
	}

	def getImageInputStream(image) {
		try {
			def s3Image = aws.s3().on('com.sharevent.images').get(image.id + ".jpg", image.galleryUser.id + "/")
			def s3InputStream = s3Image.getDataInputStream()
			if(!s3InputStream) {
				log.error "Could not retrieve inputStream for downloading image.id == " + image.id
				throw new Exception("Could not retrieve inputStream for downloading image.id == " + image.id)
			}
			return new BufferedInputStream(s3InputStream, 2048)
		}
		catch(e) {
			return null
		}
	}

	// remove all entries from imageDB, which have no counterpart in the main data source
	def synchronizeImageDB() {
		// TODO S3
//		if(Environment.currentEnvironment == Environment.DEVELOPMENT) {
//			if(!initialized) initService()
//
//			// get all image ids in mongo
//			def mongoKeys = []
//			DBCollection dbCollection = this.db.getCollection("images")
//			BasicDBObject query = new BasicDBObject()
//			//query.put("", )
//			DBCursor cursor = dbCollection.find(query)
//			try {
//				while(cursor.hasNext()) {
//					def nextObject = cursor.next()
//					// TODO get rid of all string references to collections etc
//					def imageId = nextObject.get("imageKey") as Long
//					mongoKeys += imageId
//				}
//			}
//			catch(Exception e) {
//				log.error "could not open image.id=="
//				return null
//			}
//
//			log.error  'Found the following keys in mongo'
//			log.error  mongoKeys
//
//
//			// remove all image ids also available in the local data source
//			Image.list().each { image ->
//				if(mongoKeys.contains(image.id)) {
//					mongoKeys.remove(image.id)
//				}
//			}
//
//
//			// delete all remaining image ids from the image collection
//			// delete all remaining image ids from the thumbs collection
//			mongoKeys.each { key ->
//				removeImage(key, grailsApplication.config.sharevent.imageDBCollection)
//				removeImage(key, "imagethumbs")
//			}
//		}
//		else {
//			// TODO S3?
//		}
	}
}


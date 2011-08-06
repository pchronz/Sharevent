package com.sharevent

import grails.converters.JSON
import grails.util.GrailsUtil
import com.mongodb.Mongo
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import com.mongodb.DBCursor
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.MemoryCacheImageOutputStream
import grails.plugins.springsecurity.Secured
import org.springframework.beans.factory.InitializingBean


class ImageDBService implements InitializingBean {

    static transactional = true

	def aws
	def grailsApplication
	def ajaxUploaderService
	def mongo

    def getImageURL(image) {
		// return the url for the image thumbnail
		// TODO fix this in Grails 1.4/2.0 with refactored TagLib and LinkManager
		return "${grailsApplication.config.sharevent.serverURL}" + "/image/viewImageThumbnail/${image.id}"
    }

	def delete(image) {
		// TODO read mongo settings from config
		DB db = mongo.getDB("${grailsApplication.config.sharevent.imageDB}")

		// delete the image
		DBCollection dbCollection = db.getCollection("${grailsApplication.config.sharevent.imageDBCollection}")
		def query = new BasicDBObject()
		query.put("${grailsApplication.config.sharevent.imageDBImageId}", image.id)
		DBCursor cursor = dbCollection.find(query)
		if(!cursor.hasNext())
			log.error "could not find image with image.id==" + image.id 
		DBObject imageDocObject = cursor.next()
		dbCollection.remove(imageDocObject)
		if(cursor.hasNext()) {
			log.error "multiple images found for one id. deleting all of them"
		}
		while(cursor.hasNext()) {
			def dbObject = cursor.next()
			dbCollection.remove(dbObject)
		}

		// delete the thumbnail
		dbCollection = db.getCollection("imageThumbs")
		query = new BasicDBObject()
		query.put("${grailsApplication.config.sharevent.imageDBImageId}", image.id)
		cursor = dbCollection.find(query)
		if(!cursor.hasNext())
			log.error "could not find imageThumb with image.id==" + image.id 
		imageDocObject = cursor.next()
		dbCollection.remove(imageDocObject)
		if(cursor.hasNext()) {
			log.error "multiple images found for one id. deleting all of them"
		}
		while(cursor.hasNext()) {
			def dbObject = cursor.next()
			dbCollection.remove(dbObject)
		}
	}


	def getImageThumbInputStream(image) {
		println "starting getImageThumbInputStream"
		println image
		DB db = mongo.getDB("${grailsApplication.config.sharevent.imageDB}")
		DBCollection dbCollection = db.getCollection("imageThumbs")
		BasicDBObject query = new BasicDBObject()
		query.put("${grailsApplication.config.sharevent.imageDBImageId}", image.id)
		DBCursor cursor = dbCollection.find(query)
		try {
			if(cursor.hasNext()) {
				def nextObject = cursor.next()
				if(cursor.hasNext()) {
					log.warn "Query returned multiple images for supposedly unique images. Returning only the first image."
				}
				byte[] imageBytes = nextObject.get("imageBytes") as byte[]
				println "returning imageInputStream"
				return new ByteArrayInputStream(imageBytes)
			}
		}
		catch(Exception e) {
			log.error "could not open image.id==" + image?.id
			return null
		}
	}

	def getImageInputStream(image) {
		try {
			println "starting getImageInputStream"
			println "getting mongodb"
			println "getting mongodb"
			DB db = mongo.getDB("${grailsApplication.config.sharevent.imageDB}")
			println "getting collection"
			DBCollection dbCollection = db.getCollection("${grailsApplication.config.sharevent.imageDBCollection}")
			BasicDBObject query = new BasicDBObject()
			query.put("${grailsApplication.config.sharevent.imageDBImageId}", image.id)
			println "executing query for image.id == " + image.id
			println "query == " + query.toString()
			println "collection == " + dbCollection.toString()
			println "db == " + db.toString()
			DBCursor cursor = dbCollection.find(query)
			if(cursor.hasNext()) {
				println "found a result from query"
				def nextObject = cursor.next()
				if(cursor.hasNext()) {
					println "found more than one result from query!"
					log.warn "Query returned multiple images for supposedly unique images. Returning only the first image."
				}
				byte[] imageBytes = nextObject.get("imageBytes") as byte[]
				println "returning byte[] retrieved from query, byte[].length " + imageBytes.length
				return new ByteArrayInputStream(imageBytes)
			}
			else {
				println "did not find any result to the query for image.id == " + image.id
				println "throwing an exception"
			}
		}
		catch(e) {
			println "Exception while trying to get the imageInputStream from mongodb"
			println e.printStackTrace()
			throw e
		}
	}

	def storeImageThumbnail(bais, image, user) {
		DB db = mongo.getDB("${grailsApplication.config.sharevent.imageDB}")
		DBCollection dbCollection = db.getCollection("imageThumbs")
		BufferedImage bImage = ImageIO.read(bais)
		ByteArrayOutputStream baos = new ByteArrayOutputStream()
		// TODO handle pngs properly
		ImageIO.write(bImage, "jpg", baos)
		

		BasicDBObject dbObject = new BasicDBObject()
		dbObject.put("${grailsApplication.config.sharevent.imageDBImageId}", image.id)
		dbObject.put("userId", user.id)
		byte[] imageBytesIn = baos.toByteArray()
		dbObject.put("imageBytes", imageBytesIn)
		dbCollection.insert(dbObject)
		log.info "inserted image with image.bytes[].length==" + imageBytesIn.length 
	}

	def storeImage(bais, image, user) {
		DB db = mongo.getDB("${grailsApplication.config.sharevent.imageDB}")
		DBCollection dbCollection = db.getCollection("${grailsApplication.config.sharevent.imageDBCollection}")
		BufferedImage bImage = ImageIO.read(bais)
		ByteArrayOutputStream baos = new ByteArrayOutputStream()
		// TODO handle pngs properly
		ImageIO.write(bImage, "jpg", baos)
		

		BasicDBObject dbObject = new BasicDBObject()
		dbObject.put("${grailsApplication.config.sharevent.imageDBImageId}", image.id)
		dbObject.put("userId", user.id)
		byte[] imageBytesIn = baos.toByteArray()
		dbObject.put("imageBytes", imageBytesIn)
		dbCollection.insert(dbObject)
		log.info "inserted image with image.bytes[].length==" + imageBytesIn.length 
	}

	void afterPropertiesSet() {
		// "injecting" mongo client instance into imagedb service
		this.mongo = new Mongo("localhost", 27017)
		return
	}

}

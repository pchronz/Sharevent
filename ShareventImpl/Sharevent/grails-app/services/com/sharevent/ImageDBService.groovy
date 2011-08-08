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
import grails.converters.*
import org.codehaus.groovy.grails.web.json.*

class ImageDBService implements InitializingBean {

    static transactional = true

	def aws
	def grailsApplication
	def ajaxUploaderService
	def db

    def getImageURL(image) {
		// return the url for the image thumbnail
		// TODO fix this in Grails 1.4/2.0 with refactored TagLib and LinkManager
		return "${grailsApplication.config.sharevent.serverURL}" + "/image/viewImageThumbnail/${image.id}"
    }

	def delete(image) {

		// delete the image
		DBCollection dbCollection = this.db.getCollection("${grailsApplication.config.sharevent.imageDBCollection}")
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
		// TODO replace string from value from configuration
		dbCollection = this.db.getCollection("imageThumbs")
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
		DBCollection dbCollection = this.db.getCollection("imageThumbs")
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
			println "getting collection"
			DBCollection dbCollection = this.db.getCollection("${grailsApplication.config.sharevent.imageDBCollection}")
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
		DBCollection dbCollection = this.db.getCollection("imageThumbs")
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
		DBCollection dbCollection = this.db.getCollection("${grailsApplication.config.sharevent.imageDBCollection}")
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
		def vcapEnv = parseVcapEnv()
		def mongo = new Mongo(vcapEnv.hostname, vcapEnv.port)
		// TODO read mongo settings from config
		this.db = mongo.getDB(vcapEnv.db)
		if(vcapEnv.containsKey('username')) {
			this.db.authenticate(vcapEnv.username, vcapEnv.password as char[])
		}
		return
	}

	def parseVcapEnv() {
		def results = [:]
		try {
			def env = System.getenv()
			def vcap = env['VCAP_SERVICES']
			println 'VCAP_SERVICES == ' + vcap

			def vcapJSON = JSON.parse(vcap)
			println vcapJSON['mongodb-1.8']
			def mongoJSON = vcapJSON['mongodb-1.8']
			def credentials = mongoJSON.credentials
			results['hostname'] = credentials.hostname.get(0)
			results['port'] = credentials.port.get(0)
			results['db'] = credentials.db.get(0)
			results['username'] = credentials.username.get(0)
			results['name'] = credentials.name.get(0)
			results['password'] = credentials.password.get(0)
			println results
		}
		catch(e) {
			println 'could not read VCAP_SERVICES completely... falling back to default values'
			results['hostname'] = 'localhost'
			results['port'] = 27017
			results['db'] = 'db'
			println results
		}

		results
	}

}

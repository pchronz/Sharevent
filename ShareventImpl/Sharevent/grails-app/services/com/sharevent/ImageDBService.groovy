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
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

class ImageDBService {

    static transactional = true

	def grailsApplication
	def ajaxUploaderService
	def db

	private boolean initialized = false

	void initService() {
		// "injecting" mongo client instance into imagedb service
		def vcapEnv = parseVcapEnv()
		def mongo = new Mongo(vcapEnv.hostname, vcapEnv.port)
		// TODO read mongo settings from config
		this.db = mongo.getDB(vcapEnv.db)
		if(vcapEnv.containsKey('username')) {
			this.db.authenticate(vcapEnv.username, vcapEnv.password as char[])
		}
		initialized = true
		return
	}

    def getImageURL(image) {
		if(!initialized) initService()

		// return the url for the image thumbnail
		// TODO fix this in Grails 1.4/2.0 with refactored TagLib and LinkManager
		return CH.config.grails.serverURL + "/image/viewImageThumbnail/${image.id}"
    }

	def delete(image) {
		if(!initialized) initService()

		// delete the image
		removeImage(image.id, ${grailsApplication.config.sharevent.imageDBCollection})

		// delete the thumbnail
		removeImage(image.id, "imageThumbs")
	}


	def getImageThumbInputStream(image) {
		if(!initialized) initService()

		log.error "starting getImageThumbInputStream"
		log.error image
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
				log.error "returning imageInputStream"
				return new ByteArrayInputStream(imageBytes)
			}
		}
		catch(Exception e) {
			log.error "could not open image.id==" + image?.id
			return null
		}
	}

	def getImageInputStream(image) {
		if(!initialized) initService()

		try {
			log.error "starting getImageInputStream"
			log.error "getting mongodb"
			log.error "getting mongodb"
			log.error "getting collection"
			DBCollection dbCollection = this.db.getCollection("${grailsApplication.config.sharevent.imageDBCollection}")
			BasicDBObject query = new BasicDBObject()
			query.put("${grailsApplication.config.sharevent.imageDBImageId}", image.id)
			log.error "executing query for image.id == " + image.id
			log.error "query == " + query.toString()
			log.error "collection == " + dbCollection.toString()
			log.error "db == " + db.toString()
			DBCursor cursor = dbCollection.find(query)
			if(cursor.hasNext()) {
				log.error "found a result from query"
				def nextObject = cursor.next()
				if(cursor.hasNext()) {
					log.error "found more than one result from query!"
					log.warn "Query returned multiple images for supposedly unique images. Returning only the first image."
				}
				byte[] imageBytes = nextObject.get("imageBytes") as byte[]
				log.error "returning byte[] retrieved from query, byte[].length " + imageBytes.length
				return new ByteArrayInputStream(imageBytes)
			}
			else {
				log.error "did not find any result to the query for image.id == " + image.id
				log.error "throwing an exception"
			}
		}
		catch(e) {
			log.error "Exception while trying to get the imageInputStream from mongodb"
			log.error e.printStackTrace()
			throw e
		}
	}

	def storeImageThumbnail(bais, image, user) {
		if(!initialized) initService()

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
		log.error "inserted image with image.bytes[].length==" + imageBytesIn.length 
	}

	def storeImage(bais, image, user) {
		if(!initialized) initService()

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
		log.error "inserted image with image.bytes[].length==" + imageBytesIn.length 
	}

	def parseVcapEnv() {
		def results = [:]
		try {
			def env = System.getenv()
			def vcap = env['VCAP_SERVICES']
			log.error 'VCAP_SERVICES == ' + vcap

			def vcapJSON = JSON.parse(vcap)
			log.error vcapJSON['mongodb-1.8']
			def mongoJSON = vcapJSON['mongodb-1.8']
			def credentials = mongoJSON.credentials
			results['hostname'] = credentials.hostname.get(0)
			results['port'] = credentials.port.get(0)
			results['db'] = credentials.db.get(0)
			results['username'] = credentials.username.get(0)
			results['name'] = credentials.name.get(0)
			results['password'] = credentials.password.get(0)
			log.error results
		}
		catch(e) {
			log.error 'could not read VCAP_SERVICES completely... falling back to default values'
			results['hostname'] = 'localhost'
			results['port'] = 27017
			results['db'] = 'db'
			log.error results
		}

		results
	}

	// remove all entries from imageDB, which have no counterpart in the main data source
	def synchronizeImageDB() {
		if(!initialized) initService()

		// get all image ids in mongo
		def mongoKeys = []
		DBCollection dbCollection = this.db.getCollection("images")
		BasicDBObject query = new BasicDBObject()
		//query.put("", )
		DBCursor cursor = dbCollection.find(query)
		try {
			while(cursor.hasNext()) {
				def nextObject = cursor.next()
				// TODO get rid of all string references to collections etc
				def imageId = nextObject.get("imageKey") as Long
				mongoKeys += imageId
			}
		}
		catch(Exception e) {
			log.error "could not open image.id=="
			return null
		}

		log.error  'Found the following keys in mongo'
		log.error  mongoKeys


		// remove all image ids also available in the local data source
		Image.list().each { image ->
			if(mongoKeys.contains(image.id)) {
				mongoKeys.remove(image.id)
			}
		}


		// delete all remaining image ids from the image collection
		// delete all remaining image ids from the thumbs collection
		mongoKeys.each { key ->
			removeImage(key, grailsApplication.config.sharevent.imageDBCollection)
			removeImage(key, "imageThumbs")
		}
	}

	private void removeImage(def imageId, def collection) {
		if(!initialized) initService()

		DBCollection dbCollection = this.db.getCollection(collection)
		BasicDBObject query = new BasicDBObject()
		query.put("${grailsApplication.config.sharevent.imageDBImageId}", imageId)
		DBCursor cursor = dbCollection.find(query)
		try {
			// TODO log the number of removed objects
			while(cursor.hasNext()) {
				def nextObject = cursor.next()
				dbCollection.remove(nextObject)
			}
		}
		catch(Exception e) {
			log.error e
			log.error "could not open image.id==" + imageId
		}
	}
}


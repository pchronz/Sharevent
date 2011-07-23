package com.sharevent

import grails.test.*
import org.springframework.mock.web.MockMultipartFile
import org.springframework.mock.web.MockMultipartHttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.MemoryCacheImageOutputStream
import com.mongodb.Mongo
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import com.mongodb.DBCursor

import org.codehaus.groovy.grails.web.context.ServletContextHolder 

class GalleryControllerTests extends GroovyTestCase {
	def gallery
	def imageDBService

    protected void setUp() {
        super.setUp()

    }

    protected void tearDown() {
        super.tearDown()
		// TODO use special test db in mongodb
		// TODO delete all elements in the respective collections in mongodb
    }

	void testViewExample() {
		// call the controller action to view the gallery
		GalleryController galleryController = new GalleryController()
		galleryController.viewExample()
	}

	void testCreateGallery() {

	}

	void testViewUserGallery() {
		clearAll()
		createNewGallery()
		def galleryList = Gallery.list()
		def beforeSize = galleryList.size()
		assertEquals 1, beforeSize

		// view the gallery first
		def galleryController = new GalleryController()
		galleryController.params.clear()
		galleryController.params.id = galleryList[0].id
		galleryController.view()
		assertNull galleryController.response.redirectedUrl
		assertNotNull galleryController.response.contentAsString
		
		// call contribute action
		//galleryController.contributeImages()
		//assertNotNull galleryController.response.redirectedUrl
		//assertNull galleryController.response.contentAsString
		// TODO verify that we have been redirected to the user creation view

		// TODO create the user

		// TODO upload images

		// TODO verify that the images are there
	}
	
	void testView() {
		clearAll()
		createNewGallery()
		def galleries = Gallery.list()
		assertEquals 1, galleries.size()
		def gc = new GalleryController()
		gc.params.id = galleries[0].id
		// TODO params.key, session.user incl. null
		gc.view()
		assertNull gc.response.redirectedUrl
		assertNotNull gc.response.contentAsString
	}

	void testCreateFree() {
		clearAll()
		def gc = new GalleryController()
		gc.createFree()
		assertNotNull gc.response.contentAsString
		assertNull gc.response.redirectedUrl
	}

	void testShare() {
		clearAll()
		def beforeSize = Gallery.list().size()
		assert(beforeSize == 0)
		GalleryController galleryController = new GalleryController()
		galleryController.params.date_day = '17'
		galleryController.params.title = 'Gallery Title'
		galleryController.params.location = 'Gallery Location'
		galleryController.params.date_year = '2011'
		galleryController.params.date_month = '7'
		galleryController.params.date = 'Sun Jul 17 00:00:00 CEST 2011'
		galleryController.params.creatorEmail = 'test@mail.com'
		galleryController.params.creatorLastName = 'Last name'
		galleryController.params.creatorFirstName = 'First name'
		galleryController.share()
		assertNull galleryController.response.redirectedUrl
		assertNotNull galleryController.response.contentAsString
		def afterSize = Gallery.list().size()
		assert(afterSize == 1)
		def users = GalleryUser.list()
		assert(users.size() == 1)
		assert(galleryController.session.user.id == users[0].id)
	}

	void testDownload() {
		clearAll()
		createNewGallery()
		def galleries = Gallery.list()
		assertEquals 1, galleries.size()

		def galleryController = new GalleryController()
		def userId = galleries[0].adminKey
		def users = GalleryUser.list()
		assertEquals 2, users.size()
		def user = GalleryUser.get(galleries[0].adminKey)
		assertNotNull user
		assertEquals galleries[0].adminKey, user.id
		galleryController.session.user = user

		// render contributeImages
		galleryController.params.clear()
		galleryController.params.id = galleryController.session.user.contributedGallery.id
		galleryController.contributeImages()
		assertNull galleryController.response.redirectedUrl
		assertNotNull galleryController.response.contentAsString

		// upload images
		def inImage = uploadOneImage(galleryController)
		// retrieve the image and verify it is the same as the one before
		def images = Image.list()
		assertEquals 1, images.size()
		Image image = null
		// TODO check for right associations and right order
		images.each {
			println 'image == ' + it
			println 'imageDBService == ' + imageDBService
			def imageInputStream = imageDBService.getImageInputStream(it)
			println 'imageInputStream == ' + imageInputStream
			def storedImage = ImageIO.read(imageInputStream)
			// just a quick check for the same size
			assertEquals inImage.getWidth(), storedImage.getWidth()
			assertEquals inImage.getHeight(), storedImage.getHeight()
		}
	}

	void testDeleteImages() {
		clearAll()
		createNewGallery()
		// first upload some images
		def gc = new GalleryController()
		DB db = imageDBService.mongo.getDB('sharevent')
		DBCollection dbCollection = db.getCollection('images')
		DBCollection thumbCollection = db.getCollection('imageThumbs')
		
		def image = uploadOneImageBackdoors()

		// find the upload image in mongodb
		def query = new BasicDBObject()
		query.put('imageKey', image.id)
		DBCursor cursor = dbCollection.find(query)
		if(!cursor.hasNext()) {
			log.error 'could not find image with image.id==' + image.id 
			fail()
		}
		else {
			println 'found image.id == ' + image.id + ' in mongodb.'
		}

		// find the uploaded image thumb in mongodb
		query = new BasicDBObject()
		query.put('imageKey', image.id)
		cursor = dbCollection.find(query)
		if(!cursor.hasNext()) {
			log.error 'could not find image thumb with image.id==' + image.id 
			fail()
		}
		else {
			println 'found thumb with image.id == ' + image.id + ' in mongodb.'
		}

		imageDBService.delete(image)


		// try to locate the delete image in mongodb
		query = new BasicDBObject()
		query.put('imageKey', image.id)
		cursor = dbCollection.find(query)
		if(cursor.hasNext()) {
			log.error 'image.id == ' + image.id + ' has not been deleted'
			fail()
			return
		}
		else {
			println 'found thumb with image.id == ' + image.id
		}

		// try to locate the delete image thumb in mongodb
		query = new BasicDBObject()
		query.put('imageKey', image.id)
		cursor = thumbCollection.find(query)
		if(cursor.hasNext()) {
			log.error 'thumb with image.id == ' + image.id + ' has not been deleted'
			fail()
			return
		}
		else {
			println 'found thumb with image.id == ' + image.id
		}
	}

	void testDeleteGallery() {
		// TODO
	}

	void testContributeImages() {
		clearAll()
		createNewGallery()
		def gc = new GalleryController()
		def gallery = Gallery.list()[0]
		gc.session.user = GalleryUser.get(gallery.adminKey)
		gc.params.id = gallery.id
		assertNotNull gc.session.user
		gc.contributeImages()
		assertNull gc.response.redirectedUrl
		assertNotNull gc.response.contentAsString
	}

	void testUploadImage() {
		// TODO
	}

	void testLogout() {
		clearAll()
		createNewGallery()

		def gc = new GalleryController()
		gc.session.user = GalleryUser.list()[0]
		gc.logout()
		assertNull gc.session.user
		assertNotNull gc.response.redirectedUrl
		assertEquals '', gc.response.contentAsString
	}

	protected void createNewGallery() {
		// create a gallery
		gallery = new Gallery(date:new Date(), title:"Super cool event", location:"Dortmund", creatorFirstName: "Cook", creatorLastName: "Poo", creatorEmail: "cook@poo.ie", adminKey: 1234);

		GalleryUser user = new GalleryUser(firstName:"Lance", lastName:"Hardwood", email:"lance@hardwood.xxx")
		gallery.addToContributors(user)

		ImageSet imageSet = new ImageSet()
		imageSet.images.each { image ->
			image.imageSet = imageSet
		}
		user.imageSet = imageSet

		user = new GalleryUser(firstName:"Chew", lastName:"Bakka", email:"chewie@hardwood.xxx", contributedGallery:gallery)
		gallery.addToContributors(user)

		imageSet = new ImageSet()
		imageSet.images.each { image ->
			image.imageSet = imageSet
		}

		user.imageSet = imageSet

		if(!gallery.save(flush:true)) {
			gallery.errors.each {
				println it
			}
			fail()
		}

		gallery.adminKey = user.id

		if(!gallery.save(flush:true)) {
			gallery.errors.each {
				println it
			}
			fail()
		}

	}

	protected void clearAll() {
		// galleries
		Gallery.list().each {
			it.delete(flush: true)
		}

		// collections in mongodb
		DB db = imageDBService.mongo.getDB('sharevent')
		DBCollection dbCollection = db.getCollection('images')
		DBCollection thumbCollection = db.getCollection('imageThumbs')
		dbCollection.drop()
		thumbCollection.drop()
	}

	protected def uploadOneImage(def galleryController) {
		galleryController.params.clear()
		def imgContentType = 'image/jpeg'
		Enumeration enu = this.getClass().getClassLoader().getResources("image.jpg")
		// TODO upload multiple images in sequence
		File imageFile
		if(enu.hasMoreElements()) {
			imageFile = new File(enu.nextElement().toURI())
		}
		def inImage = ImageIO.read(imageFile)
		def baos = new ByteArrayOutputStream()
		ImageIO.write(inImage, 'jpg', baos)
        byte[] imgContentBytes =  baos.toByteArray()
        galleryController.metaClass.request = new MockMultipartHttpServletRequest()
        galleryController.request.addFile(new MockMultipartFile('qqfile', 'myImage.jpg', imgContentType, imgContentBytes))
        galleryController.uploadImage()
        assertEquals HttpServletResponse.SC_OK, galleryController.response.status
		return inImage
	}

	protected def uploadOneImageBackdoors() {
		def gallery = Gallery.list()[0]
		def newImage = new Image()
		def user = GalleryUser.list()[0]
		user.imageSet.addToImages(newImage)
		if(!gallery.save(flush: true)) {
			log.error 'Could not save image during test'
			gallery.errors.each {
				log.error it.toString()
			}
			fail()
			return
		}

		Enumeration enu = this.getClass().getClassLoader().getResources("image.jpg")
		File imageFile
		if(enu.hasMoreElements()) {
			imageFile = new File(enu.nextElement().toURI())
		}
		def renderedImage = ImageIO.read(imageFile)
		def baos = new ByteArrayOutputStream()
		ImageIO.write(renderedImage, 'jpg', baos)
		def imageBytes = baos.toByteArray()
		def bais = new ByteArrayInputStream(imageBytes)
		println 'uploading image backdoors...'
		println 'bais == ' + bais
		println 'newImage == ' + newImage
		println 'user == ' + user
		imageDBService.storeImage(bais, newImage, user)

		// also uplaod the thumbnail
		enu = this.getClass().getClassLoader().getResources("image.jpg")
		imageFile
		if(enu.hasMoreElements()) {
			imageFile = new File(enu.nextElement().toURI())
		}
		renderedImage = ImageIO.read(imageFile)
		baos = new ByteArrayOutputStream()
		ImageIO.write(renderedImage, 'jpg', baos)
		imageBytes = baos.toByteArray()
		bais = new ByteArrayInputStream(imageBytes)
		println 'uploading image thumb backdoors...'
		println 'bais == ' + bais
		println 'newImage == ' + newImage
		println 'user == ' + user
		imageDBService.storeImageThumbnail(bais, newImage, user)
		return newImage
	}

}

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
		assertEquals 0, beforeSize
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
		assertEquals 1, afterSize
		def users = GalleryUser.list()
		assertEquals 1, users.size()
		assertEquals galleryController.session.user.id, users[0].id
	}

	void testDownload() {
		clearAll()
		createNewGallery()
		def galleries = Gallery.list()
		assertEquals 1, galleries.size()

		def galleryController = new GalleryController()
		def userId = galleries[0].creatorId
		def users = GalleryUser.list()
		assertEquals 1, users.size()
		def user = GalleryUser.get(galleries[0].creatorId)
		assertNotNull user
		assertEquals galleries[0].creatorId, user.id
		galleryController.session.user = user

		// render contributeImages
		galleryController.params.clear()
		def userGalleries = galleryController.session.user.galleries
		assertEquals 1, userGalleries.size()
		userGalleries.each {
			galleryController.params.id = it.id
		}
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
		// this test does not really comply with the DRY-principle...
		// it is mostly a copy of various other code snippets
		// also this test is contained by other more elaborate tests
		clearAll()
		createNewGallery()
		// first upload some images
		def gc = new GalleryController()
		DB db = imageDBService.db
		DBCollection dbCollection = db.getCollection('images')
		DBCollection thumbCollection = db.getCollection('imageThumbs')
		
		def inImage = uploadOneImageBackdoors()

		// get the domain created domain instance for the image
		def images = Image.list()
		assertEquals 1, images.size()

		def image = images[0]

		assertEquals 2, findImageInMongo(image.id)

		// TODO actually delete the images and check again
		def galleries = Gallery.list()
		assertEquals 1, galleries.size()
		gc.params['image_' + image.id] = ''
		gc.params.id = galleries[0].id
		gc.deleteImages()

		assertNotNull gc.response.redirectedUrl
		assertEquals '', gc.response.contentAsString

		assertEquals 0, findImageInMongo(image.id)

	}

	void testDeleteGallery() {
		clearAll()
		createNewGallery()
		def gc = new GalleryController()
		def newImage = uploadOneImageBackdoors()
		def imageId = newImage.id
		// retrieve the image and verify it is the same as the one before
		def images = Image.list()
		assertEquals 1, images.size()
		images.each {
			println 'image == ' + it
			println 'imageDBService == ' + imageDBService
			def imageInputStream = imageDBService.getImageInputStream(it)
			println 'imageInputStream == ' + imageInputStream
			def storedImage = ImageIO.read(imageInputStream)
			// just a quick check for the same size
			assertNotNull storedImage
			assertEquals 2, findImageInMongo(it.id)
		}

		// delete the gallery
		def galleries = Gallery.list()
		assertEquals 1, galleries.size()
		gc.params.id = galleries[0].id
		//galleries[0].users.each {
		//	println it.validate()
		//}

		gc.deleteGallery()
		assertNotNull gc.response.redirectedUrl
		assertEquals gc.response.contentAsString, ""

		// make sure all traces of the gallery are gone
		galleries = Gallery.list()
		assertEquals 0, galleries.size()
		def users = GalleryUser.list()
		assertEquals 0, users.size()
		images = Image.list()
		assertEquals 0, images.size()
		def imageSets = ImageSet.list()
		assertEquals 0, imageSets.size()

		// also check mongodb
		assertEquals 0, findImageInMongo(imageId)

	}

	def findImageInMongo(def imageId) {
		int amount = 0
		def query = new BasicDBObject()
		query.put('imageKey', imageId)
		DB db = imageDBService.db
		DBCollection dbCollection = db.getCollection('images')
		DBCursor cursor = dbCollection.find(query)
		if(!cursor.hasNext()) {
			println 'could not find image with image.id==' + imageId as String
		}
		else {
			println 'found image.id == ' + imageId + ' in mongodb.'
			amount++
		}

		// find the uploaded image thumb in mongodb
		dbCollection = db.getCollection('imageThumbs')
		query = new BasicDBObject()
		query.put('imageKey', imageId)
		cursor = dbCollection.find(query)
		if(!cursor.hasNext()) {
			println 'could not find image thumb with image.id==' + imageId 
		}
		else {
			println 'found thumb with imageId == ' + imageId + ' in mongodb.'
			amount++
		}

		return amount
	}

	void testContributeImages() {
		clearAll()
		createNewGallery()
		def gc = new GalleryController()
		def gallery = Gallery.list()[0]
		gc.session.user = GalleryUser.get(gallery.creatorId)
		gc.params.id = gallery.id
		assertNotNull gc.session.user
		gc.contributeImages()
		assertNull gc.response.redirectedUrl
		assertNotNull gc.response.contentAsString
	}

	void testUploadImage() {
		clearAll()
		createNewGallery()
		def gc = new GalleryController()
		def users = GalleryUser.list()
		assertEquals 1, users.size()
		gc.session.user = users[0]
		def inImage = uploadOneImage(gc)
		
		// retrieve the image and verify it is the same as the one before
		def images = Image.list()
		assertEquals 1, images.size()
		def image = images[0]
		println 'image == ' + image
		println 'imageDBService == ' + imageDBService
		def imageInputStream = imageDBService.getImageInputStream(image)
		println 'imageInputStream == ' + imageInputStream
		def storedImage = ImageIO.read(imageInputStream)
		// just a quick check for the same size
		assertEquals inImage.getWidth(), storedImage.getWidth()
		assertEquals inImage.getHeight(), storedImage.getHeight()
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
		// create a gallery and its creator
		GalleryUser creator = new GalleryUser(firstName: "Cook", lastName: "Poo", email: "cook@poo.ie")
		if(!creator.save(flush:true)) {
			creator.errors.each {
				println it
			}
			fail()
		}

		Gallery gallery = new Gallery(date:new Date(), title:"Super cool event", location:"Dortmund", creatorId:creator.id)
		gallery.addToUsers creator
		creator.addToGalleries gallery
		if(!gallery.save(flush:true)) {
			gallery.errors.each {
				println it
			}
			fail()
		}
		if(!creator.save(flush:true)) {
			creator.errors.each {
				println it
			}
			fail()
		}
	}

	protected void clearAll() {
		GalleryUser.list().each {
			it.delete(flush: true)
		}

		assertEquals 0, GalleryUser.list().size()
		assertEquals 0, Gallery.list().size()
		assertEquals 0, Image.list().size()

		// collections in mongodb
		DB db = imageDBService.db
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
		def galleryUser = GalleryUser.list()[0]
		def newImage = new Image()
		gallery.addToImages newImage
		galleryUser.addToImages newImage

		if(!newImage.save(flush: true)) {
			println "Could not save image during test"
			newImage.errors.each {
				println it.toString()
			}
			fail()
			return
		}

		if(!gallery.save(flush: true)) {
			println "Could not save image during test"
			gallery.errors.each {
				println it.toString()
			}
			fail()
			return
		}

		if(!galleryUser.save(flush: true)) {
			println "Could not save image during test"
			galleryUser.errors.each {
				println it.toString()
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
		println 'user == ' + galleryUser
		imageDBService.storeImage(bais, newImage, galleryUser)

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
		println 'user == ' + galleryUser
		imageDBService.storeImageThumbnail(bais, newImage, galleryUser)
		return newImage
	}

}

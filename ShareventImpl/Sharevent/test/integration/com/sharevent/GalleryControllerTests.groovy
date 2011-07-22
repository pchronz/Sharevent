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
		assertEquals beforeSize, 1

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
		assertEquals galleries.size(), 1
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
		assertEquals galleries.size(), 1

		def galleryController = new GalleryController()
		def userId = galleries[0].adminKey
		def users = GalleryUser.list()
		assertEquals users.size(), 2
		def user = GalleryUser.get(galleries[0].adminKey)
		assertNotNull user
		assertEquals user.id, galleries[0].adminKey
		galleryController.session.user = user

		// render contributeImages
		galleryController.params.clear()
		galleryController.params.id = galleryController.session.user.contributedGallery.id
		galleryController.contributeImages()
		assertNull galleryController.response.redirectedUrl
		assertNotNull galleryController.response.contentAsString

		// upload images
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
		// retrieve the image and verify it is the same as the one before
		def images = Image.list()
		assertEquals images.size(), 5
		Image image = null
		// assuming that the freshly added image is the one with the highest id
		images.each {
			if(image?.id < it?.id) {
				image = it
			}
		}
		println 'image == ' + image
		println 'imageDBService == ' + imageDBService
		def imageInputStream = imageDBService.getImageInputStream(image)
		println 'imageInputStream == ' + imageInputStream
		def storedImage = ImageIO.read(imageInputStream)
		// just a quick check for the same size
		assertEquals storedImage.getWidth(), inImage.getWidth()
		assertEquals storedImage.getHeight(), inImage.getHeight()
	}

	void testDeleteImages() {
		// first upload some images

	}

	void testDeleteGallery() {

	}

	void testContributeImages() {

	}

	void testUploadImage() {

	}

	void testLogout() {

	}

	protected void createNewGallery() {
		// create a gallery
		gallery = new Gallery(date:new Date(), title:"Super cool event", location:"Dortmund", creatorFirstName: "Cook", creatorLastName: "Poo", creatorEmail: "cook@poo.ie", adminKey: 1234);

		GalleryUser user = new GalleryUser(firstName:"Lance", lastName:"Hardwood", email:"lance@hardwood.xxx")
		gallery.addToContributors(user)

		ImageSet imageSet = new ImageSet()
		imageSet.addToImages(new Image())
		imageSet.addToImages(new Image())
		imageSet.images.each { image ->
			image.imageSet = imageSet
		}
		user.imageSet = imageSet

		user = new GalleryUser(firstName:"Chew", lastName:"Bakka", email:"chewie@hardwood.xxx", contributedGallery:gallery)
		gallery.addToContributors(user)

		imageSet = new ImageSet()
		imageSet.addToImages(new Image())
		imageSet.addToImages(new Image())
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
	}

}

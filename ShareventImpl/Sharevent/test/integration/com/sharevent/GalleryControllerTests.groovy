package com.sharevent

import grails.test.*
import org.springframework.mock.web.MockMultipartFile
import org.springframework.mock.web.MockMultipartHttpServletRequest
import javax.servlet.http.HttpServletResponse

class GalleryControllerTests extends GroovyTestCase {
	def gallery

    protected void setUp() {
        super.setUp()

    }

    protected void tearDown() {
        super.tearDown()
    }

	void testViewExample() {
		// call the controller action to view the gallery
		GalleryController galleryController = new GalleryController()
		galleryController.viewExample()
	}

	void testCreateGallery() {
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

		// render contributeImages
		galleryController.params.clear()
		galleryController.params.id = galleryController.session.user.contributedGallery.id
		galleryController.contributeImages()
		assertNull galleryController.response.redirectedUrl
		assertNotNull galleryController.response.contentAsString

		// upload images
		galleryController.params.clear()
		def imgContentType = 'image/jpeg'
        def imgContentBytes = '123' as byte[]
        galleryController.metaClass.request = new MockMultipartHttpServletRequest()
        galleryController.request.addFile(new MockMultipartFile('qqfile', 'myImage.jpg', imgContentType, imgContentBytes))
		// TODO load a real image, otherwise the upload action will handle this and the test will pass with a false image
		fail()
        galleryController.uploadImage()
        assertEquals HttpServletResponse.SC_OK, galleryController.response.status
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

		if(!gallery.save(flush:true))
		gallery.errors.each {
			println it
		}
	}

	protected void clearAll() {
		// galleries
		Gallery.list().each {
			it.delete(flush: true)
		}
	}

}

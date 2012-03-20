package com.sharevent

import grails.test.*
import javax.imageio.ImageIO

class ImageDbServiceTests extends GroovyTestCase {

	def imageDBService

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

	void testStoreImageThumbnail() {
		// FYI. Duck typing is AWESOME!
		storeImageAs("ImageThumbnail", "imagethumbs")
	}

	void testStoreImage() {
		// FYI. Duck typing is AWESOME!
		storeImageAs("Image", "images")
	}

	private def storeImageAs(def type, def bucketType) {
		// read in the test image
		def imageUrl = ImageDbServiceTests.class.getResource("/image.jpg")
		assertNotNull imageUrl
		def imageFile = new File(imageUrl.path)
		def imageBytes = imageFile.bytes
		def bais = new ByteArrayInputStream(imageBytes)

		// create users and galleries
		createAll()

		// get any user and image to her gallery
		def user = GalleryUser.list()[0]
		def image = user.images.asList()[0]
		// Madness? This is SPARTAAAAAA!
		imageDBService."store${type}"(bais, image, user)

		// download the file
		def baos = new ByteArrayOutputStream()
		// Madness? This is SPARTAAAAAA!
		baos << new URL("http://s3.amazonaws.com/com.sharevent.${bucketType}/${user.id}/${image.id}.jpg").openStream()
		assertTrue baos.toByteArray().length > 0
		image
	}

	void testGetImageUrl() {
		getUrl("Image", "images")
	}

	void testGetThumbUrl() {
		getUrl("ImageThumb", "imagethumbs")
	}

	private void getUrl(def type, def bucketType) {
		createAll()
		def image = Image.list()[0]
		def imageUrl = imageDBService."get${type}URL"(image)
		assertEquals "http://com.sharevent.${bucketType}.s3.amazonaws.com/${image.galleryUser.id}/${image.id}.jpg", imageUrl
	}

	void testGetImageThumbInputstream() {
		def image = storeImageAs("ImageThumbnail", "imagethumbs")
		def imageInputstream = imageDBService.getImageThumbInputStream(image)
		assertTrue imageInputstream.bytes.length > 0
	}

	void testGetImageInputstream() {
		def image = storeImageAs("Image", "images")
		def imageInputstream = imageDBService.getImageInputStream(image)
		assertTrue imageInputstream.bytes.length > 0
	}

	void testDeleteImage() {
		// read in the test image
		def testImageUrl = ImageDbServiceTests.class.getResource("/image.jpg")
		assertNotNull testImageUrl
		def imageFile = new File(testImageUrl.path)
		def imageBytes = imageFile.bytes
		def bais = new ByteArrayInputStream(imageBytes)

		// create users and galleries
		createAll()

		// get any user and image to her gallery
		def user = GalleryUser.list()[0]
		def image = user.images.asList()[0]
		imageDBService.storeImageThumbnail(bais, image, user)
		imageDBService.storeImage(bais, image, user)
		imageDBService.delete(image)
		def thumbUrl = imageDBService.getImageThumbURL(image)
		def imageUrl = imageDBService.getImageURL(image)

		// try to download the thumbnail
		def baos = new ByteArrayOutputStream()
		def fail = false
		try {
			baos << new URL(thumbUrl).openStream()
		}
		catch(e) {
			fail = true
		}
		assertTrue fail

		// try to download the image
		baos = new ByteArrayOutputStream()
		fail = false
		try {
			baos << new URL(imageUrl).openStream()
		}
		catch(e) {
			fail = true
		}
		assertTrue fail
	}

    private def createAll() {
		def galleryUser = new GalleryUser(firstName: 'Cook', lastName: 'Poo', email: 'cook@poo.com')
		assertNotNull galleryUser.save(flush: true)
		def gallery = new Gallery(title: 'Test Title', location: 'Test Location')
		assertNotNull galleryUser.save(flush: true)
		gallery.creatorId = galleryUser.id
		gallery.addToUsers galleryUser
		if(!gallery.save(flush: true)) {
			gallery.errors.each { error ->
				println error
			}
			fail()
		}
		galleryUser.addToGalleries gallery
		assertNotNull galleryUser.save(flush: true)
		
		def images = []
		(1..2).each {
			def image = new Image()
			images += image

			galleryUser.addToImages image
			gallery.addToImages image
		}

		assertNotNull galleryUser.save(flush: true)
		assertNotNull gallery.save(flush: true)
		images.each { image ->
			assertNotNull image.save(flush: true)
		}

		assertEquals 2, galleryUser.images.size()
		assertEquals 2, gallery.images.size()

		[gallery, galleryUser, images]
    }

}


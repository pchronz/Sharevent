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

//	void testPopulateAll() {
//		// create 3 galleries each w/ 2 users, each w/ 2 images + 1 image per gallery
//		populateAll()
//
//		// check the amount of images associate with each gallery
//		Gallery.list().each { gallery ->
//			assertEquals 6, gallery.images.size()
//		}
//
//		// check the number of images associated with each user
//		GalleryUser.list().each { user ->
//			assertEquals 2, user.images.size()
//		}
//
//		assertEquals 3, Gallery.list().size()
//		assertEquals 9, GalleryUser.list().size()
//		assertEquals 18, Image.list().size()
//	}
//
//	void testRemoveAll() {
//		// create a few galleries, users and images
//		populateAll()
//
//		// for all users remove all images
//		GalleryUser.list().each { user ->
//			def images = []
//			images += user.images
//			images.each { image ->
//				def galleryUser = image.galleryUser
//				def gallery = image.gallery
//				if(imageService.deleteImage(image)) {
//					image.errors.each {
//						println it
//					}
//					fail()
//				}
//				assertNotNull galleryUser.save(flush: true)
//				assertNotNull gallery.save(flush: true)
//			}
//			assertEquals 0, GalleryUser.get(user.id).images.size()
//		}
//		
//		Gallery.list().each { gallery ->
//			assertEquals 0, gallery.images.size()
//		}
//
//		assertEquals 0, Image.list().size()
//		assertEquals 9, GalleryUser.list().size()
//		assertEquals 3, Gallery.list().size()
//	}
//		
//
    private def createAll() {
		def galleryUser = new GalleryUser(firstName: 'Cook', lastName: 'Poo', email: 'cook@poo.com')
		assertNotNull galleryUser.save(flush: true)
		def gallery = new Gallery(date: new Date(), title: 'Test Title', location: 'Test Location')
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
//
//	private def populateAll() {
//		// create 3 galleries, users, images
//		(1..3).each {
//			Gallery gallery = null
//			GalleryUser galleryUser = null
//			def images = null
//			(gallery, galleryUser, images) = createAll()
//
//			// add two more users, each with two images to the gallery
//			(1..2).each {
//				GalleryUser user = new GalleryUser(firstName: "Cook2", lastName: "Poo2", email: "cook2@poo.com")
//				user.addToGalleries(gallery)
//				gallery.addToUsers(user)
//				if(!user.save(flush: true)) {
//					user.errors.each { err ->
//						println err
//					}
//					fail()
//				}
//				(1..2).each {
//					Image newImage = new Image() 
//					user.addToImages( newImage )
//					gallery.addToImages(newImage)
//					if(!newImage.save(flush: true)) {
//						newImage.errors.each {err ->
//							println err
//						}
//						fail()
//					}
//				}
//				assertNotNull gallery.save(flush: true)
//				assertNotNull user.save(flush: true)
//				assertEquals 2, GalleryUser.get(user.id).images.size()
//			}
//		}
//	}

}


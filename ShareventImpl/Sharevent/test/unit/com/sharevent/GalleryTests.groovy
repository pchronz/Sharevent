package com.sharevent

import grails.test.*
import com.sharevent.Gallery
import com.sharevent.GalleryUser

class GalleryTests extends GrailsUnitTestCase {

	private users = null
	private galleries = null

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testGalleryCreation() {
		mockDomains()
		GalleryUser creator = new GalleryUser(firstName: "Cook", lastName: "Poo", email: "cook@poo.ie")

		Gallery gallery = new Gallery(date:new Date(), title:"Super cool event", location:"Dortmund")
		gallery.creatorId = "placeholder"
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
		gallery.creatorId = creator.id
		if(!gallery.save(flush:true)) {
			gallery.errors.each {
				println it
			}
			fail()
		}
		assertEquals 1, GalleryUser.list().size()
		assertEquals 1, Gallery.list().size()
		GalleryUser.list().each { userIt ->
			assertEquals 1, userIt.galleries.size()
			userIt.galleries.each { galleryIt ->
				assertEquals galleryIt.creatorId, userIt.id
			}
		}
		Gallery.list().each { galleryIt ->
			assertEquals 1, galleryIt.users.size()
			galleryIt.users.each { userIt ->
				assertEquals userIt.id, galleryIt.creatorId
				assertFalse userIt.id == "placeholder"
			}
		}
    }

    void testNoUser() {
		mockDomains()
		GalleryUser creator = new GalleryUser(firstName: "Cook", lastName: "Poo", email: "cook@poo.ie")

		Gallery gallery = new Gallery(date:new Date(), title:"Super cool event", location:"Dortmund")
		gallery.creatorId = "placeholder"
		creator.addToGalleries gallery
		if(gallery.save(flush:true)) {
			fail()
		}
    }


    void testMultipleUsersMultipleGalleries() {
		mockDomains()

		// 1 contributor, 1 creator per gallery (4 users total)
		// create the 1st gallery
		Gallery gallery1
		GalleryUser creator1
		(creator1, gallery1) = createGalleryAndCreator()

		// create a 2nd user and add to the gallery
		def user1 = addNewUser(gallery1)

		// create the 2nd gallery
		Gallery gallery2
		GalleryUser creator2
		(creator2, gallery2) = createGalleryAndCreator()
		// create a 2nd user and add to the gallery
		def user2 = addNewUser(gallery2)

		// test results
		assertEquals 4, GalleryUser.list().size()
		assertEquals 2, Gallery.list().size()
		Gallery.list().each { galleryIt ->
			assertEquals 2, galleryIt.users.size()
			assertNotNull GalleryUser.get(galleryIt.creatorId)
		}

		GalleryUser.list().each { userIt ->
			assertEquals 1, userIt.galleries.size()
		}
    }

    void testMultipleUsersCrossMultipleGalleries() {
		mockDomains()

		// 1 contributor, 1 creator per gallery (2 users total)
		// create the 1st gallery
		Gallery gallery1
		GalleryUser creator1
		(creator1, gallery1) = createGalleryAndCreator()

		// create the 2nd gallery
		Gallery gallery2
		GalleryUser creator2
		(creator2, gallery2) = createGalleryAndCreator()

		// cross-add the creators as contributors
		gallery1.addToUsers creator2
		creator2.addToGalleries gallery1
		gallery2.addToUsers creator1
		creator1.addToGalleries gallery2

		// test results
		assertEquals 2, GalleryUser.list().size()
		assertEquals 2, Gallery.list().size()
		Gallery.list().each { galleryIt ->
			assertEquals 2, galleryIt.users.size()
			assertNotNull GalleryUser.get(galleryIt.creatorId)
		}

		GalleryUser.list().each { userIt ->
			assertEquals 2, userIt.galleries.size()
		}
    }

	private List createGalleryAndCreator() {
		GalleryUser creator = new GalleryUser(firstName: "Cook", lastName: "Poo", email: "cook@poo.ie")

		Gallery gallery = new Gallery(date:new Date(), title:"Super cool event", location:"Dortmund")
		gallery.creatorId = "placeholder"
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
		gallery.creatorId = creator.id
		if(!gallery.save(flush:true)) {
			gallery.errors.each {
				println it
			}
			fail()
		}
		[creator, gallery]	
	}

	private GalleryUser addNewUser(Gallery gallery) {
		GalleryUser user = new GalleryUser(firstName: "Cook", lastName: "Poo", email: "cook@poo.ie")

		gallery.addToUsers user
		user.addToGalleries gallery
		if(!user.save(flush:true)) {
			user.errors.each {
				println it
			}
			fail()
		}
		user
	}

	private void mockDomains() {
		users = []
		mockDomain(GalleryUser, users)
		galleries = [] 
		mockDomain(Gallery, galleries)
	}
}

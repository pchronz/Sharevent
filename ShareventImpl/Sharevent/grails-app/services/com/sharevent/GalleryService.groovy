package com.sharevent

import org.springframework.orm.hibernate3.SessionFactoryUtils
import org.hibernate.FlushMode

class GalleryService {

    static transactional = true
	
	def imageDBService
	def imageService
	def sessionFactory

    def deleteGallery(def galleryId) {
		// XXX I actually do not really have an idea what the following does
		// XXX it seems to work for now...
		// XXX w/o setting the session to FlushMode.COMMIT, deleting does not work
		// TODO refrain from deleting data, mark as deleted instead
		def session = SessionFactoryUtils.getSession(sessionFactory, true);
		session.setFlushMode(FlushMode.COMMIT)
		// XXX

		def gallery = Gallery.get(galleryId)
		if(!gallery) return

		// delete all images associated with this gallery
		def images = []
		images += gallery.images
		images.each { image ->
			imageDBService.delete(image)
			imageService.deleteImage(image)
		}
		
		def galleryUsers = []
		galleryUsers += gallery.users

		// remove the gallery from all users
		galleryUsers.each { user ->
			user.removeFromGalleries gallery
		}

		session = SessionFactoryUtils.getSession(sessionFactory, true);
		session.setFlushMode(FlushMode.COMMIT)
		// delete the gallery itself
		if(gallery.delete(flush: true, failOnError: true)) {
			log.error 'Could not delete gallery'
			gallery.errors.each {
				println it
			}
		}

		galleryUsers.each { user ->
			if(!user.save(flush: true, failOnError: true)) {
				user.errors.each {
					println  it
				}
			}
		}
    }
}

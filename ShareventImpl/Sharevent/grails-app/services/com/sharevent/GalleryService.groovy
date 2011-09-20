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

		// remove the gallery from all users
		gallery.users.each { user ->
			user.removeFromGalleries gallery
			if(!user.save(flush: true)) {
				user.errors.each {
					println  it
				}
			}
		}

		// delete all images associated with this gallery
		def images = []
		images += gallery.images
		images.each { image ->
			imageDBService.delete(image)
			imageService.deleteImage(image)
		}

		if(!gallery.delete(flush: true)) {
			log.error 'Could not delete gallery'
			gallery.errors.each {
				println it
			}
		}
    }
}

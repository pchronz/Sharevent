package com.sharevent

class GalleryService {

    static transactional = true
	
	def imageDBService

    def deleteGallery(def galleryId) {
		// XXX I actually do not really have an idea what the following does
		// XXX it seems to work for now...
		// XXX w/o setting the session to FlushMode.COMMIT, deleting does not work
		def session = SessionFactoryUtils.getSession(sessionFactory, true);
		session.setFlushMode(FlushMode.COMMIT)
		// XXX

		// also delete all stored images and thumbnails
		// TODO this needs to be reworked for multiple contributions
		// add an association class for ImageSet, GalleryUser and Gallery
		Gallery.get(galleryId).users.each {
			it.imageSet.images.each {
				imageDBService.delete(it)
			}
		}

    	// TODO secure this action for the admin user
		def gallery = Gallery.get(galleryId)
		def creator = GalleryUser.get(gallery.creatorId)
        creator.removeFromGalleries gallery
		if(!creator.save(flush: true)) {
			creator.errors.each {
				println it
			}
		}

		def c = GalleryUser.createCriteria()
		def contributors = c.list {
			galleries {
				idEq(gallery.id)
			}
		}
		contributors.each { contributor ->
			contributor.removeFromGalleries gallery
			if(!contributor.save(flush:true)) {
				contributor.errors.each { error ->
					println error
				}
			}
		}
		//def galleryUsers = gallery.users
		//galleryUsers.each {
		//	gallery.removeFromUsers it
		//}

		c = GalleryUser.createCriteria()
		contributors = c.list {
			galleries {
				idEq(gallery.id)
			}
		}
		println 'number of occurences of gallery to delete: ' + contributors.size()
		if(!Gallery.get(galleryId).delete(flush: true)) {
			log.error 'Could not delete gallery'
			gallery.errors.each {
				println it
			}
		}
    }
}

package com.sharevent

import org.springframework.orm.hibernate3.SessionFactoryUtils
import org.hibernate.FlushMode

class ImageService {

	def sessionFactory
	
    static transactional = true

	def deleteImage(Image image) {
		// TODO refrain from deleting data, mark as deleted instead
		def session = SessionFactoryUtils.getSession(sessionFactory, true);
		session.setFlushMode(FlushMode.COMMIT)

		def galleryUser = image.galleryUser
		def gallery = image.gallery
		galleryUser.removeFromImages(image)
		gallery.removeFromImages(image)

		image.delete(flush: true)
	}
}


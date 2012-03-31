package com.sharevent

class GallerySubscription {

	Boolean needsUpdate
	String email
	// Grails sucks ass. Not possible to call createLink without major hacks outside a controller
	String url
	String unsubscribe
	
	static belongsTo = [gallery: Gallery]

    static constraints = {
		email(blank: false, email: true)
    }

	static mapping = {
		version false
	}
}

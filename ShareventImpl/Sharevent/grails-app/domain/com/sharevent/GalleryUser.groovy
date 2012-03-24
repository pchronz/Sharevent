package com.sharevent

class GalleryUser {
	
	static String INCOGNITO = 'INCOGNITO'
	static String INCOGNITO_MAIL = 'INCOGNITO@INCOGNITO.COM'

    // need to add the id explicitly as String for the UUID-generator to work
    String id

    String firstName
    String lastName
    String email

    static hasMany = [galleries:Gallery, images:Image]

    static constraints = {
        firstName(blank:false)
        lastName(blank:false)
        email(email:true, blank:false, nullable:false)
		galleries(nullable: true)
		images(nullable: true)
    }

    static mapping = {
		id generator: 'uuid'
		version false
    }
}


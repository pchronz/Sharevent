package com.sharevent

class GalleryUser {

    String id

    String firstName
    String lastName
    String email

    ImageSet imageSet

    static hasMany = [galleries:Gallery]

    static constraints = {
        firstName(blank:false)
        lastName(blank:false)
        email(email:true, blank:false, nullable:false)
        imageSet(nullable:true)
		galleries(nullable: true)
    }

    static mapping = {
		id generator: 'uuid'
    }
}


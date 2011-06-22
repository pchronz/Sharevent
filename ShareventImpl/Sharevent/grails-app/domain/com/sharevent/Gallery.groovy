package com.sharevent

class Gallery {

    // need to add the id explicitly as String for the UUID-generator to work
    String id

    Date date
    String title
    String location

    String creatorFirstName
    String creatorLastName
    String creatorEmail

    String adminKey

    static hasMany = [contributors:GalleryUser]

    static constraints = {
        date(nullable:false)
        title(blank:false)
        location(blank:false)
        creatorFirstName(blank:false)
        creatorLastName(blank: false)
        creatorEmail(blank: false, email: true)
        adminKey(nullable: false)
    }

    static mapping = {
	id generator: 'uuid'
    }
}

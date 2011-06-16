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

    int adminKey

    static hasMany = [contributors:GalleryUser]

    static constraints = {
        date(nullable:false)
        title(nullable:false)
        location(nullable:false)
        creatorFirstName(nullable:false)
        creatorLastName(nullable: false)
        creatorEmail(nullable: false)
        adminKey(nullable: false)
    }

    static mapping = {
	id generator: 'uuid'
    }
}

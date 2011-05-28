package com.sharevent

class Gallery {

    Date date
    String title
    String location

    String creatorFirstName
    String creatorLastName
    String creatorEmail

    static hasMany = [contributors:GalleryUser]

    static constraints = {
        date(nullable:false)
        title(nullable:false)
        location(nullable:false)
        creatorFirstName(nullable:false)
        creatorLastName(nullable: false)
        creatorEmail(nullable: false)
    }
}

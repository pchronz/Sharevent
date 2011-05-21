package com.sharevent

class Gallery {

    Date date
    String title
    String location

    static belongsTo = [creator:GalleryUser]
    static hasMany = [contributor:GalleryUser]

    static constraints = {
        date(nullable:false)
        title(nullable:false)
        location(nullable:false)
    }
}

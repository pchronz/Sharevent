package com.sharevent

class ImageSet {

    static hasMany = [images:Image]

    static belongsTo = [galleryUser:GalleryUser]

    static constraints = {
		galleryUser(nullable: false)
    }

    static mapping = {
        images cascade: "all-delete-orphan"
    }
}


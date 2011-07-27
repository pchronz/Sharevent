package com.sharevent

class ImageSet {

    static hasMany = [images:Image]

    static belongsTo = [galleryUser:GalleryUser]

    static constraints = {
    }

    static mapping = {
        images cascade: "all-delete-orphan"
		galleryUser(nullable: false)
    }
}

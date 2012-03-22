package com.sharevent

class Image {

	Date dateCreated
    static belongsTo = [gallery:Gallery, galleryUser:GalleryUser]

    static constraints = {
		gallery(nullable: false)
		galleryUser(nullable: false)
    }

}


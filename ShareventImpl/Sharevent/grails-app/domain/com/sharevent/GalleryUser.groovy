package com.sharevent

class GalleryUser {

    String firstName
    String lastName
    String email

    ImageSet imageSet

    static belongsTo = [contributedGallery:Gallery]

    static constraints = {
        firstName(blank:false)
        lastName(blank:false)
        email(email:true, blank:false, nullable:false)
        contributedGallery(nullable:false)
        imageSet(nullable:true)
    }
}

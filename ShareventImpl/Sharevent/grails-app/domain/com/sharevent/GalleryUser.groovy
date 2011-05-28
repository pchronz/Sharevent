package com.sharevent

class GalleryUser {

    String firstName
    String lastName
    String email

    Gallery contributedGallery
    ImageSet imageSet

    static constraints = {
        firstName(blank:false)
        lastName(blank:false)
        email(email:true, blank:false, nullable:false)
        createdGallery(nullable:true)
        contributedGallery(nullable:true)
        imageSet(nullable:true)
    }
}

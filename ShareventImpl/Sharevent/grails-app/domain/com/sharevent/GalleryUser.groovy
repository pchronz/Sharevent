package com.sharevent

class GalleryUser {

    String firstName
    String lastName
    String email

    def createdGallery
    def contributedGallery
    def imageSet

    static constraints = {
        firstName(blank:false)
        lastName(blank:false)
        email(email:true, blank:false)
        createdGallery(nullable:true)
    }
}

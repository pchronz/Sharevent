package com.sharevent

class Image {

    static belongsTo = [imageSet:ImageSet]

    static constraints = {
        images cascade: "all-delete-orphan"
    }
}

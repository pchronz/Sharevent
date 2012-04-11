package com.sharevent

import grails.converters.JSON

class RESTController {

    def index() { 
		println "index called"
		render status: 200
	}

	def image = {
		def userId = params.userId
		def imageId = params.imageId
		if(!userId || !imageId) {
			render status: 400, text: [message: "userId and imageId not set."]
			return
		}
		println "userId == " + userId
		println "imageId == " + imageId

		// TODO download the full image from S3
		// TODO process the image
		// TODO upload the processed image

		render status: 200, text: [foo: "foo"] as JSON
	}
}

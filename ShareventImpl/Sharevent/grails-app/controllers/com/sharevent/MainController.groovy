package com.sharevent

import grails.plugins.springsecurity.Secured

class MainController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
		redirect(action:"view", params:params)
    }

    def view = {
		def userGalleries = []
		def adminGalleries = []
		request.cookies.each { cookie ->
			if(cookie.value == "admin") {
				def gallery = Gallery.findById(cookie.name)
				if(gallery) adminGalleries << gallery
			}
			else {
				def gallery = Gallery.findById(cookie.name)
				if(gallery) userGalleries << gallery
			}
		}
		render view: "view", model: [adminGalleries: adminGalleries, userGalleries: userGalleries]
    }
}

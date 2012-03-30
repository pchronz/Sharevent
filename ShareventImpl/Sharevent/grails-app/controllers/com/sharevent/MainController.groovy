package com.sharevent

import grails.plugins.springsecurity.Secured
import javax.servlet.http.Cookie

class MainController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
		redirect(action:"view", params:params)
    }

    def view = {
		def userGalleries = []
		def adminGalleries = []
		request.cookies?.each { cookie ->
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

	def removeCookie = {
		if(!params.id) {
			redirect view: 'view'
			return
		}

		def id = params.id 

		def c = new Cookie(id, "user")
		c.maxAge = 0
		c.path = "/"
		response.addCookie(c)
		redirect action: 'view'
	}

}

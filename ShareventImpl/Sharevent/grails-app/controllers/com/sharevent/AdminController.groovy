package com.sharevent

import grails.plugins.springsecurity.Secured

class AdminController {

    def index() {
		redirect action: "view"
	}

	@Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
	def view() {
		def galleries = Gallery.list()
		render view: 'view', model: [galleries: galleries]
	}
}

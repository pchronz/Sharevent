package com.sharevent

import grails.plugins.springsecurity.Secured

class MainController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
		if(flash.message)
			flash.message = flash.message
        redirect(action:"view", params:params)
    }

    def view = {
		log.info 'info'
    }
}

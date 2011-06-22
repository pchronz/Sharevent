package com.sharevent

import grails.plugins.springsecurity.Secured

class MainController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action:"view", params:params)
    }

    def view = {

    }
}

package com.sharevent

import grails.converters.JSON

class GalleryUserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [galleryUserInstanceList: GalleryUser.list(params), galleryUserInstanceTotal: GalleryUser.count()]
    }

    def create = {
        def galleryUserInstance = new GalleryUser()
        galleryUserInstance.properties = params
        return [galleryUserInstance: galleryUserInstance]
    }

    def save = {
        def galleryUserInstance = new GalleryUser(params)
        if (galleryUserInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'galleryUser.label', default: 'GalleryUser'), galleryUserInstance.id])}"
            redirect(action: "show", id: galleryUserInstance.id)
        }
        else {
            render(view: "create", model: [galleryUserInstance: galleryUserInstance])
        }
    }

    def show = {
        def galleryUserInstance = GalleryUser.get(params.id)
        if (!galleryUserInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'galleryUser.label', default: 'GalleryUser'), params.id])}"
            redirect(action: "list")
        }
        else {
            [galleryUserInstance: galleryUserInstance]
        }
    }

    def edit = {
        def galleryUserInstance = GalleryUser.get(params.id)
        if (!galleryUserInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'galleryUser.label', default: 'GalleryUser'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [galleryUserInstance: galleryUserInstance]
        }
    }

    def update = {
        def galleryUserInstance = GalleryUser.get(params.id)
        if (galleryUserInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (galleryUserInstance.version > version) {
                    
                    galleryUserInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'galleryUser.label', default: 'GalleryUser')] as Object[], "Another user has updated this GalleryUser while you were editing")
                    render(view: "edit", model: [galleryUserInstance: galleryUserInstance])
                    return
                }
            }
            galleryUserInstance.properties = params
            if (!galleryUserInstance.hasErrors() && galleryUserInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'galleryUser.label', default: 'GalleryUser'), galleryUserInstance.id])}"
                redirect(action: "show", id: galleryUserInstance.id)
            }
            else {
                render(view: "edit", model: [galleryUserInstance: galleryUserInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'galleryUser.label', default: 'GalleryUser'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def galleryUserInstance = GalleryUser.get(params.id)
        if (galleryUserInstance) {
            try {
                galleryUserInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'galleryUser.label', default: 'GalleryUser'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'galleryUser.label', default: 'GalleryUser'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'galleryUser.label', default: 'GalleryUser'), params.id])}"
            redirect(action: "list")
        }
    }


    // USER DEFINED ACTIONS FOLLOW
    def createNew = {
	def user = new GalleryUser(params)
	user.imageSet = new ImageSet()
	user.imageSet.galleryUser = user
	def gallery = Gallery.get(params.id)
	gallery.addToContributors(user)
	user.contributedGallery = gallery
	if(!gallery.save(flush:true)) {
		gallery.errors.each{
			log.error it
		}
	}

	session.userId = user.id
	session.isLoggedIn = true
	session.galleryId = gallery.id

        render(text: [sucess: true] as JSON, contentType: 'text/JSON')
    }
}

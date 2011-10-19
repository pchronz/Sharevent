package com.sharevent

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

class GalleryUserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def index = {
        redirect(action: "list", params: params)
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [galleryUserInstanceList: GalleryUser.list(params), galleryUserInstanceTotal: GalleryUser.count()]
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def create = {
        def galleryUserInstance = new GalleryUser()
        galleryUserInstance.properties = params
        return [galleryUserInstance: galleryUserInstance]
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
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

    @Secured(['IS_AUTHENTICATED_FULLY'])
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

    @Secured(['IS_AUTHENTICATED_FULLY'])
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

    @Secured(['IS_AUTHENTICATED_FULLY'])
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

    @Secured(['IS_AUTHENTICATED_FULLY'])
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
    	if(session.user != null) {
	    def loggedInUser = session.user
	    if(loggedInUser?.contributedGallery.id == params.id) {
	        redirect(controller: 'gallery', action: 'view', params:[id: params.id])
	        return
	    }
	    else {
	        session.user = null
	    }
	}

	def gallery = Gallery.get(params.id)
	if(gallery == null) {
	    redirect(controller: 'main')
	    return
	}
	if(session.userCreationStarted == null) {
	    session.userCreationStarted = true
	    render(view: 'createNew', model: [galleryInstance: gallery])
	    return
	}
	def user = new GalleryUser(params)
	user.contributedGallery = gallery
	if(!user.validate()) {
		log.error "Could not create a new user. Errors follow."
		user.errors.each {
			log.error it.toString()
		}
		render(view: 'createNew', model: [galleryInstance: gallery, user: user])
		return
	}
	//user.imageSet = new ImageSet()
	//user.imageSet.galleryUser = user
	user.save(flush: true)
	gallery.addToContributors(user)
	if(!gallery.save(flush:true)) {
		log.error "Could not save new user. Errors follow."
		gallery.errors.each{
			log.error it
		}
		render(view: 'createNew', model: [galleryInstance: gallery, user: user])
		return
	}

	session.user = user

	session.userCreationStarted = null

    	redirect(controller: 'gallery', action: 'contributeImages', params: [id: gallery.id])
    }

    /**
    * TODO: wait for real authentification
    */
    def inbox = {
        def galUser = GalleryUser.findByEmail('cook@poo.com')
        def ownGalleries = Gallery.findAllByCreatorId(galUser.id)

        println "${ownGalleries} : ${galUser.galleries.size()}"
        [galUser:galUser,ownGalleries:ownGalleries]
    }

    /**
    * TODO: only for allowed user, and should be a service method
    */
    def loadGalleryImages = {
        println "loadGalleryImages: ${params.id}"
        def gallery = Gallery.get(params.id)
        render(template:"gImages", model:[gImages: gallery.images])
    }

    def viewOwner = {
        def galUser = GalleryUser.get(params.id)
        render "<a href=\"mailto:${galUser.email}\">owned by ${galUser.firstName} ${galUser.lastName}</a>"
    }
}

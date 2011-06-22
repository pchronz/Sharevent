package com.sharevent

import grails.plugins.springsecurity.Secured

class ImageSetController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def index = {
        redirect(action: "list", params: params)
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [imageSetInstanceList: ImageSet.list(params), imageSetInstanceTotal: ImageSet.count()]
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def create = {
        def imageSetInstance = new ImageSet()
        imageSetInstance.properties = params
        return [imageSetInstance: imageSetInstance]
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def save = {
        def imageSetInstance = new ImageSet(params)
        if (imageSetInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'imageSet.label', default: 'ImageSet'), imageSetInstance.id])}"
            redirect(action: "show", id: imageSetInstance.id)
        }
        else {
            render(view: "create", model: [imageSetInstance: imageSetInstance])
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def show = {
        def imageSetInstance = ImageSet.get(params.id)
        if (!imageSetInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'imageSet.label', default: 'ImageSet'), params.id])}"
            redirect(action: "list")
        }
        else {
            [imageSetInstance: imageSetInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def edit = {
        def imageSetInstance = ImageSet.get(params.id)
        if (!imageSetInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'imageSet.label', default: 'ImageSet'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [imageSetInstance: imageSetInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def update = {
        def imageSetInstance = ImageSet.get(params.id)
        if (imageSetInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (imageSetInstance.version > version) {
                    
                    imageSetInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'imageSet.label', default: 'ImageSet')] as Object[], "Another user has updated this ImageSet while you were editing")
                    render(view: "edit", model: [imageSetInstance: imageSetInstance])
                    return
                }
            }
            imageSetInstance.properties = params
            if (!imageSetInstance.hasErrors() && imageSetInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'imageSet.label', default: 'ImageSet'), imageSetInstance.id])}"
                redirect(action: "show", id: imageSetInstance.id)
            }
            else {
                render(view: "edit", model: [imageSetInstance: imageSetInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'imageSet.label', default: 'ImageSet'), params.id])}"
            redirect(action: "list")
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def delete = {
        def imageSetInstance = ImageSet.get(params.id)
        if (imageSetInstance) {
            try {
                imageSetInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'imageSet.label', default: 'ImageSet'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'imageSet.label', default: 'ImageSet'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'imageSet.label', default: 'ImageSet'), params.id])}"
            redirect(action: "list")
        }
    }
}

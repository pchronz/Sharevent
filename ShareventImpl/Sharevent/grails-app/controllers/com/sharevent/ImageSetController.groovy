package com.sharevent

class ImageSetController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [imageSetInstanceList: ImageSet.list(params), imageSetInstanceTotal: ImageSet.count()]
    }

    def create = {
        def imageSetInstance = new ImageSet()
        imageSetInstance.properties = params
        return [imageSetInstance: imageSetInstance]
    }

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

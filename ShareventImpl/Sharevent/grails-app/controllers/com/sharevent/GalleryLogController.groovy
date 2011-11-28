package com.sharevent

import grails.plugins.springsecurity.Secured

class GalleryLogController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def index = {
        redirect(action: "list", params: params)
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [galleryLogInstanceList: GalleryLog.list(params), galleryLogInstanceTotal: GalleryLog.count()]
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def show = {
        def galleryLogInstance = GalleryLog.get(params.id)
        if (!galleryLogInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'galleryLog.label', default: 'GalleryLog'), params.id])}"
            redirect(action: "list")
        }
        else {
            [galleryLogInstance: galleryLogInstance]
        }
    }

}

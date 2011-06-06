package com.sharevent

import java.util.zip.ZipOutputStream
import org.apache.tools.ant.taskdefs.Zip
import java.util.zip.ZipEntry

class GalleryController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [galleryInstanceList: Gallery.list(params), galleryInstanceTotal: Gallery.count()]
    }

    def create = {
        def galleryInstance = new Gallery()
        galleryInstance.properties = params
        return [galleryInstance: galleryInstance]
    }

    def save = {
        def galleryInstance = new Gallery(params)
        if (galleryInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'gallery.label', default: 'Gallery'), galleryInstance.id])}"
            redirect(action: "show", id: galleryInstance.id)
        }
        else {
            render(view: "create", model: [galleryInstance: galleryInstance])
        }
    }

    def show = {
        def galleryInstance = Gallery.get(params.id)
        if (!galleryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'gallery.label', default: 'Gallery'), params.id])}"
            redirect(action: "list")
        }
        else {
            [galleryInstance: galleryInstance]
        }
    }

    def edit = {
        def galleryInstance = Gallery.get(params.id)
        if (!galleryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'gallery.label', default: 'Gallery'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [galleryInstance: galleryInstance]
        }
    }

    def update = {
        def galleryInstance = Gallery.get(params.id)
        if (galleryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (galleryInstance.version > version) {
                    
                    galleryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'gallery.label', default: 'Gallery')] as Object[], "Another user has updated this Gallery while you were editing")
                    render(view: "edit", model: [galleryInstance: galleryInstance])
                    return
                }
            }
            galleryInstance.properties = params
            if (!galleryInstance.hasErrors() && galleryInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'gallery.label', default: 'Gallery'), galleryInstance.id])}"
                redirect(action: "show", id: galleryInstance.id)
            }
            else {
                render(view: "edit", model: [galleryInstance: galleryInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'gallery.label', default: 'Gallery'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def galleryInstance = Gallery.get(params.id)
        if (galleryInstance) {
            try {
                galleryInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'gallery.label', default: 'Gallery'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'gallery.label', default: 'Gallery'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'gallery.label', default: 'Gallery'), params.id])}"
            redirect(action: "list")
        }
    }

    // ********* OWN ACTIONS BELOW *******

    def view = {
        def galleryInstance = Gallery.get(params.id)
        println params

        if(galleryInstance)
            render(view:"view", model:[galleryInstance:galleryInstance])
        else
            redirect(controller:"main")
    }

    def createFree = {

    }

    def createPremium = {

    }

    def share = {
        println params
        // TODO the creator user does not exist anymore, read the properties directly
        def creator = new GalleryUser(params)
        def gallery = new Gallery(params)
        gallery.creator = creator
        creator.save(flush:true)
        gallery.save(flush:true)
        render(view:"share", model:[galleryInstance:gallery])
    }

    def pay = {

    }

    def download = {

        // first get the selected image ids
        def images = []
        params.each {
            if(it.key instanceof java.lang.String) {
                if(it.key.startsWith("image")) {
                    // Assuming that Grails is providing only selected checkboxes!
                    String imageId = it.key
                    images.add(imageId.split("_")[1])
                }
            }
        }


        // the build the paths for the image ids
        def imagePaths = []
        images.each {
            String imagePath = "/Users/peterandreaschronz/Documents/business/Sharevent/ImageDB/" + params.id + "/" + it + ".jpg"
            imagePaths.add(imagePath)
        }

        // now zip the selected images to the response stream
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(response.outputStream))
        def data = new byte[2048]
        def galleryInstance = Gallery.get(params.id)
        imagePaths.each { imagePath ->
            // check whether the gallery's path also works on windows
            String imageId = imagePath.split("/")[-1]
            String zipPath = galleryInstance.title + "/" + imageId
            println zipPath
            FileInputStream fi = new FileInputStream(imagePath)
            BufferedInputStream origin = new BufferedInputStream(fi, 2048)
            ZipEntry entry = new ZipEntry(zipPath)
            zos.putNextEntry(entry)
            int count
            while((count = origin.read(data, 0, 2048)) != -1) {
               zos.write(data, 0, count);
            }
            origin.close();
        }
        zos.close()
    }

}

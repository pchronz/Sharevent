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
        if(params.containsKey("key")) {
            try {
                if(params.key.toString().toInteger() == galleryInstance.adminKey) {
                    session.isAdmin = true
                    session.galleryId = galleryInstance.id
                }
            }
            catch(Exception e) {
                e.printStackTrace()
            }
        }

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
        def gallery = new Gallery(params)
        gallery.adminKey = new Random(System.currentTimeMillis()).nextInt()
        gallery.creatorFirstName = params.firstName
        gallery.creatorLastName = params.lastName
        gallery.creatorEmail = params.email
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


        // then build the paths for the image ids
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

    def deleteImages = {
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

        // remove the corresponding entries in the database
        images.each {
            Image image = Image.get(it.toString().toLong())
            image.imageSet.removeFromImages(image)
        }

        // if some user does not have anymore images, delete the user as well
        def galleryInstance = Gallery.get(params.id)
        def contributorsCopy = []
        contributorsCopy += galleryInstance.contributors
        contributorsCopy.each {
            if(it.imageSet.images.size() == 0) {
                galleryInstance.removeFromContributors(it)
                it.delete(flush:true)
            }
        }


        // then build the paths for the image ids
        def imagePaths = []
        images.each {
            String imagePath = "/Users/peterandreaschronz/Documents/business/Sharevent/ImageDB/" + params.id + "/" + it + ".jpg"
            imagePaths.add(imagePath)
        }

        // TODO finally delete the images from disk

        flash.message = images.size() + " images have been deleted."


        // show the gallery again
        redirect(controller: "gallery", action: "view", params: [id: params.id])
    }

    def deleteGallery = {
        Gallery.get(params.id).delete(flush: true)

        flash.message = "The gallery has been deleted. Would you maybe like to create a new one?"

        redirect(controller: "main", action: "index")
    }

}

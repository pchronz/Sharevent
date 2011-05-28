import com.sharevent.Gallery
import com.sharevent.GalleryUser
import com.sharevent.ImageSet
import com.sharevent.Image

class BootStrap {

    def init = { servletContext ->
        def gallery = new Gallery(date:new Date(), title:"Super cool event", location:"Dortmund");
        GalleryUser userCreator = new GalleryUser(firstName:"Cook", lastName:"Poo", email:"cook.poo@cookapoo.kr", createdGallery:gallery)
        if(!gallery.save(flush:true))
            gallery.errors.each {
                println it
            }

        if(!userCreator.save(flush:true))
            userCreator.errors.each {
                println it
            }

        GalleryUser user = new GalleryUser(firstName:"Lance", lastName:"Hardwood", email:"lance@hardwood.xxx")
        gallery.contributors = []
        gallery.contributors.add user
        if(!user.save(flush:true))
            user.errors.each {
                println it
            }
        user = new GalleryUser(firstName:"Chew", lastName:"Bakka", email:"chewie@hardwood.xxx")
        gallery.contributors.add user

        gallery.contributors.add userCreator

        ImageSet imageSet = new ImageSet()
        imageSet.images = []
        imageSet.images.add(new Image())
        imageSet.images.add(new Image())
        imageSet.images.add(new Image())
        imageSet.images.add(new Image())
        imageSet.images.add(new Image())
        imageSet.images.each { image ->
            image.imageSet = imageSet
        }
        userCreator.imageSet = imageSet
        imageSet.galleryUser = userCreator
        if(!imageSet.save(flush:true))
            imageSet.errors.each {
                println it
            }

        if(!userCreator.save(flush:true)) {
            userCreator.errors.each {
                println it
            }
        }

        imageSet = new ImageSet()
        imageSet.images = []
        imageSet.images.add(new Image())
        imageSet.images.add(new Image())
        imageSet.images.add(new Image())
        imageSet.images.add(new Image())
        imageSet.images.add(new Image())
        imageSet.images.each { image ->
            image.imageSet = imageSet
        }
        user.imageSet = imageSet
        imageSet.galleryUser = user
        if(!user.save(flush:true)) {
            user.errors.each {
                println it
            }
        }
        if(!gallery.save(flush:true))
            gallery.errors.each {
                println it
            }

        gallery = Gallery.get(1)
        println gallery.creator
    }
    def destroy = {
    }
}

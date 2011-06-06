import com.sharevent.Gallery
import com.sharevent.GalleryUser
import com.sharevent.ImageSet
import com.sharevent.Image

class BootStrap {

    def init = { servletContext ->
        Gallery gallery = new Gallery(date:new Date(), title:"Super cool event", location:"Dortmund", creatorFirstName: "Cook", creatorLastName: "Poo", creatorEmail: "cook@poo.ie");

        GalleryUser user = new GalleryUser(firstName:"Lance", lastName:"Hardwood", email:"lance@hardwood.xxx")
        gallery.addToContributors(user)

        ImageSet imageSet = new ImageSet()
        imageSet.addToImages(new Image())
        imageSet.addToImages(new Image())
        imageSet.addToImages(new Image())
        imageSet.addToImages(new Image())
        imageSet.addToImages(new Image())
        imageSet.images.each { image ->
            image.imageSet = imageSet
        }
        user.imageSet = imageSet

        user = new GalleryUser(firstName:"Chew", lastName:"Bakka", email:"chewie@hardwood.xxx", contributedGallery:gallery)
        gallery.addToContributors(user)

        imageSet = new ImageSet()
        imageSet.addToImages(new Image())
        imageSet.addToImages(new Image())
        imageSet.addToImages(new Image())
        imageSet.addToImages(new Image())
        imageSet.addToImages(new Image())
        imageSet.images.each { image ->
            image.imageSet = imageSet
        }

        user.imageSet = imageSet

        if(!gallery.save(flush:true))
            gallery.errors.each {
                println it
            }
    }
    def destroy = {
    }
}

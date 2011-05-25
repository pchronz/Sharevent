import com.sharevent.Gallery
import com.sharevent.GalleryUser

class BootStrap {

    def init = { servletContext ->
        def gallery = new Gallery(date:new Date(), title:"Super cool event", location:"Dortmund");
        def userCreator = new GalleryUser(firstName:"Cook", lastName:"Poo", email:"cook.poo@cookapoo.kr", createdGallery:gallery)
        gallery.creator = userCreator
        userCreator.save(flush:true)
        gallery.save(flush:true)

        def user = new GalleryUser(firstName:"Lance", lastName:"Hardwood", email:"lance@hardwood.xxx")
        gallery.contributors = []
        gallery.contributors.add user
        user = new GalleryUser(firstName:"Chew", lastName:"Bakka", email:"chewie@hardwood.xxx")
        gallery.contributors.add user

        gallery.contributors.add userCreator
        gallery.save()
    }
    def destroy = {
    }
}

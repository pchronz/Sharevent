import com.sharevent.Gallery
import com.sharevent.GalleryUser
import com.sharevent.ImageSet
import com.sharevent.Image
import com.sharevent.SecRole
import com.sharevent.SecUser
import com.sharevent.SecUserSecRole


class BootStrap {

    def springSecurityService

    def init = { servletContext ->
	    // for spring security create an admin role
	    def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true)
	    // add and activate the admin user
	    def adminUser = SecUser.findByUsername('admin') ?: new SecUser( username: 'admin', password: springSecurityService.encodePassword('admin'), enabled: true).save(failOnError: true)
 
            if (!adminUser.authorities.contains(adminRole)) {
                SecUserSecRole.create adminUser, adminRole
	    }

        for(int i = 0; i <5; i++) {
            Gallery gallery = new Gallery(date:new Date(), title:"Super cool event", location:"Dortmund", creatorFirstName: "Cook", creatorLastName: "Poo", creatorEmail: "cook@poo.ie", adminKey: 1234);

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

    }
    def destroy = {
    }
}

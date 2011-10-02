import com.sharevent.Gallery
import com.sharevent.GalleryUser
import com.sharevent.Image
import com.sharevent.SecRole
import com.sharevent.SecUser
import com.sharevent.SecUserSecRole
import com.mongodb.Mongo


class BootStrap {

    def springSecurityService
	def imageDBService

    def init = { servletContext ->
	    // for spring security create an admin role
	    def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true)
	    // add and activate the admin user
	    def adminUser = SecUser.findByUsername('admin') ?: new SecUser( username: 'admin', password: springSecurityService.encodePassword('admin'), enabled: true).save(failOnError: true)
 
		if (!adminUser.authorities.contains(adminRole)) {
			SecUserSecRole.create adminUser, adminRole
	    }

		// remove all orphaned image ids from the imagedb
		imageDBService.synchronizeImageDB()
    }

    def destroy = {
    }
}

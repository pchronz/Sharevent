import com.sharevent.Gallery
import com.sharevent.GalleryUser
import com.sharevent.Image
import com.sharevent.SecRole
import com.sharevent.SecUser
import com.sharevent.SecUserSecRole
import com.mongodb.Mongo
import grails.util.Environment


class BootStrap {

    def springSecurityService
	def imageDBService
	def contentScaffolder

    def init = { servletContext ->
	    // for spring security create an admin role
	    def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true)
	    // add and activate the admin user
	    def adminUser = SecUser.findByUsername('admin') ?: new SecUser( username: 'admin', password: springSecurityService.encodePassword('admin'), enabled: true).save(failOnError: true)
 
		if (!adminUser.authorities.contains(adminRole)) {
			SecUserSecRole.create adminUser, adminRole
	    }

		if(Environment.current == Environment.DEVELOPMENT) {
			// do fancy stuff
		}

    }

    def destroy = {
    }
}

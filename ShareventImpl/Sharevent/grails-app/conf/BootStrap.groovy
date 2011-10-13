import com.sharevent.Gallery
import com.sharevent.GalleryUser
import com.sharevent.Image
import com.sharevent.SecRole
import com.sharevent.SecUser
import com.sharevent.SecUserSecRole
import com.sharevent.ContentScaffolder
import com.mongodb.Mongo
import grails.util.Environment

class BootStrap {

    def springSecurityService
    def contentScaffolder

    def init = { servletContext ->

    	switch (Environment.current) {
            
            case Environment.TEST:
                break
            case Environment.PRODUCTION:
            	break
            case Environment.DEVELOPMENT:

            // for spring security create an admin role
		    def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true)
		    // add and activate the admin user
		    def adminUser = SecUser.findByUsername('admin') ?: new SecUser( username: 'admin', password: springSecurityService.encodePassword('admin'), enabled: true).save(failOnError: true)
	 
			if (!adminUser.authorities.contains(adminRole)) {
				SecUserSecRole.create adminUser, adminRole
		    }

        	contentScaffolder.createContent()
    	}	    

		// special user for incognito image uploads
		GalleryUser incognitoUser = new GalleryUser(firstName: 'direct', lastName: 'direct', email: 'direct@direct.com')
		if(!incognitoUser.save(flush: true)) {
			println  'Errors while saving the special incognito user'
			incognitoUser.errors.each { error ->
				println  error
			}
		}

    }

    def destroy = {
    }    
}

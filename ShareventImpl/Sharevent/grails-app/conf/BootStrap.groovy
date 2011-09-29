import com.sharevent.Gallery
import com.sharevent.GalleryUser
import com.sharevent.Image
import com.sharevent.SecRole
import com.sharevent.SecUser
import com.sharevent.SecUserSecRole
import com.mongodb.Mongo
import javax.imageio.stream.MemoryCacheImageOutputStream
import javax.imageio.ImageIO
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.awt.image.BufferedImage
import grails.util.Environment


class BootStrap {

    def springSecurityService
	def imageDBService

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
        	createContent()
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

    def createContent(){

    	def final imgRange = [1..5, 6..10]
    	def galleryUser = new GalleryUser(firstName: 'Cook', lastName: 'Poo', email: 'cook@poo.com').save()

    	(0..1).each{
			def gallery = new Gallery(date: new Date(), title:'Test${it} Title', location:'Test Location')

			gallery.creatorId = galleryUser.id
			gallery.addToUsers galleryUser
			gallery.save(flush: true)

			galleryUser.addToGalleries gallery
			galleryUser.save(flush:true)

			Image image = null
			(imgRange[${it}]).each { index ->
				image = new Image()

				galleryUser.addToImages image
				gallery.addToImages image

				image.save(flush: true)

				def bais = loadImageAsBais(${index})
				imageDBService.storeImage(bais, image, galleryUser)
				imageDBService.storeImageThumbnail(bais, image, galleryUser)
			}

			galleryUser.save(flush: true)
			gallery.save(flush: true)
			
		}
	}

	def loadImageAsBais(index){
        println "loadImageAsBais beginn ${count}" 
        File file = new File("./web-app/images/exampleGallery/${index}.jpg")
        println file.length() + " " + file.exists()

        BufferedImage bi = ImageIO.read(file)
        def baos = new ByteArrayOutputStream()
        ImageIO.write(bi, "JPG", new MemoryCacheImageOutputStream(baos))

        def byteArray = baos.toByteArray()
        def bais = new ByteArrayInputStream(byteArray)
        println "loadImageAsBais end"
        return bais
    }
}

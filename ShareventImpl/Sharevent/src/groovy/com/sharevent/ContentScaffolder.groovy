package com.sharevent

import java.awt.*
import java.awt.geom.*
import java.awt.image.*
import com.mongodb.Mongo
import javax.imageio.stream.MemoryCacheImageOutputStream
import javax.imageio.ImageIO
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.awt.image.BufferedImage
import org.codehaus.groovy.grails.commons.DefaultGrailsApplication

class ContentScaffolder{

	def imageDBService

	/**
	* creating 2 galleries with 5 images each.
	* 1.jpg - 10.jpg of example pictures
	*/
	public boolean createContent(){

		try{
	    	println "createContent"

	    	def final imgRange = [1..5, 6..10]

	    	def galleryUser = new GalleryUser(firstName:'Cook', lastName:'Poo', email:'cook@poo.com')
	    	galleryUser.save()
	    	println "galleryUser saved"

	    	def gallery

	    	(0..1).each{
	    		println "loop over galleries ${it}"
				gallery = new Gallery(date: new Date(), title:"Test ${it} Title", location:'Test Location')

				gallery.creatorId = galleryUser.id
				gallery.addToUsers galleryUser
				gallery.save(flush: true)

				println "gallery saved"

				galleryUser.addToGalleries gallery
				galleryUser.save(flush:true)

				println "gallery user saved with gallery"

				(imgRange[it]).each { index ->
					
					def image = new Image()

					galleryUser.addToImages image
					gallery.addToImages image

					println "try to store image"
					image.save(flush: true)
					
					println "image saved....with index " + index
					def bais = loadImageAsBais(index)
					println "image loaded from filesystem " + imageDBService

					imageDBService.storeImage(bais, image, galleryUser)
					
					println "try to resize and save image"
					bais = loadImageAsBais(index)
					println "resizeing now"
					bais = resizeImage(bais)

					println "image resized!"

					imageDBService.storeImageThumbnail(bais, image, galleryUser)
					println "both saved for round ${index}"
				}
			}
			galleryUser.save(flush: true)
			gallery.save(flush: true)
		}catch(e){
			e.printStackTrace()
			return false
		}
		return true
	}

	private def loadImageAsBais(int index){
        println "loadImageAsBais beginn ${index}.jpg" 
        
        File file = new File("./web-app/images/exampleGallery/${index}.jpg")
        
        println file.length() + " " + file.exists()

        BufferedImage bi = ImageIO.read(file)
        def baos = new ByteArrayOutputStream()
        ImageIO.write(bi, "JPG", baos)

        def byteArray = baos.toByteArray()
        def bais = new ByteArrayInputStream(byteArray)
        println "loadImageAsBais end"
        return bais
    }

    private def resizeImage(bais){
    	println "resizeImage, bais available? ${bais != null} "
    	BufferedImage biSrc = ImageIO.read(bais)
		int height = 300
		int width = (double)biSrc.getWidth() * (double)height/(double)biSrc.getHeight()

		BufferedImage bdest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
		AffineTransform at = AffineTransform.getScaleInstance((double)width/biSrc.getWidth(), (double)height/biSrc.getHeight())

		Graphics2D g = bdest.createGraphics()
		g.drawRenderedImage(biSrc,at)

		def baos = new ByteArrayOutputStream()
		ImageIO.write(bdest, "JPG", new MemoryCacheImageOutputStream(baos))
		def byteArray = baos.toByteArray()

		bais = new ByteArrayInputStream(byteArray)
		return bais
    }
}
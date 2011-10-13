package com.sharevent

class InboxController {

    def index = { 
    	redirect(action:'inbox')
    }
    
    def inbox = {
     	
    	def galUser = GalleryUser.findByEmail('cook@poo.com')
    	println galUser
    	println galUser.galleries.size()
    	[galUser:galUser]
    }
}

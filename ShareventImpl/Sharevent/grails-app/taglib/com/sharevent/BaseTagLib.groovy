package com.sharevent

class BaseTagLib {

	static namespace = "sv"

	def galleryOwner = { attr ->
		
		def galUser = GalleryUser.get(attr.id)
        out << "<a href=\"mailto:${galUser.email}\">owned by ${galUser.firstName} ${galUser.lastName}</a>"
	}
}

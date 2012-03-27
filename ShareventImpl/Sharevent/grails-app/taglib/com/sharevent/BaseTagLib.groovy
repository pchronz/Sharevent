package com.sharevent

class BaseTagLib {

	static namespace = "sv"

	def galleryOwner = { attr ->
		
		def galUser = GalleryUser.get(attr.id)
        out << "<a href=\"mailto:${galUser.email}\">owned by ${galUser.firstName} ${galUser.lastName}</a>"
	}

	def shortUrl = {attr ->
		def serverUrl = grailsApplication.config.grails.serverURL
		def shortUrl = attr.gallery.urlMap.shortUrl
		out << serverUrl
		out << "/" + shortUrl
	}

	def shortAdminUrl = {attr ->
		def serverUrl = grailsApplication.config.grails.serverURL
		def shortAdminUrl = attr.gallery.urlMap.shortAdminUrl
		out << serverUrl
		out << "/" + shortAdminUrl
	}

	def shortLink = {attr, body ->
		def serverUrl = grailsApplication.config.grails.serverURL
		def shortUrl = attr.gallery.urlMap.shortUrl
		out << "<a href=\""
		out << serverUrl
		out << "/" + shortUrl + "\""
		attr.each {
			if(it != attr.gallery) out << it.key + "=\"" + it.value + "\""
		}
		out << ">"
		out << body()
		out << "</a>"
	}

	def shortAdminLink = {attr, body ->
		def serverUrl = grailsApplication.config.grails.serverURL
		def shortAdminUrl = attr.gallery.urlMap.shortAdminUrl
		out << "<a href=\""
		out << serverUrl
		out << "/" + shortAdminUrl + "\""
		attr.each {
			if(it != attr.gallery) out << it.key + "=\"" + it.value + "\""
		}
		out << ">"
		out << body()
		out << "</a>"
	}
}

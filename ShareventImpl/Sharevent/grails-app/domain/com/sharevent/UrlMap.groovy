package com.sharevent

class UrlMap {

	String shortUrl
	String shortAdminUrl

	static belongsTo = [gallery:Gallery]

    static constraints = {
			
    }

	static String generateRandomUrl() {
		(1..12).inject("") { a, b -> a += [('a'..'z'),('A'..'Z')].flatten()[new Random().nextFloat() * 52 as int] }
	}

}

package com.sharevent

class Gallery {

    // need to add the id explicitly as String for the UUID-generator to work
    String id

    Date date
    String title
    String location

	String creatorId

	UrlMap urlMap

    static hasMany = [users:GalleryUser, images:Image]
	static belongsTo = GalleryUser

    static constraints = {
        date(nullable:false)
        title(blank:false)
        location(blank:false)
		creatorId(nullable: false, blank: false)
		users(validator: { val, obj ->
			if(obj.users == null) return false
			return obj.users.size() > 0
		})
		creatorId(validator: {val, obj ->
			boolean found = false
			obj.users.each { user ->
				if(user.id == val) {
					found =  true
				}
			}
			return found
		})
		urlMap(nullable: false)
    }

    static mapping = {
		id generator: 'uuid'
    }

	Gallery() {
		this.urlMap = new UrlMap()
		this.urlMap.shortUrl = UrlMap.generateRandomUrl()
		this.urlMap.shortAdminUrl = UrlMap.generateRandomUrl()
		this.date = new Date()

	}
}


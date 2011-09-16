package com.sharevent

class Gallery {

    // need to add the id explicitly as String for the UUID-generator to work
    String id

    Date date
    String title
    String location

	String creatorId

    static hasMany = [users:GalleryUser]
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
    }

    static mapping = {
		id generator: 'uuid'
    }
}


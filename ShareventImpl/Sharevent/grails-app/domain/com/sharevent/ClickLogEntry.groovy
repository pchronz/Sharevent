package com.sharevent

class ClickLogEntry {
	
	Date time
	String source

	static belongsTo = GalleryLog

    static constraints = {
		time(nullable: false)
		source(nullable: false, blank: false)
    }

}

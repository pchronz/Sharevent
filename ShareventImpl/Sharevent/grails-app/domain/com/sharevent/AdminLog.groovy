package com.sharevent

class AdminLog {

	Date dateCreated
	String message

    static constraints = {
		dateCreated(nullable: false)
		message(nullable: false, blank: false)
    }
}

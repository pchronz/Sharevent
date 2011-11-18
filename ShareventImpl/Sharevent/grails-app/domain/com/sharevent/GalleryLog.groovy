package com.sharevent

class GalleryLog {
	
	Gallery gallery
	static hasMany = [clickLogEntries: ClickLogEntry]

    static constraints = {
		gallery(nullable: false)
    }

	static mapping = {
		clickLogEntries cascade: 'all-delete-orphan'
	}

	void addClickLogEntry(Date time, String source) {
		log.info "Adding ClickLogEntry: time == ${time}, source == ${source}, gallery.id == ${gallery.id}"
		this.addToClickLogEntries(new ClickLogEntry(time: time, source: source))
		if(!this.save(flush: true)) {
			log.error "Error while saving gallery log after adding new click log entry"
			this.errors.each { error ->
				log.error error
			}
		}
	}
}

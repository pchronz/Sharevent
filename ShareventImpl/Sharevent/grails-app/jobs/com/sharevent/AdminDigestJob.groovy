package com.sharevent


class AdminDigestJob {

	def mailService

	static triggers = {
		simple name: 'adminDigestTrigger', startDelay: 30000, repeatInterval: 900000  
	}
	def group = "AdminGroup"

    def execute() {
		def adminLogs = AdminLog.list()
		log.info "Sending admin digest mail for ${adminLogs.size()} messages."

		String digestMessage = "Email Digest sent: ${new Date()}\n"
		adminLogs.each { it ->
			digestMessage += it.dateCreated.toString() + " " + it.message + "\n"
		}
		log.info "Message: " + digestMessage

		if(adminLogs.size() > 0) {
			mailService.sendMail {     
				to "peter.chronz@gmail.com"     
				from "info@sharevent.net"
				subject "Sharevent admin digest"     
				body digestMessage
			}
			log.info "Sent email to you with the new gallery."

			// delete the sent messages
			adminLogs*.discard()
			adminLogs.each { it ->
				def adminLogProxy = AdminLog.load(it.id)
				adminLogProxy.delete()
			}
			log.info "Deleted all AdminLogs."
		}
		else {
			log.info "No messages to send. Skipped this mail."
		}
    }
}


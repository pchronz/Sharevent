package com.sharevent


class SubscriptionsJob {
	def mailService

	static triggers = {
		// TODO set the interval higher
		simple name: 'subscriptionDigestTrigger', startDelay: 30000, repeatInterval: 1000*30*15  
	}
	def group = "SubscriptionGroup"

    def execute() {
		def numSentMails = 0
		def numSubscribers = 0
		def galleries = Gallery.list()
		galleries.each { gallery ->
			gallery.subscriptions.each {subscription ->
				if(subscription.needsUpdate) {
					def updateMessage = "Hi,\nSomeone has uploaded new images to gallery \"${gallery.title}\".\n"
					updateMessage += " Click the following link to see the gallery:\n\n"
					updateMessage += subscription.url
					updateMessage += "\n"
					updateMessage += "\n"
					updateMessage += "You can unsubscribe from updates to this gallery by clicking the following link:\n"
					updateMessage += subscription.unsubscribe
					updateMessage += "\n"
					mailService.sendMail {     
						to subscription.email
						from "info@sharevent.net"
						subject "Sharevent Gallery Update"     
						body updateMessage
					}
					subscription.needsUpdate = false
					subscription.save(flush: true)
					numSentMails += 1
				}
				numSubscribers += 1
			}
		}
		log.info "Sent ${numSentMails} emails to ${numSubscribers} subscribers."
    }
}

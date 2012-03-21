class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/$shortUrl"(controller: "gallery", action: "viewShortUrl")
		"/"(view:"/main/view")
		"404"(view:"/main/view")
		"500"(view:'/error')
		"/login/$action?"(controller: "login")
		"/logout/$action?"(controller: "logout")		
	}
}

class UrlMappings {

	static mappings = {
		"/"(controller: 'REST') {
			action = [GET: 'index']
		}
		"/image"(controller: 'REST') {
			action = [POST: 'image']
		}
	}
}

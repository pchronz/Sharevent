modules = {
	

	'gallery-view' {
		dependsOn 'jquery'
		resource url:[dir:'js', file:'gallery-view.js'], disposition: 'head'
	}

	
	'contribute-images' {
		resource url:[dir:'js', file:'contribute-images.js'], disposition: 'head'
	}

	facebox {
		dependsOn 'jquery'
		resource url:[dir:'js', file:'facebox.js'], disposition: 'head'
		resource url:[dir:'css', file:'facebox.css'], disposition: 'head'
	}
}
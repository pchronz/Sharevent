modules = {
	

	'gallery-view' {
		dependsOn 'jquery'
		resource url:[dir:'js', file:'gallery-view.js'], disposition: 'head'
	}

	
	'contribute-images' {
		resource url:[dir:'js', file:'contribute-images.js'], disposition: 'head'
	}

}
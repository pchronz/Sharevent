modules = {
	
	'gallery-view' {
		depensOn 'jquery'
		resource url:[dir:'js/file', file:'gallery-view.js'], disposition: 'head'
	}
	
	'contribute-images' {
		depensOn 'jquery'
		resource url:[dir:'js/file', file:'contribute-images.js'], disposition: 'head'
	}
}
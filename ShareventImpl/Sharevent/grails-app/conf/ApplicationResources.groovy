def isDev = grails.util.GrailsUtil.isDevelopmentEnv()

modules = {
	application {
		dependsOn 'jquery'
		resource url:'js/application.js'
	}

	'bootstrap-responsive' {
		resource url:[dir:'bootstrap/css', file:"bootstrap-responsive.${isDev?'css':'min.css'}"], disposition: 'head', exclude:'minify'	
	}

	'bootstrap-css' {
		resource url:[dir:'bootstrap/css', file:"bootstrap.${isDev?'css':'min.css'}"], disposition: 'head', exclude:'minify'	
	}

	'bootstrap-js' {
		dependsOn 'jquery'
		resource url:[dir:'bootstrap/js', file:"bootstrap.${isDev?'js':'min.js'}"], disposition: 'head', exclude:'minify'	
	}

//	'bootstrap-fixed-taglib'{
//		resource url:[dir:'bootstrap/css', file:'fixed-taglib.css']
//	}

	bootstrap {
		defaultBundle 'bs'
		//dependsOn 'bootstrap-fixed-taglib'
		dependsOn 'bootstrap-responsive'
		dependsOn 'bootstrap-css'
		dependsOn 'bootstrap-js'	
	}
	
	'sharevent-css'{
		resource url:[dir:'css', file:'sharevent.css'], disposition: 'head'	
	}

	sharevent {
		defaultBundle 'sv'
		dependsOn 'sharevent-css'
	}

	overrides {
		fileuploader{
			defaultBundle 'sv'
			resource url:'/css/uploader.css'
		}
	}

	colorbox {
		resource url:[dir:'colorbox', file:isDev ? 'jquery.colorbox.js' : 'jquery.colorbox-min.js']
		resource url:[dir:'colorbox', file: 'colorbox.css']
	}
	
	background {
		resource url:[dir:'images', file: 'blue_bg.jpg']
	}

}

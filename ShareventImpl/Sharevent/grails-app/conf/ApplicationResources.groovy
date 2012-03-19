def isDev = grails.util.GrailsUtil.isDevelopmentEnv()

modules = {
	application {
		resource url:'js/application.js'
	}

	'bootstrap-responsive' {
		resource url:[dir:'bootstrap/css', file:"bootstrap-responsive.${isDev?'css':'min.css'}"], disposition: 'head', exclude:'minify'	
	}

	'bootstrap-css' {
		resource url:[dir:'bootstrap/css', file:"bootstrap.${isDev?'css':'min.css'}"], disposition: 'head', exclude:'minify'	
	}

	'bootstrap-js' {
		resource url:[dir:'bootstrap/js', file:"bootstrap.${isDev?'js':'min.js'}"], disposition: 'head', exclude:'minify'	
	}

	bootstrap {
		defaultBundle 'sv'
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
	

}

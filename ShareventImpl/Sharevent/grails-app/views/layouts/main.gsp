<!DOCTYPE html>
<!-- paulirish.com/2008/conditional-stylesheets-vs-css-hacks-answer-neither/ -->
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en"> <!--<![endif]-->
    <head>
        <title><g:layoutTitle default="Sharevent" /></title>
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <r:layoutResources/>
    </head>
    <body>
        <div class="container">
    	    <g:if test="${flash.message}">
    			<div class="message">${flash.message}</div>
    	    </g:if>

            <div class="row">
                <div class="ten columns centered">
                    <g:link controller="main" action="view"><img src="${resource(dir:'images',file:'sharevent_logo.png')}" alt="Sharevent" border="0" />
                    </g:link>
                </div>
            </div>

            <div class="row">
                <div class="ten columns centered">
                    <g:layoutBody />
                </div>
            </div>

        </div>    
        <r:layoutResources/>
    </body>
</html>

<!DOCTYPE html>
<html>
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
            <div id="spinner" class="spinner">
                <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
            </div>
            <div id="shareventLogo" >
                <g:link controller="main" action="view"><img src="${resource(dir:'images',file:'sharevent_logo.png')}" alt="Sharevent" border="0" />
                </g:link>
            </div>

            <div class="row">
                  <div class="span16">
                    <g:layoutBody />
                  </div>
            </div>
        </div>    
        <r:layoutResources/>
    </body>
</html>

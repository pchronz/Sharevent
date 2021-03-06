<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Sharevent" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
    <body>
	    <g:if test="${flash.message}">
			<div class="message">${flash.message}</div>
	    </g:if>
        <div id="spinner" class="spinner">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
        </div>
        <div id="shareventLogo" ><g:link controller="main" action="view"><img src="${resource(dir:'images',file:'sharevent_logo.png')}" alt="Sharevent" border="0" /></g:link></div>
        <g:layoutBody />
    </body>
</html>

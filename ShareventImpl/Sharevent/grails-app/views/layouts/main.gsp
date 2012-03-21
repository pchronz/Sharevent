<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Sharevent" /></title>
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
		<g:javascript library="jquery" plugin="jquery"/>
        <g:layoutHead />
		<r:require modules="bootstrap, sharevent, colorbox"/>
        <r:layoutResources/>

    </head>
    <body>

	<g:if test="${flash.message}">
		<div class="message">${flash.message}</div>
	</g:if>
	
	<div class="container">
		<div class="row">
			<div class="span12">
				<g:link controller="main" action="view">
					<img class="logo" src="${resource(dir:'images',file:'sharevent_logo.png')}" alt="Sharevent" />
				</g:link>
			</div>
		</div>
		
		<div class="row">
			<div class="span12">
				<g:layoutBody />	
			</div>
		</div>
	</div>
        
        <r:layoutResources/>
    </body>
</html>

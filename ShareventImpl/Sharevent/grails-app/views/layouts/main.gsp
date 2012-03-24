<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Sharevent" /></title>
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
		<%-- Google web fonts --%>
		<link href='http://fonts.googleapis.com/css?family=Sonsie+One' rel='stylesheet' type='text/css'>
		<g:javascript library="jquery" plugin="jquery"/>
        <g:layoutHead />
		<r:require modules="bootstrap, sharevent, colorbox"/>
        <r:layoutResources/>
<style type="text/css">
		// Landscape phones and down
			@media (max-width: 480px) {
				.logo1 {
					width:10%;
					max-width:37px;
				}
			}

			// Landscape phone to portrait tablet
			@media (max-width: 768px) {
				.logo1 {
					width:10%;
					max-width:37px;
				}

			}

			// Portrait tablet to landscape and desktop
			@media (min-width: 768px) and (max-width: 1200px) {

				.logo1 {
					width:10%;
					max-width:37px;
				}

			}

			// Large desktop
			@media (min-width: 1201px) {
				.logo1 {
					width:10%;
					max-width:37px;
				}
			}
			.logo1 {
					max-width:437px;
				}
	</style>
    </head>
    <body>
		<style>
			a {
				color: #FFFFFF;
			}
		</style>
	<canvas id="background" width="1800" height="1800" style="position: absolute; top: 0px; left: 0px; z-index: -1;"></canvas>
	<g:if test="${flash.message}">
		<div class="message">${flash.message}</div>
	</g:if>
	
	<div class="container">
		
		<div class="row">
			<div class="span12">
				<g:layoutBody />	
			</div>
		</div>
	</div>
        
	<r:layoutResources/>
    </body>
</html>

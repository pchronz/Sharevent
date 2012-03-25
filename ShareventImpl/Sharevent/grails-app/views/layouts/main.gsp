<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Sharevent" /></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
				.sharevent-title{
					font-size:54px;
				}

			}

			// Landscape phone to portrait tablet
			@media (min-width: 481px) and (max-width: 768px) {
				.sharevent-title{
					font-size:54px;
				}
			}

			// Portrait tablet to landscape and desktop
			@media (max-width: 980px) {
				#sharevent-title{
					font-size:54px;
				}
			}

			// Large desktop
			@media (min-width: 981px) {

			}

			
</style>
    </head>
    <body>
		<%-- Google Analytics --%>
		<script type="text/javascript">
		  var _gaq = _gaq || [];
		  _gaq.push(['_setAccount', 'UA-30319549-1']);
		  _gaq.push(['_setDomainName', 'sharevent.net']);
		  _gaq.push(['_setAllowLinker', true]);
		  _gaq.push(['_trackPageview']);

		  (function() {
			var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
			ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
			var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
		  })();
		</script>
				
		

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

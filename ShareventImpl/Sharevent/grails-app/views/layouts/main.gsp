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
	<canvas id="background" width="1800" height="1800" style="position: absolute; top: 0px; left: 0px; z-index: -1;"></canvas>
	<div class="container">
		<g:if test="${flash.message}">
			<div class="row" style="margin-top: 20px;">
				<div class="span12">
					<div class="alert alert-info">
						<a class="close" data-dismiss="alert">×</a>
						${flash.message}
					</div>
				</div>
			</div>
		</g:if>
		
		<g:if test="${flash.error}">
			<div class="row" style="margin-top: 20px;">
				<div class="span12">
					<div class="alert alert">
						<a class="close" data-dismiss="alert">×</a>
						${flash.error}
					</div>
				</div>
			</div>
		</g:if>

		<div class="row">
			<div class="span12">
				<g:layoutBody />	
			</div>
		</div>
	</div>
        
	<r:layoutResources/>
	<div class="footer">
		<g:link controller="impress" action="view" style="color: white;">${message(code: 'impress.impress')}</g:link>
	</div>

	<script type="text/javascript" charset="utf-8">
		function getDocHeight() {
			return "innerHeight" in window ? window.innerHeight : document.documentElement.offsetHeight;
		}

		function getDocWidth() {
			return "innerWidth" in window ? window.innerWidth : document.documentElement.offsetWidth;
		}

		$(function() {
			var linkHeight = $('#a-impress').height();
			var linkWidth = $('#a-impress').width();
			var docHeight = getDocHeight();
			var docWidth = getDocWidth();
			$('#a-impress').css('position', 'absolute');
			$('#a-impress').css('top', docHeight - linkHeight - 20);
			$('#a-impress').css('left', docWidth/2 - linkWidth/2);
		});
	</script>
    </body>
</html>	

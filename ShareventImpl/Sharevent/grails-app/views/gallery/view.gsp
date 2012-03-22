<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>
    	<g:message code="userDef.companyName" />: <g:message code="userDef.companySlogan" />
    </title>
	<r:require module="fileuploader" />
</head>
  <body>
	  <div class="row">
		  <div class="span8" style="margin-top: 50px;">
			<g:link controller="main" action="view" style="text-decoration: none;">
				<span style="font-family: 'Sonsie One', cursive; font-size: 96px; text-decoration: none; color: #FFFFFF;">SharEvent</span>
			</g:link>
		  </div>
	  </div>
	  
	<div class="row">
		<div class="span12 galleryTitle">
			<h1 style="color: #FFFFFF;">${galleryInstance.title}</h1>
			<div class="g-plusone"
				style="display:inline"
				data-annotation="none"
				data-size="medium"
				data-href="${createLink(action: 'view', id: galleryInstance.id)}">
			</div>
			<div id="name" style="display:inline">
				<a href="https://twitter.com/share" 
					class="twitter-share-button" 
					data-lang="en" 
					data-count="none"
					data-url="${createLink(action: 'view', id: galleryInstance.id)}">
					Tweet
				</a>	
			</div>

			<div class="fb-like" 
				style="display:inline;position:relative;top:-3px;"
				data-href= "${createLink(action: 'view', id: galleryInstance.id)}"
				data-send="false"
				data-layout="button_count" 
				data-width="450"
				data-show-faces="true">
			</div>
		</div>

	</div>
	
	<div class="row">
	  <form class="form">
		<div class="span3">
			<fieldset>
				<div class="control-group">
					<label class="control-label"><sv:shortLink gallery="${galleryInstance}"><g:message code="userDef.participationLink"/></sv:shortLink></label>
					<div class="controls">
						<input class="span3 gallery-short-url" type="text" value="${shortUrl}"></input>
					</div>
				</div>
			</fieldset>
		</div>
		<div class="span3">
			<fieldset>
				<g:if test="${isAdmin}">
					<div class="control-group">
						<label class="control-label"><sv:shortAdminLink gallery="${galleryInstance}"><g:message code="userDef.administrationLink"/></sv:shortAdminLink></label>
						<div class="controls">
							<input class="span3 gallery-short-url" type="text" value="${shortAdminUrl}"></input>
						</div>
					</div>
				</g:if>
			</fieldset>
		</div>
			
		<script type="text/javascript" charset="utf-8">
			$(function() {
				$(".gallery-short-url").focus(function() {
					this.select();
				});
				$(".gallery-short-url").mouseup(function(e) {
					e.preventDefault();
				});
			});
		</script>
	  </form>
	</div>

        <g:form controller="gallery" id="${galleryInstance.id}">

		<r:script type="text/javascript" charset="utf-8">
			function selectAllImages(boxId){
				$('input[type=checkbox]').attr('checked',$('#'+boxId).is(':checked'));
			}		
		</r:script>

		
		<div class="row">
			<g:if test="${urls.size() > 0}">
				<div class="span3">
					<g:actionSubmit 
						name="Download" 
						value="${message(code: 'userDef.downloadImages')}" 
						action="download"
						class="btn btn-primary span3" />
				</div>
			</g:if>	
			<div class="span2">
				<a class="btn btn-primary" data-toggle="modal" href="#upload-modal">Upload images</a>
			</div>
		</div>
<br>	
		<div class="row">
			<div class="span12">
				<g:if test="${urls.size() > 0}">
					<ul class="thumbnails">
						<g:each var="imageUrl" in="${urls}">
							<li class="span3">
								<a href="${urlsFull[imageUrl.key]}" class="thumbnail">
									<img src="${imageUrl.value}" id="img_${imageUrl.key}"/>
								</a>
								
								<div class="ac-wrapper ac-top ac-left">	
									<div class="ac-overlay ac-gallery">
									</div>
								</div>
								<div class="ac-wrapper ac-top ac-right">
									<div class="ac-overlay ac-delete">
									</div>
								</div>
								<div class="ac-wrapper ac-bottom ac-left">
									<div class="ac-overlay ac-select">
										<input type="hidden" name="selected_img_${imageUrl.key}" value="" id="selected_img_${imageUrl.key}" />
									</div>
								</div>
								<div class="ac-wrapper ac-bottom ac-right">
									<div class="ac-overlay ac-social">
										<div class="g-plusone"
											data-annotation="none"
											data-size="medium"
											data-href="${createLink(action: 'view', id: galleryInstance.id, params:[showImage:imageUrl.key] )}">
										</div>
										<div id="name">
											<a href="https://twitter.com/share" 
												class="twitter-share-button" 
												data-lang="en" 
												data-count="none"
												data-url="${createLink(action: 'view', id: galleryInstance.id, params:[showImage:imageUrl.key] )}">
												Tweet
											</a>	
										</div>

										<div class="fb-like" 
											data-href= "${createLink(action: 'view', id: galleryInstance.id, params:[showImage:imageUrl.key] )}"
											data-send="false"
											data-layout="button_count" 
											data-width="450"
											data-show-faces="true">
										</div>
									</div>
								</div>
							</li>				 
						</g:each>
					</ul>	
				</g:if>
				<g:else>
					<div class="alert alert-info">
						<a class="close" data-dismiss="alert">×</a>
						<h3>
							<g:message code="view.gallery.view.emptyGallery" />
						</h3>
					</div>
				</g:else>
			</div>			
		</div>

		<%-- g-plusone: generatet code --%>
		<script type="text/javascript">
		  (function() {
		    var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
		    po.src = 'https://apis.google.com/js/plusone.js';
		    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
		  })();
		</script>
		<%-- END g-plusone --%>

		<%-- twitter tweet --%>
		<script>
			!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");
		</script>
		<%-- END twitter tweet--%>

		<%-- Faceboook like --%>
		<div id="fb-root"></div>
		<script>
		(function(d, s, id) {
		  var js, fjs = d.getElementsByTagName(s)[0];
		  if (d.getElementById(id)) return;
		  js = d.createElement(s); js.id = id;
		  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
		  fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));
		</script>
		<%-- END facebook like --%>

		<div class="row">
			<g:if test="${urls.size() > 0}">
				<div class="span3">
					<g:actionSubmit 
						name="Download" 
						value="${message(code: 'userDef.downloadImages')}" 
						action="download"
						class="btn btn-primary span3" />
				</div>
			</g:if>	

			<div class="span2">
				<a class="btn btn-primary" data-toggle="modal" href="#upload-modal" >Upload images</a>
			</div>
		</div>

		<g:hiddenField name="key" value="${galleryInstance.creatorId}" />
        </g:form>

	<script type="text/javascript" charset="utf-8">
		var ongoingUploads = 0;
	</script>

	<div class="modal hide fade" id="upload-modal">
	  <div class="modal-header">
		<a class="close" data-dismiss="modal">×</a>
		<h3>Upload images</h3>
	  </div>
	  <div class="modal-body">
		<uploader:uploader 
			id="${galleryInstance.id}"
			url="${[controller: 'gallery', action: 'uploadImage', id: galleryInstance.id]}"
			multiple="true" 
			sizeLimit="5000000"
			allowedExtensions=" [ 'jpg', 'jpeg', 'JPG', 'JPEG' ]">
			<uploader:onSubmit>
				$('.qq-upload-drop-area').hide();
				$('.qq-upload-button').hide();
				$('#upload-modal .close').hide();
				ongoingUploads += 1;
				console.log('obSubmit: ' +ongoingUploads)
			</uploader:onSubmit>
			<uploader:onComplete>
				ongoingUploads -= 1;
				console.log('onComplete :' +ongoingUploads)

				if (ongoingUploads == 0) {
					window.location.reload(true);
					console.log('refresh :' +ongoingUploads)

				}
			</uploader:onComplete>
		</uploader:uploader>

		<script type="text/javascript" charset="utf-8">
			$(function(){
				$('.qq-upload-drop-area').css('display', 'inline');
			});
		</script>
	  </div>
	  <div class="modal-footer">
	  </div>
	</div>


		<style type="text/css" media="screen">
		
			.ac-wrapper {
				position:relative;
				z-index:2;
			}

			.ac-overlay {
				display:none; 
				z-index:3;
				
				background-repeat:no-repeat;
				background-size: 100%;

				opacity:0.6;
				filter:alpha(opacity=60); /* IE transparency */
			}
			
			.ac-delete {
				background-image: url(${resource(dir:'images',file:'delete.png')});
			}

			.ac-gallery {
				background-image: url(${resource(dir:'images',file:'gallery.png')});
			}

			.ac-select{
				background-image: url(${resource(dir:'images',file:'download.png')});
			}

			.ac-social {
				background-image: url(${resource(dir:'images',file:'social.png')});
			}			

			.ac-top {
				top:0px;
			}
			.ac-bottom {
				
			}
			.ac-left {
				float:left;
			}
			.ac-right {
				float:right;
			}
		
		</style>
		<script type="text/javascript" charset="utf-8">

			$('.thumbnail').hover(
				function(){
					var ww = $(window).width();	
					if( ww <= 480)
						return null;


					var h = this.clientHeight;
					var divs = $(this).nextAll();
					
					var hpx = h/2+'px';
					var wpx = h/2+'px';

					divs.css({height:hpx,width:wpx});

					//TODO select only this children
					$(".ac-wrapper").css('margin-top',"-"+h+"px");
					$(".ac-bottom").css('top', hpx);
					$(".ac-overlay").css({height:hpx,width:wpx});
				},
				function(){
					return null;
				}
			);			

			$(".ac-wrapper").hover(
			  function(){
				$(this).find(':first-child').show();

			  }, 
			  function(){
				$(this).find(':first-child').hide();
			  }
			);
			
			$('.ac-gallery').click(function () {
				var a = $(this).parent().siblings(':first');
				a.click();
			
			});

			$('.ac-delete').click(function () {
				var img = $(this).parent().parent().find('a:first-child').find('img').get(0);
				var hidden = $(this).children(':first');
				hidden.attr('value','');

				var elem = $(this).parent().parent();

				//TODO remove from dom only onSuccess
				var galId = '${galleryInstance.id}';
				${remoteFunction(action: 'deleteImage', onSuccess: 'hure();' , params: '\'imageId=\' + img.id + \'&id=\'+ galId')}
				elem.remove();
			});

			$('.ac-select').click(function () {
				var a = $(this).parent().siblings(':first');
				var img = $(this).parent().parent().find('a:first-child').find('img').get(0);
				var hidden = $(this).children(':first');
				var value = hidden.attr('value');

				if(value == ""){
					a.css('background-color', '#0069d6');
					hidden.attr('value',img.id);
				}else{
					a.css('background-color', '#fff');
					hidden.attr('value','');
				}
			});
		
			$('.ac-social').click(function () {
				//TODO
			});

			//reset overlay on window resize
			$(window).bind("resize", resizeWindow);
			function resizeWindow( e ) {
				$(".ac-wrapper").css({height:'0px',width:'0px'});
			};

			$(function(){
				$(".thumbnail").colorbox({rel: 'group1', preloading: true, scalePhotos: true, maxWidth: "100%", maxHeight: "100%"});
				<g:if test="$showImage}">
					$("#img_${showImage}").click();
				</g:if>
			});
			
			$(document).bind('click', function(e) {
				
				console.log( e );
			    });
	</script>

	<script type="text/javascript" charset="utf-8">
		$(function()
		{
			if(document.createElement('canvas') && navigator && !(navigator.userAgent.match(/iPhone/i) || navigator.userAgent.match(/iPod/i) || navigator.userAgent.match(/iPad/i)))
			{
				function Bit(size, color){
					this.size = size;
					this.color = color;
					// uniform distribution across the whole canvas
					this.xpos = Math.random() * screen.availWidth - this.size;
					this.ypos = Math.random() * screen.availHeight - this.size;
					this.offset = new Number(100 * Math.random());

					// give it a random vector
					this.xDir = 2 * (Math.random() - 0.5);
					this.yDir = 2 * (Math.random() - 0.5);

					// growing vs. shrinking
					this.down = Math.random() > 0.5;
				}

				function tick(bit)
				{
					context.shadowColor = bit.color;
					context.fillStyle = bit.color;

					var scrollX = window.pageXOffset;
					var scrollY = window.pageYOffset;

					if(bit.size >= 75) {
						bit.down = true;
					}
					if(bit.size <= 1) {
						bit.down = false;
						// relocate
						bit.xpos = scrollX + Math.random() * screen.availWidth - bit.size;
						bit.ypos = scrollY + Math.random() * screen.availHeight - bit.size;
					}

					if(bit.down) {
						bit.size -= 0.1;
					}
					else {
						bit.size += 0.1;
					}

					// move the object a little
					bit.xpos += 0.1 * bit.xDir;
					bit.ypos += 0.1 * bit.yDir;

					context.beginPath();  
					context.arc(bit.xpos,bit.ypos,bit.size,0,Math.PI*2,true); // Outer circle  
					context.fill();
				}

				function reDraw()
				{
					var scrollX = window.pageXOffset;
					var scrollY = window.pageYOffset;
					context.clearRect(0,0,canvas.width, canvas.height);
					bits.map(tick);
				}

				function getDocHeight() {
					var D = document;
					return Math.max(
						Math.max(D.body.scrollHeight, D.documentElement.scrollHeight),
						Math.max(D.body.offsetHeight, D.documentElement.offsetHeight),
						Math.max(D.body.clientHeight, D.documentElement.clientHeight)
					);
				}
				

				document.body.style['background-image'] = 'url("${resource(dir:'images', file:'blue_bg.jpg')}")';
				document.body.style['backgroundImage'] = 'url("${resource(dir:'images', file:'blue_bg.jpg')}")';
				document.body.style['background-attachment'] = 'fixed';

				// object colors, chosen at random
				var colours = ['#CFFFFF', '#3366ff', '#0099ff', '#0074CC'];

				// preparing the canvas
				maxX = screen.availWidth + 40;
				var canvas = document.getElementById('background');
				canvas.width = screen.width;
				//canvas.height = getDocHeight();
				canvas.height = 2400;
				var context = canvas.getContext('2d');
				context.globalAlpha = 0.7;
				context.shadowBlur = 7;

				// creating the objects
				var numObjects = 15;
				var bits = new Array(numObjects);
				var bit;
				for(var i = 0; i < numObjects; ++i)
				{
					var minSize = 10;
					var maxDelta = 20;
					// get a random size and a random from our list
					bit = new Bit(minSize + maxDelta * Math.random(), colours[Math.floor(colours.length * Math.random())]);
					bits[i] = bit;
				}

				setInterval(reDraw, 50);
			}
		});
	</script>
</body>
</html>

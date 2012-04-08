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
				<span style="font-family: 'Sonsie One', cursive; font-size: 96px; text-decoration: none; color: #FFFFFF;text-shadow: 2px 2px #000077;">SharEvent</span>
			</g:link>
		  </div>
	  </div>
	  
	<div class="row">
		<div class="span12 galleryTitle">
			<h1 style="color: #FFFFFF;display:inline;margin-right:12px;">${galleryInstance.title}</h1>
			<div class="social-bar">
					<div class="div-qr">
						<a data-toggle="modal" href="#qr-code-modal" ><img src="${resource(dir: 'images/social-media', file: 'qr.png')}" style="margin-top: -20px;"/></a>
						<script type="text/javascript" charset="utf-8">
							$('#qr-code-modal').modal();
						</script>
					</div>
					<div>
						<a href="#" class="ac-gallery-mailto">
							<img src="${resource(dir: 'images/social-media', file: 'mailTo_25.png')}" style="margin-top: -17px;"></img>
						</a>
					</div>
					<div class="g-plusone"
						data-annotation="none"
						data-size="medium"
						data-href="${createLink(absolute: true, action: 'viewExample')}">
					</div>
					<div id="name">
						<a href="https://twitter.com/share" 
							class="twitter-share-button" 
							data-lang="en" 
							data-count="none"
							data-url="${createLink(absolute: true, action: 'viewExample')}">
							Tweet
						</a>	
					</div>

					<div class="fb-like" 
						style="display:inline;position:relative;top:-3px;"
						data-href= "${createLink(absolute: true, action: 'viewExample')}"
						data-send="false"
						data-layout="button_count" 
						data-width="25"
						data-show-faces="true">
					</div>
			</div>
		</div>

	</div>

	<%-- QR code modal --%>
	<div class="modal hide" id="qr-code-modal">
	  <div class="modal-header">
		<a class="close" data-dismiss="modal">×</a>
		<h3>QR Code</h3>
	  </div>
	  <div class="modal-body">
		 <ul class="thumbnails" style="position: relative; left: 130px; width: 300px;">
			 <li class="span3">
				 <div class="thumbnail" id="qr-link">
					 <qrcode:image text="${createLink(absolute: true, controller: 'gallery', action: 'viewExample')}" />
					 <script type="text/javascript" charset="utf-8">
						 $('#qr-link').click(function(){return false;});
					 </script>
				</div>
			</li>
		</ul>
	  </div>
	  <div class="modal-footer">
		  <g:link class="btn btn-info" controller="gallery" action="saveQrCode" id="${42}">Save</g:link>
	  </div>
	</div>
	
	
	<div class="row">
		<div class="span3" >
		  <form class="form">
			<fieldset>
				<div class="control-group">
					<label class="control-label"><g:link controller="gallery" action="viewExample" style="color: #FFF"><g:message code="userDef.participationLink"/></g:link></label>
					<div class="controls">
						<input class="span3 gallery-short-url" type="text" value="${shortUrl}"></input>
					</div>
				</div>
			</fieldset>
			</form>
		</div>
		<g:if test="${isAdmin}">
			<div class="span3">
			  <form class="form">
				<fieldset> 
						<div class="control-group">
							<label class="control-label"<g:link controller="gallery" action="viewExample"><g:message code="userDef.administrationLink"/></g:link></label>
							<div class="controls">
								<input class="span3 gallery-short-url" type="text" value="${shortAdminUrl}"></input>
							</div>
						</div>
				</fieldset>
				</form>
			</div>
		</g:if>
		<div class="span6">
			<g:form class="form" name="subscriptionForm" controller="gallery" action="subscribe" id="${42}">
						<label class="control-label" style="color: #fff;">Email-Notification</label>
						<input type="text" name="email" class="input-large subscription-tooltip" title="We can notify you when new images are uploaded." placeholder="Email" />
						<button type="submit" class="btn" style="margin-top: -8px;">Subscribe</button>
						<script type="text/javascript" charset="utf-8">
							$('.subscription-tooltip').tooltip();
						</script>
			</g:form>
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
	<g:form controller="gallery" id="${42}">

		<div class="row">
				<div class="span7 up-down-toolbar">
					<div class="btn-group">
						<g:actionSubmit 
							name="Download" 
							value="${message(code: 'userDef.downloadImages')}" 
							action="download"
							class="btn btn-large ${urls.size() == 0 ? 'disabled':'' } download-button" />
							<a class="btn btn-large upload-button" data-toggle="modal" href="#upload-modal" style="">${message(code: 'gallery.upload')}</a>
					</div>
				</div>
		</div>
		<br>	
		<div class="row">
			<div class="span12">
				<g:if test="${urls.size() > 0}">
					<ul class="thumbnails">
						<g:each var="imageUrl" in="${urls}">
							<li class="span3 thumbwall">
								<a href="${urlsFull[imageUrl.key]}" class="thumbnail gallery-image">
									<img width="270px" height="270px" src="${imageUrl.value}" id="img_${imageUrl.key}"/>
								</a>
								
								<div class="ac-wrapper ac-top ac-left">	
									<div class="ac-overlay ac-gallery">
									</div>
								</div>
								<div class="ac-wrapper ac-top ac-right">
									<div class="ac-overlay ${isAdmin?'ac-delete':'ac-download'}">
										<g:link controller="gallery" action="downloadImage" params="${[imageId: imageUrl.key]}" />
									</div>
								</div>
								<div class="ac-wrapper ac-bottom ac-left">
									<div class="ac-overlay ac-select">
										<input type="hidden" name="selected_img_${imageUrl.key}" value="" id="selected_img_${imageUrl.key}" />
									</div>
								</div>
								<div class="ac-wrapper ac-bottom ac-right">
									<div class="ac-overlay ac-social">
										<div class="ac-top ac-left ac-google"></div>
										<div class="ac-top ac-right ac-twitter"></div>
										<div class="ac-top ac-left ac-facebook"></div>
										<div class="ac-top ac-right ac-mailTo"></div>
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

		<div class="row">
				<div class="span7 up-down-toolbar">
					<div class="btn-group">
						<g:actionSubmit 
							name="Download" 
							value="${message(code: 'userDef.downloadImages')}" 
							action="download"
							class="btn btn-large ${urls.size() == 0 ? 'disabled':'' } download-button" />
							<a class="btn btn-large upload-button" data-toggle="modal" href="#upload-modal">${message(code: 'gallery.upload')}</a>
					</div>
				</div>
		</div>
		
		<g:hiddenField name="key" value="${42}" />
        </g:form>

	<script type="text/javascript" charset="utf-8">
		var ongoingUploads = 0;
	</script>

	<div class="modal hide fade" id="upload-modal">
	  <div class="modal-header">
		<a class="close" data-dismiss="modal">×</a>
		<h3>${message(code: 'userDef.uploadImages')}</h3>
	  </div>
	  <div class="modal-body">
		<uploader:uploader 
			id="${42}"
			url="${[controller: 'gallery', action: 'uploadImage', id: 42]}"
			multiple="true" 
			sizeLimit="9000000"
			allowedExtensions=" [ 'jpg', 'jpeg', 'JPG', 'JPEG' ]">
			<uploader:onSubmit>
				$('.qq-upload-drop-area').hide();
				$('.qq-upload-button').hide();
				$('#upload-modal .close').hide();
				$('.upload-button').addClass('upload-spinning');
				$('.upload-button').addClass('disabled');
				$('.upload-button').html('.');
				ongoingUploads += 1;
				console.log('obSubmit: ' +ongoingUploads)
			</uploader:onSubmit>
			<uploader:onComplete>
				ongoingUploads -= 1;
				console.log('onComplete :' +ongoingUploads)


				if (ongoingUploads == 0) {
					window.location.reload(true);
					console.log('refresh :' +ongoingUploads)
					$('.upload-button').removeClass('upload-spinning');
					$('.upload-button').removeClass('disabled');
					$('.upload-button').html('Upload');
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

	<%-- needed to send image links via mail --%>
	<a id="sendMail" href="mailto:"></a>


	<%-- modal dialogue for social network --%>

		<div class="modal hide fade" id="social-modal">
		  <div class="modal-header">
			<a class="close" data-dismiss="modal">×</a>
			<h3>Share this image</h3>
		  </div>
		  <div class="modal-body">
			<ul class="thumbnails" style="position: relative; left: 130px; width: 300px;">
				<li class="thumbwall">
					<div class="thumbnail">
						<img id="social-modal-image" src="">
					</div>
				</li>
			</ul>
			<div class="caption">
				<iframe src="" id="social-frame"></iframe>
			</div>
		  </div>
		</div>

		<script type="text/javascript" charset="utf-8">

			$('.thumbnail').hover(
				function(){
					var ww = $(window).width();	
					if( ww <= 480)
						return null;

					var h = this.clientHeight;
					var w = this.clientWidth;
					var divs = $(this).nextAll();
				
					var hpx = h/2 -4+'px';
					var wpx = h/2 -4+'px';
					var offset = "-" + (h-3) +"px";

					divs.css({height:hpx,width:wpx});

					//TODO select only this children
					$(".ac-wrapper").css('margin-top',offset);
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
		

			<%-- #################################--%>
			<%-- BEGINN functions for image overlay--%>
			<%-- #################################--%>
			$('.ac-gallery').click(function () {
				var a = $(this).parent().siblings(':first');
				a.click();
			});

			$('.ac-download').click(function () {
				var a = $(this).children();
				$(this).hide();
				window.location.href = a.attr('href');
			});

			$('.ac-delete').click(function () {
				var img = $(this).parent().parent().find('a:first-child').find('img').get(0);
				var hidden = $(this).children(':first');
				hidden.attr('value','');

				var elem = $(this).parent().parent();

				//TODO remove from dom only onSuccess
				var galId = '${42}';
				var key = '${-1}'
				${remoteFunction(action: 'deleteImage', params: '\'imageId=\' + img.id + \'&id=\'+ galId +\'&key=\' + key')}
				elem.remove();
				
				
				if(setCounter() == 0){
					$('input[name=_action_download]').addClass('disabled');
				}
			});
			
			$('.ac-select').click(function () {
				var a = $(this).parent().siblings(':first');
				var img = $(this).parent().parent().find('a:first-child').find('img').get(0);
				var hidden = $(this).children(':first');
				var value = hidden.attr('value');

				if(value == ""){
					a.css({'background-color':'#f89a07','border-color':'#f89a07'});
					hidden.attr('value',img.id);
					
				}else{
					a.css({'background-color':'#fff','border-color':'#fff'});
					hidden.attr('value','');
				}
				
				setCounter();	
				
			});

			function setCounter(){

				var countSelected =  $('.ac-select input[value^="img_"]').size();
				var countAll = $('.thumbnail').not('#social-modal .thumbnail').size();

				if(countSelected > 0){
					var bText = "Download (" + countSelected +"/"+countAll+")";
					$('input[name=_action_download]').attr('value',bText);
				}else{
					var	bText = "Download"
					$('input[name=_action_download]').attr('value',bText);
				}
				return countAll;
			}
	
			<%-- ################################--%>
			<%-- connect social network to images--%>
			<%-- ################################--%>
			$('.ac-google').click(function () {

				var iframe = $('iframe[title="+1"]');
				var src = iframe.attr('src');

				var img = $(this).parent().parent().siblings('a').children('img');

				var gLink = src.replace(/${42}/g,'${42}?showImage=' + img.attr('id'));
				
				$('#social-modal-image').attr('src',img.attr('src'));
				$("#social-frame").attr('src',gLink );
				$('#social-modal').modal({show:true});
			});
			
			$('.ac-twitter').click(function () {
				var iframe = $('iframe[title~="Tweet"]');
				var src = iframe.attr('src');

				var img = $(this).parent().parent().siblings('a').children('img');
				
				var twLink = src.replace(/${42}/g,'${42}?showImage=' + img.attr('id'));
				$('#social-modal-image').attr('src',img.attr('src'));
				$("#social-frame").attr('src',twLink );
				$('#social-modal').modal({show:true});
			});

			$('.ac-facebook').click(function () {

				var iframe = $('iframe.fb_ltr');
				var src = iframe.attr('src');

				var img = $(this).parent().parent().siblings('a').children('img');

				var fbLink = src.replace(/${42}/g,'${42}?showImage=' + img.attr('id'));
				$('#social-modal-image').attr('src',img.attr('src'));
				$("#social-frame").attr('src',fbLink );
				$('#social-modal').modal({show:true});
				
			});

			$('.ac-mailTo').click(function () {
				var img = $(this).parent().parent().siblings('a').children('img');
				var url = "${createLink(absolute: true, action: 'view', id: 42)}?showImage=" + img.attr('id');
		
				var subject = '?subject=Sharevent shared event'	
				var body= '&body='+'Check out this gallery: '+url;

				var mailContent = subject + body;
				var a = $('#sendMail');
				a.attr('href','mailto:'+ mailContent);

				window.open(a.attr('href'),'_blank');

			});

			$('.ac-gallery-mailto').click(function () {
				var url = "${createLink(absolute: true, controller: 'gallery', action: 'view', id: 42)}";
		
				var subject = '?subject=Sharevent shared event'	
				var body= '&body='+'Check out this gallery: '+url+'';

				var mailContent = subject + body;
				var a = $('#sendMail');
				a.attr('href','mailto:'+ mailContent);

				window.open(a.attr('href'),'_blank');

			});
			

			//reset overlay on window resize
			$(window).bind("resize", resizeWindow);
			function resizeWindow( e ) {
				$(".ac-wrapper").css({height:'0px',width:'0px'});
			};

			$(function(){
				$(".gallery-image").colorbox({rel: 'group1', preloading: true, scalePhotos: true, maxWidth: "100%", maxHeight: "100%"});
				<g:if test="${showImage}">
					$("#${showImage}").click();
				</g:if>
			});
			<%-- ################################--%>
			<%-- END events for image overlay    --%>
			<%-- ################################--%>


	</script>

	<script type="text/javascript" charset="utf-8">
		$(function()
		{
			if(document.createElement('canvas') && navigator && (!(navigator.userAgent.match(/iPhone/i) || navigator.userAgent.match(/iPod/i) || navigator.userAgent.match(/iPad/i) || navigator.userAgent.match(/msie/i) || navigator.userAgent.match(/Safari/i) || navigator.userAgent.match(/Android/i)) || navigator.userAgent.match(/Chrome/i) || navigator.userAgent.match(/Firefox/i)))
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
						bit.size -= 0.2;
					}
					else {
						bit.size += 0.2;
					}

					// move the object a little
					bit.xpos += 0.5 * bit.xDir;
					bit.ypos += 0.5 * bit.yDir;

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
					return "innerHeight" in window ? window.innerHeight : document.documentElement.offsetHeight;
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
				canvas.height = getDocHeight();
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

				reDraw();
				setInterval(reDraw, 500);
			}
			else {
				document.body.style['background-image'] = 'url("${resource(dir:'images', file:'blue_bg.jpg')}")';
				document.body.style['backgroundImage'] = 'url("${resource(dir:'images', file:'blue_bg.jpg')}")';
				document.body.style['background-attachment'] = 'fixed';
			}
		});
	</script>

<%-- #################################################################### --%>
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
<%-- #################################################################### --%>
<%-- END facebook like --%>

<%-- Corrupt image detector --%>
<script type="text/javascript" charset="utf-8">
	$('.thumbnails .thumbnail img').error(function() {
			console.log('error');
			$(this).parent().parent().remove();
	});
</script>
</body>
</html>

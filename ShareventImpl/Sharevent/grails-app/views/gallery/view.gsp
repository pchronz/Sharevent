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
		<div class="span12 galleryTitle">
			<h2>${galleryInstance.title}</h2>
		</div>
	</div>
	
	<div class="row">
	  <form class="form-horizontal">
		<div class="span12">
			<fieldset>
				<div class="control-group">
					<label class="control-label"><sv:shortLink gallery="${galleryInstance}"><g:message code="userDef.participationLink"/></sv:shortLink></label>
					<div class="controls">
						<input class="span3 gallery-short-url" type="text" value="${shortUrl}"></input>
						<!-- AddThis Button BEGIN -->
						<span class="help-inline addthis_toolbox addthis_default_style addthis_32x32_style">
							<a class="addthis_button_twitter"></a>
							<a class="addthis_button_facebook"></a>
							<a class="addthis_button_mailto"></a>
							<a class="addthis_button_google_plusone"></a>
						</span>
						<script type="text/javascript">
							var addthis_config = {"data_track_addressbar":true, services_overlay:'facebook,twitter,mailto'};
							var addthis_share = {
							   url_transforms: {
								 clean: true,
								 remove: ['key'],
								 shorten: { twitter: 'bitly' } 
							   },
								shorteners : {
										bitly : { 
											username: 'o_2146m4g6q1',
											apiKey: 'R_c16d074693b049fec0ee2c5a8944c11b'
										}
									}
							};
						</script>
					</div>
				</div>
				<g:if test="${isAdmin}">
					<div class="control-group">
						<label class="control-label"><sv:shortAdminLink gallery="${galleryInstance}"><g:message code="userDef.administrationLink"/></sv:shortAdminLink></label>
						<div class="controls">
							<input class="span3 gallery-short-url" type="text" value="${shortAdminUrl}"></input>
						</div>
					</div>
				</g:if>
			</fieldset>

			
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
			
			<script type="text/javascript" src="http://s7.addthis.com/js/250/addthis_widget.js#pubid=ra-4f69a3e22612bf8e"></script>
			<%-- image overlay --%>
			<script type="text/javascript" src="http://s7.addthis.com/js/300/addthis_widget.js#pubid=ra-4f69a3e22612bf8e"></script> 
			<!-- AddThis Button END -->
		</div>
	  </form>
	</div>

        <g:form controller="gallery" id="${galleryInstance.id}">

		<r:script type="text/javascript" charset="utf-8">
			function selectAllImages(boxId){
				$('input[type=checkbox]').attr('checked',$('#'+boxId).is(':checked'));
			}		
		</r:script>

		
		<div class="row control">
			<g:if test="${urls.size() > 0}">
				<div class="span3">
					<div class="btn-group dropdown-toggle" data-toggle="dropdown" >
						<g:actionSubmit 
						name="Download" 
						value="${message(code: 'userDef.downloadImages')}" 
						action="download"
						class="btn btn-primary span2" />
						<button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
							<span class="caret"></span>
						</button> 	
						<ul class="dropdown-menu">
							<li><a href="#">selected only</a></li>
						</ul>
					</div>
				</div>
				<div class="span2">
					<a class="btn btn-primary" data-toggle="modal" href="#upload-modal">Upload images</a>
				</div>
			</g:if>
		</div>
	
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
										<br>
										<span class="help-inline addthis_toolbox addthis_default_style addthis_32x32_style">
										<a class="addthis_button_twitter"></a>
										<a class="addthis_button_facebook"></a>
										<br><br>
										<a class="addthis_button_google_plusone"></a>
										<a class="addthis_button_mailto"></a>
										</span>
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
			<%--
			<g:if test="${isAdmin}">
				<div class="span3">
					<g:actionSubmit 
						name="removeImages" 
						value="${message(code: 'userDef.deleteSelection')}" 
						action="deleteImages"
						onclick="if(!confirm('Are you sure?')) return false" 
						class="btn btn-info span3" />
				</div>
				<div class="span3">
					<g:actionSubmit 
						name="removeGallery" 
						value="${message(code: 'userDef.deleteGallery')}" 
						action="deleteGallery"
						onclick="if(!confirm('Are you sure?')) return false"
						class="btn span3" />
				</div>
			</g:if>
			--%>
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
			sizeLimit="5000000">
			<uploader:onSubmit>
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
				$(this).parent().parent().remove();
				//TODO make a remote call to delete
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
	</script>
</body>
</html>

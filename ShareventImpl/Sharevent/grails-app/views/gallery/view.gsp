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
		<div class="span12">
			<g:message code="userDef.participationLink"/>
			<g:link controller="gallery" action="view" id="${galleryInstance.id}" >
				<sv:shortUrl gallery="${galleryInstance}" />
			</g:link>
		</div>
	</div>

	<g:if test="${isAdmin}">
		<div class="row">
			<div class="span12">
				<g:message code="userDef.administrationLink" args="${[]}" />
				<g:link controller="gallery" action="view" id="${galleryInstance.id}" params="${[key: galleryInstance.creatorId]}" >
					<sv:shortAdminUrl gallery="${galleryInstance}" />
				</g:link>
			</div>
		</div>
	</g:if>

        <g:form controller="gallery" id="${galleryInstance.id}">

		<r:script type="text/javascript" charset="utf-8">
			function selectAllImages(boxId){
				$('input[type=checkbox]').attr('checked',$('#'+boxId).is(':checked'));
			}		
		</r:script>

		
		<div class="row control">
			<g:if test="${urls.size() > 0}">
				<div class="span3">
					<g:actionSubmit 
						class="btn btn-primary span3" 
						name="Download" 
						value="${message(code: 'userDef.downloadImages')}"
						action="download"  />
				</div>
				<div class="span2">
					<g:checkBox id="selectAll" 
						name="selectAll"
						value="${true}"
						onclick="selectAllImages('selectAll')"/>
						Select All
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
									<img  src="${imageUrl.value}" id="img_${imageUrl.key}"/>
								</a>
								<div class="caption">
									<g:checkBox  name="image_${imageUrl.key}" value="${true}"/>
									<g:message code="userDef.selectMe" args="${[]}" /> 
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
		
		<g:if test="${urls.size() > 0}">
			<div class="row">
				<div class="span3">
					<g:actionSubmit 
						name="Download" 
						value="${message(code: 'userDef.downloadImages')}" 
						action="download"
						class="btn btn-primary span3" />
				</div>
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
			</div>
		</g:if>	

		<g:hiddenField name="key" value="${galleryInstance.creatorId}" />
        </g:form>

	<script type="text/javascript" charset="utf-8">
		var ongoingUploads = 0;
	</script>


	<div class="row">
		<div class="span12">
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
	</div>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>
    	<g:message code="userDef.companyName" />: <g:message code="userDef.companySlogan" />
    </title>


	<uploader:head css="${resource(dir: 'css', file:'main.css')}"/>
  </head>
  <body>


	<div class="row">
		<div class="span12">
			<h2>${galleryInstance.title}</h2>

		</div>
		

	</div>
	
	<div class="row">
		<div class="span12">
			<g:message code="userDef.participationLink"/>
			<g:link controller="gallery" action="view" id="${galleryInstance.id}" >
				${createLink(controller: 'gallery', action: 'view', id: galleryInstance.id)}
			</g:link>
		</div>
	</div>

	<g:if test="${isAdmin}">
		<div class="row">
			<div class="span12">
				
				<g:message code="userDef.administrationLink" args="${[]}" />
				<g:link controller="gallery" action="view" id="${galleryInstance.id}" params="${[key: galleryInstance.creatorId]}" >
					${createLink(controller: 'gallery', action: 'view', id: galleryInstance.id, params: [key: galleryInstance.creatorId])}
				</g:link>
			</div>
		</div>
	</g:if>



	
        <g:form controller="gallery" id="${galleryInstance.id}">


		<!-- only show the users contribution if it is not empty -->
		<g:each var="user" in="${galleryInstance.users}">

		<div class="row control">
			<div class="span3">
				<g:actionSubmit 
					class="btn btn-primary span3" 
					name="Download" 
					value="${message(code: 'userDef.downloadImages')}"
					action="download"  />
			</div>
			<div class="span2">
				<g:checkBox name="selectAll" />
				<a href="#">Select All</a>
			</div>
			
		</div>
	
		<div class="row">
			<div class="span12">

				<ul class="thumbnails">
					<g:each in="${1..10}" var="i">
						<li class="span3">
							<a href="#" class="thumbnail">
								<img src="http://s3.amazonaws.com/com.sharevent.imagethumbs/test/${i}.jpg" />
							</a>
							<div class="caption">
								<g:checkBox  name="image_1" value="${true}" />
								<g:message code="userDef.selectMe" args="${[]}" /> 
							</div>
						</li>				 
					</g:each>
				</ul>	

			</div>			
		</div>
		
<%--
					<g:if test="${urls.size() > 0}">
						<g:each var="imageUrl" in="${urls}" >
							
						          <a href="${urlsFull[imageUrl.key]}">
						            <img src="${imageUrl.value}" id="img_${imageUrl.key}" width="250px" />
						          </a>
						          <g:checkBox class="selectBox" name="image_${imageUrl.key}" value="${true}" />
						          <g:message code="userDef.selectMe" args="${[]}" /> 
						
						</g:each>
					</g:if>
					<g:else>
						<p id='emptyGalleryWarningDiv'>
							<g:message code="view.gallery.view.emptyGallery" />
						</p>
					</g:else>
--%>


				
		</g:each>


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
						class="btn btn-warning span3" />
				</div>
				<div class="span3">
					<g:actionSubmit 
						name="removeGallery" 
						value="${message(code: 'userDef.deleteGallery')}" 
						action="deleteGallery"
						class="btn btn-danger span3" />
				</div>
			</g:if>
		</div>
		

		<g:hiddenField name="key" value="${galleryInstance.creatorId}" />
        </g:form>



	<div class="row">
		<div class="span12">
			<uploader:uploader 
				id="${galleryInstance.id}"
				url="${[controller: 'gallery', action: 'uploadImage', id: galleryInstance.id]}"
				multiple="true" 
				sizeLimit="5000000">
		</div>
	</div>
	


	<uploader:onSubmit>
			ongoingUploads += 1;
			$('#divContributeSpinner').show();
			console.log('On submit done.');
		</uploader:onSubmit>
		<uploader:onComplete>
			var attrs = "";
			var url = "";
			if(responseJSON.success == true) {
				url = responseJSON.imageURL;
				attrs = "src='" + url + "' width='250px'";
			}
			else {
				url = ${resource(dir: 'images', file: 'error.png')};
				attrs = "src='" + url + "alt='Something went wrong while uploading your image.'";
			}
			
			// TODO add select box
			// TODO unique ids!
			var imgDiv = "<div id='all_images' class='galleryImageDiv'><img " + attrs + "></img></div>";
			$('#galleryHidePhotosDivBottom').before(imgDiv);

			$('#emptyGalleryWarningDiv').hide();

			ongoingUploads -= 1;
			if (ongoingUploads == 0) {
				$('#divContributeSpinner').hide();
				$('#divPostUpload').show();
			}
			console.log("On complete done.");
		</uploader:onComplete>
	</uploader:uploader>
</body>
</html>

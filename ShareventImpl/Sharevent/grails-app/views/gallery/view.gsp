<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
	<g:javascript library="jquery" />
    <g:javascript src="gallery-view.js" />
	<uploader:head css="${resource(dir: 'css', file:'main.css')}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title><g:message code="userDef.companyName" />: <g:message code="userDef.companySlogan" /></title>
  </head>
  <body>
    <div id="viewGalleryViewport">
        <h2>${galleryInstance.title}</h2>
        <p>
	    <span class="spanBold"><g:message code="userDef.participationLink" args="${[]}" /></span><g:link controller="gallery" action="view" id="${galleryInstance.id}" >${createLink(controller: 'gallery', action: 'view', id: galleryInstance.id)}</g:link>
		<br />
		<g:if test="${isAdmin}">
			<span class="spanBold"><g:message code="userDef.administrationLink" args="${[]}" /></span><g:link controller="gallery" action="view" id="${galleryInstance.id}" params="${[key: galleryInstance.creatorId]}" >${createLink(controller: 'gallery', action: 'view', id: galleryInstance.id, params: [key: galleryInstance.creatorId])}</g:link>
		</g:if>
		<br />
        </p>

        <g:form controller="gallery" id="${galleryInstance.id}">

		<div class="buttons">
			<g:actionSubmit name="Download" value="${message(code: 'userDef.downloadImages')}" action="download" class="save" />
		</div>
	<!-- only show the users contribution if it is not empty -->
		<g:each var="user" in="${galleryInstance.users}">
			<div id="user_${user.id}">
				<div id="userBoxView" >
					<div class="galleryHidePhotosDiv" id="galleryHidePhotosDivTop">
						<p>
							<a class="hide" href="#"><g:message code="userDef.hide" args="${[]}" /></a>
						</p>
					</div>
					<g:if test="${urls.size() > 0}">
						<g:each var="imageUrl" in="${urls}" >
							<div id="all_images" class="galleryImageDiv" >
								<img src="${imageUrl.value}" width="250px" />
								<br />
								<g:checkBox class="selectBox" name="image_${imageUrl.key}" value="${true}" /> <g:message code="userDef.selectMe" args="${[]}" /> 
							</div>
						</g:each>
					</g:if>
					<g:else>
						<p id='emptyGalleryWarningDiv'><g:message code="view.gallery.view.emptyGallery" /></p>
					</g:else>
					<div class="galleryHidePhotosDiv" id="galleryHidePhotosDivBottom">
						<p>
							<a class="hide" href="#"><g:message code="userDef.hide" args="${[]}" /></a>
						</p>
					</div>
				</div>
			</div>
		</g:each>

        <div class="buttons">
            <g:actionSubmit name="Download" value="${message(code: 'userDef.downloadImages')}" action="download" class="save" />
		<g:if test="${isAdmin}">
			<g:hiddenField name="key" value="${galleryInstance.creatorId}" />
			<g:actionSubmit name="removeImages" value="${message(code: 'userDef.deleteSelection')}" action="deleteImages" class="delete" />
			<g:actionSubmit name="removeGallery" value="${message(code: 'userDef.deleteGallery')}" action="deleteGallery" class="delete" />
		</g:if>
        </div>


        </g:form>


			<!-- upload div -->
			<div id="upload_div">
				<uploader:uploader id="${galleryInstance.id}" url="${[controller: 'gallery', action: 'uploadImage', id: galleryInstance.id]}" multiple="true" sizeLimit="5000000">
					<uploader:onSubmit>
						ongoingUploads += 1;
						$('#divContributeSpinner').show();
					</uploader:onSubmit>
					<uploader:onComplete>
						var attrs = "";
						if(responseJSON.success == true) {
							attrs = "src='" + responseJSON.imageURL + "' width='250px'";
						}
						else {
							attrs = "src='" + ${resource(dir: 'images', file: 'error.png')} + "alt='Something went wrong while uploading your image.'";
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
					</uploader:onComplete>
				</uploader:uploader>
			</div>
    </div>
  </body>
</html>

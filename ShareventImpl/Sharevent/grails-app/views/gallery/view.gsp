<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
	<uploader:head css="${resource(dir: 'css', file:'main.css')}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>
    	<g:message code="userDef.companyName" />: <g:message code="userDef.companySlogan" />
    </title>
    <r:require modules="foundation, jquery, gallery-view"/>
  </head>
  <body>
    <div id="viewGalleryViewport">
        <h1>${galleryInstance.title}</h1>
        <p>
	    <span class="spanBold"><g:message code="userDef.participationLink" args="${[]}" /></span><g:link controller="gallery" action="view" id="${galleryInstance.id}" >${createLink(controller: 'gallery', action: 'view', id: galleryInstance.id)}</g:link>
		<br />
		<g:if test="${isAdmin}">
			<span class="spanBold"><g:message code="userDef.administrationLink" args="${[]}" /></span><g:link controller="gallery" action="view" id="${galleryInstance.id}" params="${[key: galleryInstance.creatorId]}" >${createLink(controller: 'gallery', action: 'view', id: galleryInstance.id, params: [key: galleryInstance.creatorId])}</g:link>
		</g:if>
		<br />
        </p>

        <g:form controller="gallery" id="${galleryInstance.id}">

		<g:actionSubmit 
					name="Download" 
					value="${message(code: 'userDef.downloadImages')}" 
					action="download" 
					class="btn primary" />

	<!-- only show the users contribution if it is not empty -->
		<g:each var="user" in="${galleryInstance.users}">
			<div id="user_${user.id}">
				<div id="userBoxView" >
					<div class="selectAll">
						<g:checkBox name="selectAll" /><a href="#">Select All</a>
					</div>
					<div class="galleryHidePhotosDiv" id="galleryHidePhotosDivTop">
						<p>
							<a class="hide" href="#"><g:message code="userDef.hide" args="${[]}" /></a>
						</p>
					</div>
					<g:if test="${urls.size() > 0}">
						<ul class="block-grid mobile four-up">
							<g:each var="imageUrl" in="${urls}" >
						        <li>
						          <a href="${urlsFull[imageUrl.key]}">
						            <img src="${imageUrl.value}" id="img_${imageUrl.key}"/>
						          </a>
						          <g:checkBox class="selectBox" name="image_${imageUrl.key}" value="${true}" />
						          <g:message code="userDef.selectMe" args="${[]}" /> 
						        </li>
							</g:each>
						</ul>
					</g:if>
					<g:else>
						<p id='emptyGalleryWarningDiv'>
							<g:message code="view.gallery.view.emptyGallery" />
						</p>
					</g:else>
					<div class="galleryHidePhotosDiv" id="galleryHidePhotosDivBottom">
						<p>
							<a class="hide" href="#"><g:message code="userDef.hide" args="${[]}" /></a>
						</p>
					</div>
					<div class="selectAll">
						<g:checkBox name="selectAll" /><a href="#">Select All</a>
					</div>
				</div>
			</div>
		</g:each>

        <g:actionSubmit
					name="Download" 
					value="${message(code: 'userDef.downloadImages')}" 
					action="download" 
					class="btn primary" />
		<g:if test="${isAdmin}">
			<g:hiddenField name="key" value="${galleryInstance.creatorId}" />
			<g:actionSubmit 
					name="removeImages" 
					value="${message(code: 'userDef.deleteSelection')}" 
					ction="deleteImages" 
					class="btn" />
			<g:actionSubmit 
					name="removeGallery" 
					value="${message(code: 'userDef.deleteGallery')}" 
					action="deleteGallery" 
					class="btn danger" />
		</g:if>


        </g:form>


			<!-- upload div -->
			<div id="upload_div">
				<uploader:uploader id="${galleryInstance.id}" url="${[controller: 'gallery', action: 'uploadImage', id: galleryInstance.id]}" multiple="true" sizeLimit="5000000">
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
			</div>
    </div>
  </body>
</html>

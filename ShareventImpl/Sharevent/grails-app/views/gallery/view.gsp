<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
	<g:javascript library="jquery" />
    <g:javascript src="gallery-view.js" />
	<g:javascript src="facebox.js" />
	<uploader:head css="${resource(dir: 'css', file:'main.css')}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title><g:message code="userDef.companyName" />: <g:message code="userDef.companySlogan" /></title>
	<g:javascript>
		$(function(){
			// FACEBOX
			console.log('found ' + $('a[rel*="facebox"]').length + ' elements to facebox');
			$('a[rel*=facebox]').facebox({
				loadingImage : "${resource(dir: 'images/facebox', file: 'loading.gif')}",
				closeImage   : "${resource(dir: 'images/facebox', file: 'closelabel.png')}"
			})
		});
	</g:javascript>
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
					<div class="selectAll">
						<g:checkBox name="selectAll" /><a href="#">Select All</a>
					</div>
					<div class="galleryHidePhotosDiv" id="galleryHidePhotosDivTop">
						<p>
							<a class="hide" href="#"><g:message code="userDef.hide" args="${[]}" /></a>
						</p>
					</div>
					<g:if test="${urls.size() > 0}">
						<g:each var="imageUrl" in="${urls}" >
							<div id="all_images" class="galleryImageDiv" >
								<a href="${urlsFull[imageUrl.key]}">
									<img src="${imageUrl.value}" id="img_${imageUrl.key}" width="250px" />
								</a>
								<br />
								<g:checkBox class="selectBox" name="image_${imageUrl.key}" value="${true}" /> <g:message code="userDef.selectMe" args="${[]}" /> 
							</div>
						</g:each>
					</g:if>
					<g:else>
						<p id='emptyGalleryWarningDiv'><g:message code="view.gallery.view.emptyGallery" /></p>
					</g:else>
					<%-- XXX mocked images for layouting --%>
					<div id="all_images" class="galleryImageDiv">
						<a href="http://s3.amazonaws.com/com.sharevent.images/test/1.jpg">
							<img src="http://s3.amazonaws.com/com.sharevent.imagethumbs/test/1.jpg" id="img_1" width="250px" />
						</a>
						<br />
						<g:checkBox class="selectBox" name="image_1" value="${true}" /> <g:message code="userDef.selectMe" args="${[]}" /> 
					</div>
					<div id="all_images" class="galleryImageDiv">
						<a href="http://s3.amazonaws.com/com.sharevent.images/test/2.jpg">
							<img src="http://s3.amazonaws.com/com.sharevent.imagethumbs/test/2.jpg" id="img_1" width="250px" />
						</a>
						<br />
						<g:checkBox class="selectBox" name="image_2" value="${true}" /> <g:message code="userDef.selectMe" args="${[]}" /> 
					</div>
					<div id="all_images" class="galleryImageDiv">
						<a href="http://s3.amazonaws.com/com.sharevent.images/test/3.jpg">
							<img src="http://s3.amazonaws.com/com.sharevent.imagethumbs/test/3.jpg" id="img_1" width="250px" />
						</a>
						<br />
						<g:checkBox class="selectBox" name="image_3" value="${true}" /> <g:message code="userDef.selectMe" args="${[]}" /> 
					</div>
					<div id="all_images" class="galleryImageDiv">
						<a href="http://s3.amazonaws.com/com.sharevent.images/test/4.jpg">
							<img src="http://s3.amazonaws.com/com.sharevent.imagethumbs/test/4.jpg" id="img_1" width="250px" />
						</a>
						<br />
						<g:checkBox class="selectBox" name="image_4" value="${true}" /> <g:message code="userDef.selectMe" args="${[]}" /> 
					</div>
					<div id="all_images" class="galleryImageDiv">
						<a href="http://s3.amazonaws.com/com.sharevent.images/test/5.jpg">
							<img src="http://s3.amazonaws.com/com.sharevent.imagethumbs/test/5.jpg" id="img_1" width="250px" />
						</a>
						<br />
						<g:checkBox class="selectBox" name="image_5" value="${true}" /> <g:message code="userDef.selectMe" args="${[]}" /> 
					</div>
					<div id="all_images" class="galleryImageDiv">
						<a href="http://s3.amazonaws.com/com.sharevent.images/test/6.jpg">
							<img src="http://s3.amazonaws.com/com.sharevent.imagethumbs/test/6.jpg" id="img_1" width="250px" />
						</a>
						<br />
						<g:checkBox class="selectBox" name="image_6" value="${true}" /> <g:message code="userDef.selectMe" args="${[]}" /> 
					</div>
					<div id="all_images" class="galleryImageDiv">
						<a href="http://s3.amazonaws.com/com.sharevent.images/test/7.jpg">
							<img src="http://s3.amazonaws.com/com.sharevent.imagethumbs/test/7.jpg" id="img_1" width="250px" />
						</a>
						<br />
						<g:checkBox class="selectBox" name="image_7" value="${true}" /> <g:message code="userDef.selectMe" args="${[]}" /> 
					</div>
					<div id="all_images" class="galleryImageDiv">
						<a href="http://s3.amazonaws.com/com.sharevent.images/test/8.jpg">
							<img src="http://s3.amazonaws.com/com.sharevent.imagethumbs/test/8.jpg" id="img_1" width="250px" />
						</a>
						<br />
						<g:checkBox class="selectBox" name="image_8" value="${true}" /> <g:message code="userDef.selectMe" args="${[]}" /> 
					</div>
					<div id="all_images" class="galleryImageDiv">
						<a href="http://s3.amazonaws.com/com.sharevent.images/test/9.jpg">
							<img src="http://s3.amazonaws.com/com.sharevent.imagethumbs/test/9.jpg" id="img_1" width="250px" />
						</a>
						<br />
						<g:checkBox class="selectBox" name="image_9" value="${true}" /> <g:message code="userDef.selectMe" args="${[]}" /> 
					</div>
					<div id="all_images" class="galleryImageDiv">
						<a href="http://s3.amazonaws.com/com.sharevent.images/test/10.jpg">
							<img src="http://s3.amazonaws.com/com.sharevent.imagethumbs/test/10.jpg" id="img_1" width="250px" />
						</a>
						<br />
						<g:checkBox class="selectBox" name="image_10" value="${true}" /> <g:message code="userDef.selectMe" args="${[]}" /> 
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

<%--
  Peter A. Chronz
  Sun Jun 19 19:32:20 CEST 2011
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <g:javascript library="prototype" />
    <g:javascript src="gallery-view.js" />
	<uploader:head css="${resource(dir: 'css', file:'main.css')}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Sharevent: Easy Photo Sharing</title>
  </head>
  <body>
    <div id="viewGalleryViewport">
        <h2>${galleryInstance.title}</h2>
        <p>
	    <span class="spanBold"><g:message code="userDef.participationLink" args="${[]}" /></span><g:link controller="gallery" action="view" id="${galleryInstance.id}" >${createLink(controller: 'gallery', action: 'view', id: galleryInstance.id)}</g:link>
		<br />
        </p>

        <g:form controller="gallery" id="${galleryInstance.id}">

            <g:actionSubmit name="Download" value="${message(code: 'userDef.downloadImages')}" action="download" class="buttons" />
	<!-- only show the users contribution if it is not empty -->
		<g:each var="user" in="${galleryInstance.users}">
			<g:if test="${urls.size() > 0}">
				<div id="user_${user.id}">
					<div id="userBoxView" >
						<div class="galleryHidePhotosDiv">
							<p>
								<a class="hide" href="#"><g:message code="userDef.hide" args="${[]}" /></a>
							</p>
						</div>
						<g:each var="imageUrl" in="${urls}" >
							<div id="all_images" class="galleryImageDiv" >
								<img src="${imageUrl.value}" width="250px" />
								<br />
								<%-- <g:checkBox class="selectBox" name="image_${id}" value="${true}" /> <g:message code="userDef.selectMe" args="${[]}" /> --%>
							</div>
						</g:each>
						<div class="galleryHidePhotosDiv">
							<p>
								<a class="hide" href="#"><g:message code="userDef.hide" args="${[]}" /></a>
							</p>
						</div>
					</div>
				</div>
			</g:if>
		</g:each>

        <p>
			<!-- Addding a logout-link if logged in as admin might be a good idea -->

            <g:if test="${session.user?.contributedGallery?.id == galleryInstance.id && session.user?.id == session.user.contributedGallery.adminKey}">
                <!-- TODO issue warning before deleting some images or even whole gallery -->
                <g:actionSubmit name="RemoveSelection" value="${message(code: 'userDef.deleteSelection')}" action="deleteImages" />
                <g:actionSubmit name="DeleteGallery" value="${message(code: 'userDef.deleteGallery')}" action="deleteGallery" />
            </g:if>
        </p>

            <g:actionSubmit name="Download" value="${message(code: 'userDef.downloadImages')}" action="download" class="buttons" />

        </g:form>


			<!-- upload div -->
			<div id="upload_div">
				<uploader:uploader id="${galleryInstance.id}" url="${[controller: 'gallery', action: 'uploadImageDirect', id: galleryInstance.id]}" multiple="true" sizeLimit="5000000">
					<uploader:onSubmit>
						$$('#divContributeSpinner').each(function(s) {
							ongoingUploads += 1;
							s.show();
						});
					</uploader:onSubmit>
					<uploader:onComplete>
						$$('.galleryHidePhotosDiv').each(function(s) {
							Element.insert(s, {before: "<div id='all_images' class='galleryImageDiv'><img src='" + responseJSON.imageURL + "' width='250px'>"});
							Element.update(s);
						});
						$$('#divContributeSpinner').each(function(s) {
							ongoingUploads -= 1;
							if (ongoingUploads == 0) {
								s.hide();
								$$('#divPostUpload').each(function(s) {
									s.show();
								});

						//		$$('#divImageUpload').each(function(s){
						//			s.hide();
						//		});
							}
						});
					</uploader:onComplete>
				</uploader:uploader>
			</div>
    </div>
  </body>
</html>

<%--
  Peter A. Chronz
  Sat Jun 18 19:49:47 CEST 2011
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
	<!-- Image uploader plugin -->
	<uploader:head />
  	<g:javascript library="prototype" />
	<g:javascript src="gallery-contribute-images.js" />
	<g:if test="${session.user != null && session.user?.contributedGallery.id == galleryInstance.id}">
		<g:javascript>
			var isLoggedIn = true;
		</g:javascript>
	</g:if>
	<g:else>
		<g:javascript>
			var isLoggedIn = false;
		</g:javascript>
	</g:else>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Sharevent: Easy Photo Sharing</title>
  </head>
  <body>
    <div id="contributeImagesViewport">
        <h2>Contribute to gallery: ${galleryInstance.title}</h2>
        <p>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
	    <g:if test="${session.user != null && session.user.contributedGallery.id == galleryInstance.id}">
                <div class="message">
		    You are logged in.
		    <g:link controller="gallery" action="logout" id="${galleryInstance.id}">Logout</g:link>
		</div>
            </g:if>
            <br />
            ${galleryInstance.location + ", " + galleryInstance.date.format("dd.MM.yyyy")}
            <br />
            Creator: <a href="mailto:${galleryInstance.creatorEmail}">${galleryInstance.creatorFirstName + " " + galleryInstance.creatorLastName}</a>
        </p>

	<div id="divImageUpload">
		<h3>Choose your images and upload them</h3>
		<!-- TODO get rid of imageUpload, which is GPL code -->
		<uploader:uploader id="${galleryInstance.id}" url="[controller: 'gallery', action: 'uploadImage']" multiple="true" />
	</div>

	<g:link controller="gallery" action="view" id="${galleryInstance.id}">back</g:link>

    </div>
  </body>
</html>

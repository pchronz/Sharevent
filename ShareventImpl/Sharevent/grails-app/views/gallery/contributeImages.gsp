<%--
  User: peterandreaschronz
  Date: 08.06.11
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
	<!-- Image uploader plugin -->
	<uploader:head />
  	<g:javascript library="prototype" />
	<g:javascript src="gallery-contribute-images.js" />
	<g:if test="${session.isLoggedIn && session.galleryId == galleryInstance.id}">
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
	    <g:if test="${session.isLoggedIn && session.galleryId == galleryInstance.id}">
                <div class="message">You are logged in.</div>
            </g:if>
            <br />
            ${galleryInstance.location + ", " + galleryInstance.date.format("dd.MM.yyyy")}
            <br />
            Creator: <a href="mailto:${galleryInstance.creatorEmail}">${galleryInstance.creatorFirstName + " " + galleryInstance.creatorLastName}</a>
        </p>


		<div id="userCreationDiv">
	<g:formRemote name="userCreationForm" url="${[controller: 'galleryUser', action: 'createNew', id: galleryInstance.id]}">
            First name
            <br/>
            <g:textField name="firstName" />
            <br/>

            Last name
            <br/>
            <g:textField name="lastName" />
            <br/>

            e-mail
            <br/>
            <g:textField name="email" />
            <br/>                  
	
            <g:submitButton id="userCreationButton" name="Create User" value="Create User" />
        </g:formRemote>
        </div>

	<div id="divImageUpload">
		<h3>Choose your images and upload them individually</h3>
		<!-- TODO get rid of imageUpload, which is GPL code -->
		<uploader:uploader id="${galleryInstance.id}" url="[controller: 'gallery', action: 'uploadImage']" multiple="true" />
	</div>

	<g:link controller="gallery" action="view" id="${galleryInstance.id}">back</g:link>

    </div>
  </body>
</html>

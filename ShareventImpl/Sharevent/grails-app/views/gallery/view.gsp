<%--
  User: peterandreaschronz
  Date: 21.05.11
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <g:javascript library="prototype" />
    <g:javascript src="gallery-view.js" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Sharevent: Easy Photo Sharing</title>
  </head>
  <body>
    <div id="viewGalleryViewport">
        <h2>${galleryInstance.title}</h2>
        <p>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
	    <g:if test="${session.isAdmin && session.galleryId == galleryInstance.id}">
	    	<div class="message">${galleryInstance.creatorFirstName}, you are logged in as admin!</div>
	    </g:if>
            <g:link controller="gallery" action="share"><img src="${resource(dir:'images',file:'Share.jpg')}" /></g:link>
            <br />
            ${galleryInstance.location + ", " + galleryInstance.date.format("dd.MM.yyyy")}
            <br />
            Creator: <a href="mailto:${galleryInstance.creatorEmail}">${galleryInstance.creatorFirstName + " " + galleryInstance.creatorLastName}</a>
        </p>

        <g:form controller="gallery" id="${galleryInstance.id}">

        <g:each var="user" in="${galleryInstance?.contributors}">
	<!-- only show the users contribution if it is not empty -->
	<g:if test="${user.imageSet.images.size() > 0}">
            <div id="user_${user.id}">
                <p><a href="mailto:${user.email}">${user.firstName} ${user.lastName}</a> <a class="selectAll" href="">all</a> <a class="selectNone" href="">none</a></p>
                <div id="userBoxView" >

                <div class="galleryHidePhotosDiv">
                    <p>
                        <a class="hide" href="">Hide</a>
                    </p>
                </div>
                <g:each var="image" in="${user.imageSet?.images}" >
                    <div id="user_${user.id}_photo_${image.id}" class="galleryImageDiv" >
                        <img src="${createLink(controller: 'image', action: 'viewImage', params: [id: image.id])}" width="250px"/>
                        <br />
                        <g:checkBox class="selectBox" name="image_${image.id}" value="${true}" /> Select me!
                    </div>
                </g:each>
                <div class="galleryHidePhotosDiv">
                    <p>
                        <a class="hide" href="">Hide</a>
                    </p>
                </div>
        </div>


        </div>

	</g:if>
        </g:each>

        <p>
            <g:link controller="gallery" action="contributeImages" id="${galleryInstance.id}"><img src="${resource(dir:'images',file:'UploadPhotos.jpg')}" alt="Upload Photos" /></g:link>

            <!-- Addding a logout-link if logged in as admin might be a good idea -->
            <g:actionSubmit name="Download" value="Download" action="download" />
            <g:if test="${session.isAdmin && session.galleryId == galleryInstance.id}">
                <!-- TODO issue warning before deleting some images or even whole gallery -->
                <g:actionSubmit name="RemoveSelection" value="Delete selection" action="deleteImages" />
                <g:actionSubmit name="DeleteGallery" value="Delete gallery" action="deleteGallery" />
            </g:if>
        </p>

        </g:form>

    </div>
  </body>
</html>

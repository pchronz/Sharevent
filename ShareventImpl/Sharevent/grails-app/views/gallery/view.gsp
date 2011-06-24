<%--
  Peter A. Chronz
  Sun Jun 19 19:32:20 CEST 2011
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
	    <g:if test="${session.user?.contributedGallery?.id == galleryInstance.id && session.user?.id == session.user.contributedGallery.adminKey}">
	    	<div class="message">
		    ${galleryInstance.creatorFirstName}, you are logged in as this gallery's admin!
		    <g:link controller="gallery" action="logout" id="${galleryInstance.id}">Logout</g:link>
		</div>
	    </g:if>
	    
	    Participation Link: <g:link controller="gallery" action="view" id="${galleryInstance.id}" >${createLink(controller: 'gallery', action: 'view', id: galleryInstance.id)}</g:link>
	    <br />
	    <g:if test="${session.user?.contributedGallery?.id == galleryInstance.id && session.user?.id == session.user.contributedGallery.adminKey}">
	    Administration Link: <g:link controller="gallery" action="view" id="${galleryInstance.id}" params="${[key: galleryInstance.adminKey]}" >${createLink(controller: 'gallery', action: 'view', id: galleryInstance.id, params: [key: galleryInstance.adminKey])}</g:link>
	    </g:if>

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
                <p><a href="mailto:${user.email}">${user.firstName} ${user.lastName}</a> <a class="selectAll" href="#">all</a> <a class="selectNone" href="#">none</a></p>
                <div id="userBoxView" >

                <div class="galleryHidePhotosDiv">
                    <p>
                        <a class="hide" href="#">Hide</a>
                    </p>
                </div>
                <g:each var="image" in="${user.imageSet?.images}" >
		    <g:if test="image != null">
                        <div id="user_${user.id}_photo_${image.id}" class="galleryImageDiv" >
                            <img src="${createLink(controller: 'image', action: 'viewImage', params: [id: image.id])}" width="250px"/>
                            <br />
                            <g:checkBox class="selectBox" name="image_${image.id}" value="${true}" /> Select me!
                        </div>
		    </g:if>
                </g:each>
                <div class="galleryHidePhotosDiv">
                    <p>
                        <a class="hide" href="#">Hide</a>
                    </p>
                </div>
        </div>


        </div>

	</g:if>
        </g:each>

        <p>
            <g:link controller="gallery" action="contributeImages" id="${galleryInstance.id}">Upload photos</g:link>

            <!-- Addding a logout-link if logged in as admin might be a good idea -->
            <g:actionSubmit name="Download" value="Download" action="download" />
            <g:if test="${session.user?.contributedGallery?.id == galleryInstance.id && session.user?.id == session.user.contributedGallery.adminKey}">
                <!-- TODO issue warning before deleting some images or even whole gallery -->
                <g:actionSubmit name="RemoveSelection" value="Delete selection" action="deleteImages" />
                <g:actionSubmit name="DeleteGallery" value="Delete gallery" action="deleteGallery" />
            </g:if>
        </p>

        </g:form>

    </div>
  </body>
</html>

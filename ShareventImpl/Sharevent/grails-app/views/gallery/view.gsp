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
	    <g:if test="${session.user?.contributedGallery?.id == galleryInstance.id && session.user?.id == session.user.contributedGallery.adminKey}">
	    	<div class="messageAdminLoggedIn">
				<g:message code="userDef.loggedInAdmin" args="${[session.user?.firstName]}" />
				<g:link controller="gallery" action="logout" id="${galleryInstance.id}">Logout</g:link>
			</div>
	    </g:if>
	    
	    <span class="spanBold"><g:message code="userDef.participationLink" args="${[]}" /></span><g:link controller="gallery" action="view" id="${galleryInstance.id}" >${createLink(controller: 'gallery', action: 'view', id: galleryInstance.id)}</g:link>
	    <br />
	    <g:if test="${session.user?.contributedGallery?.id == galleryInstance.id && session.user?.id == session.user.contributedGallery.adminKey}">
	    <span class="spanBold"><g:message code="userDef.administrationLink" args="${[]}" /></span><g:link controller="gallery" action="view" id="${galleryInstance.id}" params="${[key: galleryInstance.adminKey]}" >${createLink(controller: 'gallery', action: 'view', id: galleryInstance.id, params: [key: galleryInstance.adminKey])}</g:link>
	    </g:if>

            <br />
            ${galleryInstance.location + ", " + galleryInstance.date.format("dd.MM.yyyy")}
            <br />
            <span class="spanBold"><g:message code="userDef.creator" args="${[]}" /></span><a href="mailto:${galleryInstance.creatorEmail}">${galleryInstance.creatorFirstName + " " + galleryInstance.creatorLastName}</a>
        </p>

        <g:form controller="gallery" id="${galleryInstance.id}">

        <g:each var="user" in="${galleryInstance?.contributors}">
	<!-- only show the users contribution if it is not empty -->
	<g:if test="${user.imageSet.images.size() > 0}">
            <div id="user_${user.id}">
                <p><a href="mailto:${user.email}">${user.firstName} ${user.lastName}</a> <a class="selectAll" href="#">all</a> <a class="selectNone" href="#"><g:message code="userDef.none" args="${[]}" /></a></p>
                <div id="userBoxView" >

                <div class="galleryHidePhotosDiv">
                    <p>
                        <a class="hide" href="#"><g:message code="userDef.hide" args="${[]}" /></a>
                    </p>
                </div>
                <g:each var="image" in="${user.imageSet?.images}" >
		    <g:if test="image != null">
                        <div id="user_${user.id}_photo_${image.id}" class="galleryImageDiv" >
                            <img src="${createLink(controller: 'image', action: 'viewImage', params: [id: image.id])}" width="250px"/>
                            <br />
                            <g:checkBox class="selectBox" name="image_${image.id}" value="${true}" /> <g:message code="userDef.selectMe" args="${[]}" /> 
                        </div>
		    </g:if>
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
            <g:link controller="gallery" action="contributeImages" id="${galleryInstance.id}" class="buttons"><g:message code="userDef.uploadImages" args="${[]}" /></g:link>

            <!-- Addding a logout-link if logged in as admin might be a good idea -->
            <g:actionSubmit name="Download" value="${message(code: 'userDef.downloadImages')}" action="download" />
            <g:if test="${session.user?.contributedGallery?.id == galleryInstance.id && session.user?.id == session.user.contributedGallery.adminKey}">
                <!-- TODO issue warning before deleting some images or even whole gallery -->
                <g:actionSubmit name="RemoveSelection" value="${message(code: 'userDef.deleteSelection')}" action="deleteImages" />
                <g:actionSubmit name="DeleteGallery" value="${message(code: 'userDef.deleteGallery')}" action="deleteGallery" />
            </g:if>
        </p>

        </g:form>

    </div>
  </body>
</html>

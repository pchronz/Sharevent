<%--
  Peter A. Chronz
  Sat Jun 18 19:49:47 CEST 2011
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
	<g:javascript library="prototype" />
	<g:javascript src="contribute-images.js" />
	<!-- Image uploader plugin -->
	<uploader:head css="${resource(dir: 'css', file:'main.css')}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title><g:message code="userDef.companyName" args="${[]}" />: <g:message code="userDef.companySlogan" args="${[]}" /></title>
  </head>
  <body>
    <div id="contributeImagesViewport">
	<g:link controller="gallery" action="view" id="${galleryInstance.id}"><--<g:message code="userDef.goBack" args="${[]}" /></g:link>
        <h2><g:message code="userDef.contributeToGallery" args="${[galleryInstance.title]}" /></h2>
        <p>
	    <g:if test="${session.user != null && session.user.contributedGallery.id == galleryInstance.id}">
                <div class="message">
			<g:message code="userDef.loggedIn" args="${[session.user?.firstName]}" /> 
		    <g:link controller="gallery" action="logout" id="${galleryInstance.id}"><g:message code="userDef.logout" args="${[]}" /></g:link>
		</div>
            </g:if>
            <br />
            ${galleryInstance.location + ", " + galleryInstance.date.format("dd.MM.yyyy")}
            <br />
            <g:message code="userDef.creator" args="${[]}" /><a href="mailto:${galleryInstance.creatorEmail}">${galleryInstance.creatorFirstName + " " + galleryInstance.creatorLastName}</a>
        </p>

	<div id="divPostUpload">
	    <g:message code="userDef.uploadMoreImagesQuestion" args="${[]}" /> 
	    <br />
	    <a id="aUploadMoreImages" class="buttons"><g:message code="userDef.uploadMoreImages" args="${[]}" /></a> | <g:link controller="gallery" action="view" id="${galleryInstance.id}" class="buttons"><g:message code="userDef.goBack" args="${[]}" /></g:link>
	    <br />
	</div>

	<div id="divImageUpload">
		<h3><g:message code="userDef.chooseImages" args="${[]}" /></h3>
		<!-- TODO get rid of imageUpload, which is GPL code -->
		<uploader:uploader id="${galleryInstance.id}" url="[controller: 'gallery', action: 'uploadImage']" multiple="true" sizeLimit="5000000">
		    <uploader:onComplete>
		    	$$('#divPostUpload').each(function(s) {
				s.show();
			});
			$$('#divImageUpload').each(function(s){
				s.hide();
			});
		    </uploader:onComplete>
		</uploader:uploader>
	</div>


    </div>
  </body>
</html>

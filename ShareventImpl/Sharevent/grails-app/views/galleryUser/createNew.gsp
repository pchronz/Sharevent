<%--
  Peter A. Chronz
  Sat Jun 18 19:49:47 CEST 2011
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Sharevent: Easy Photo Sharing</title>
  </head>
  <body>
    <div id="createUserViewport">
        <h2>Create a user to contribute to gallery: ${galleryInstance.title}</h2>
        <p>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
	    <g:if test="${session.user != null && session.user?.contributedGallery == galleryInstance.id}">
                <div class="message">
		    You are logged in.
		    <g:link controller="gallery" action="logout" id="${galleryInstance.id}">Logout</g:link>
		</div>
            </g:if>
        </p>


	<div id="userCreationDiv">
	<g:form name="userCreationForm" controller="galleryUser" action="createNew" id="${galleryInstance?.id}">
            First name
            <br/>
	    <div class="${hasErrors(bean: user, field: 'firstName', 'errors')}">
                <g:textField name="firstName" value="${fieldValue(bean: user, field: 'firstName')}" />
	          <g:hasErrors bean="${user}" field="firstName">
	              Please provide your first name.
	          </g:hasErrors>
	    </div>
            <br/>

            Last name
            <br/>
	    <div class="${hasErrors(bean: user, field: 'lastName', 'errors')}">
            <g:textField name="lastName" value="${fieldValue(bean: user, field: 'lastName')}"/>
	          <g:hasErrors bean="${user}" field="lastName">
	              Please provide your last name.
	          </g:hasErrors>
	    </div>
            <br/>

            e-mail
            <br/>
	    <div class="${hasErrors(bean: user, field: 'email', 'errors')}">
                <g:textField name="email" value="${fieldValue(bean: user, field: 'email')}"/>
	        <g:hasErrors bean="${user}" field="email">
	            Please provide your email address.
	        </g:hasErrors>
	    </div>
            <br/>                  
	
            <g:submitButton id="userCreationButton" name="Create User" value="Create User" />
        </g:form>
        </div>

	<g:link controller="gallery" action="view" id="${galleryInstance.id}">back</g:link>

    </div>
  </body>
</html>

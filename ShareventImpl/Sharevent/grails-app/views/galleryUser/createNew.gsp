<%--
  Peter A. Chronz
  Sat Jun 18 19:49:47 CEST 2011
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title><g:message code="userDef.companyName" args="${[]}" />: <g:message code="userDef<g:message code="userDef.companySlogan" args="${[]}" />." args="${[]}" /></title>
  </head>
  <body>
    <div id="createUserViewport">
        <h2><g:message code="userDef.createNewUser" args="${[galleryInstance.title]}" /></h2>
        <p>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
	    <g:if test="${session.user != null && session.user?.contributedGallery == galleryInstance.id}">
                <div class="message">
		    <g:message code="userDef.loggedIn" args="${[]}" /> 
		    <g:link controller="gallery" action="logout" id="${galleryInstance.id}"><g:message code="userDef.logout" args="${[]}" /></g:link>
		</div>
            </g:if>
        </p>


	<div id="userCreationDiv">
	<g:form name="userCreationForm" controller="galleryUser" action="createNew" id="${galleryInstance?.id}">
            <g:message code="userDef.firstName" args="${[]}" /> 
            <br/>
	    <div class="${hasErrors(bean: user, field: 'firstName', 'errors')}">
                <g:textField name="firstName" value="${fieldValue(bean: user, field: 'firstName')}" />
	          <g:hasErrors bean="${user}" field="firstName">
	             <g:message code="userDef.error.provideFirstName" args="${[]}" /> 
	          </g:hasErrors>
	    </div>
            <br/>

            <g:message code="userDef.lastName" args="${[]}" /> 
            <br/>
	    <div class="${hasErrors(bean: user, field: 'lastName', 'errors')}">
            <g:textField name="lastName" value="${fieldValue(bean: user, field: 'lastName')}"/>
	          <g:hasErrors bean="${user}" field="lastName">
	              <g:message code="userDef.error.provideLastName" args="${[]}" /> 
	          </g:hasErrors>
	    </div>
            <br/>

            <g:message code="userDef.email" args="${[]}" /> 
            <br/>
	    <div class="${hasErrors(bean: user, field: 'email', 'errors')}">
                <g:textField name="email" value="${fieldValue(bean: user, field: 'email')}"/>
	        <g:hasErrors bean="${user}" field="email">
	            <g:message code="userDef.error.provideEmail" args="${[]}" /> 
	        </g:hasErrors>
	    </div>
            <br/>                  
	
            <g:submitButton id="userCreationButton" name="Create User" value="${message(code: 'userDef.createUser')}" />
        </g:form>
        </div>

	<g:link controller="gallery" action="view" id="${galleryInstance.id}"><g:message code="userDef.goBack" args="${[]}" /></g:link>

    </div>
  </body>
</html>

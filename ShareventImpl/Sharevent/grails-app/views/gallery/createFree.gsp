<%--
  Peter A. Chronz
  Sat Jun 18 20:09:54 CEST 2011
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title><g:message code="userDef.companyName" args="${[]}" />: <g:message code="userDef.companySlogan" args="${[]}" /></title>
  </head>
  <body>
  <div id="createFreeGalleryViewport">
      <h2><g:message code="userDef.startFreeGallery" args="${[]}" /></h2>
      <g:form controller="gallery" action="share" >
          <p>
               
              <br />
	      <div class="${hasErrors(bean: galleryInstance, field: 'title', 'errors')}">
                  <g:textField name="title"  value="${fieldValue(bean: galleryInstance, field: 'title')}"/>
	          <g:hasErrors bean="${galleryInstance}" field="title">
	              <g:message code="userDef.error.provideGalleryTitle" args="${[]}" /> 
	          </g:hasErrors>
	      </div>
              <br />

              <g:message code="userDef.galleryLocation" args="${[]}" /> 
	      <br />
	      <div class="${hasErrors(bean: galleryInstance, field: 'location', 'errors')}">
                  <g:textField name="location"  value="${fieldValue(bean: galleryInstance, field: 'location')}"/>
	          <g:hasErrors bean="${galleryInstance}" field="location">
	              <g:message code="userDef.error.provideGalleryLocation" args="${[]}" /> 
	          </g:hasErrors>
	      </div>
              <br />

              <g:message code="userDef.galleryDate" args="${[]}" /> 
	      <div class="${hasErrors(bean: galleryInstance, field: 'date', 'errors')}">
                  <g:datePicker name="date" precision="day" years="${1930..2015}"></g:datePicker>
	          <g:hasErrors bean="${galleryInstance}" field="date">
	              <g:message code="userDef.error.provideGalleryDate" args="${[]}" /> 
	          </g:hasErrors>
	      </div>
              <br />

              <g:message code="userDef.userFirstName" args="${[]}" /> 
              <br />
	      <div class="${hasErrors(bean: galleryInstance, field: 'creatorFirstName', 'errors')}">
                  <g:textField name="creatorFirstName"  value="${fieldValue(bean: galleryInstance, field: 'creatorFirstName')}" />
	          <g:hasErrors bean="${galleryInstance}" field="creatorFirstName">
	              <g:message code="userDef.error.provideUserFirstName" args="${[]}" /> 
	          </g:hasErrors>
	      </div>
              <br />
              <g:message code="userDef.userLastName" args="${[]}" /> 
              <br />
	      <div class="${hasErrors(bean: galleryInstance, field: 'creatorLastName', 'errors')}">
                  <g:textField name="creatorLastName"  value="${fieldValue(bean: galleryInstance, field: 'creatorLastName')}" />
	          <g:hasErrors bean="${galleryInstance}" field="creatorLastName">
	              <g:message code="userDef.error.provideUserLastName" args="${[]}" /> 
	          </g:hasErrors>
	      </div>
              <br />
              <g:message code="userDef.userEmail" args="${[]}" /> 
              <br />
	      <div class="${hasErrors(bean: galleryInstance, field: 'creatorEmail', 'errors')}">
                  <g:textField name="creatorEmail" value="${fieldValue(bean: galleryInstance, field: 'creatorEmail')}" />
	          <g:hasErrors bean="${galleryInstance}" field="creatorEmail">
	              <g:message code="userDef.error.provideUserEmail" args="${[]}" /> 
	          </g:hasErrors>
	      </div>
              <br />

              <g:submitButton name="create-button" value="${message(code: 'userDef.button.createGallery')}" />

          </p>
      </g:form>
      </div>
  </body>
</html>

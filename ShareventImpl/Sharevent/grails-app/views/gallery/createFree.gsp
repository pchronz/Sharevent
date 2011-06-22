<%--
  Peter A. Chronz
  Sat Jun 18 20:09:54 CEST 2011
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Sharevent: Easy Photo Sharing</title>
  </head>
  <body>
  <div id="createFreeGalleryViewport">
      <h2>Start a free gallery</h2>
      <g:form controller="gallery" action="share" >
          <p>
              Title
              <br />
	      <div class="${hasErrors(bean: galleryInstance, field: 'title', 'errors')}">
                  <g:textField name="title"  value="${fieldValue(bean: galleryInstance, field: 'title')}"/>
	          <g:hasErrors bean="${galleryInstance}" field="title">
	              Please provide a title for your gallery.
	          </g:hasErrors>
	      </div>
              <br />

              Location
	      <br />
	      <div class="${hasErrors(bean: galleryInstance, field: 'location', 'errors')}">
                  <g:textField name="location"  value="${fieldValue(bean: galleryInstance, field: 'location')}"/>
	          <g:hasErrors bean="${galleryInstance}" field="location">
	              Please provide a location for your gallery.
	          </g:hasErrors>
	      </div>
              <br />

              Date
	      <div class="${hasErrors(bean: galleryInstance, field: 'date', 'errors')}">
                  <g:datePicker name="date" precision="day" years="${1930..2015}"></g:datePicker>
	          <g:hasErrors bean="${galleryInstance}" field="date">
	              Please provide a valid date for your event.
	          </g:hasErrors>
	      </div>
              <br />

              Your first name
              <br />
	      <div class="${hasErrors(bean: galleryInstance, field: 'creatorFirstName', 'errors')}">
                  <g:textField name="creatorFirstName"  value="${fieldValue(bean: galleryInstance, field: 'creatorFirstName')}" />
	          <g:hasErrors bean="${galleryInstance}" field="creatorFirstName">
	              Please provide your first name.
	          </g:hasErrors>
	      </div>
              <br />
              Your last name
              <br />
	      <div class="${hasErrors(bean: galleryInstance, field: 'creatorLastName', 'errors')}">
                  <g:textField name="creatorLastName"  value="${fieldValue(bean: galleryInstance, field: 'creatorLastName')}" />
	          <g:hasErrors bean="${galleryInstance}" field="creatorLastName">
	              Please provide your last name.
	          </g:hasErrors>
	      </div>
              <br />
              Your e-mail address (Will be displayed in the gallery)
              <br />
	      <div class="${hasErrors(bean: galleryInstance, field: 'creatorEmail', 'errors')}">
                  <g:textField name="creatorEmail" value="${fieldValue(bean: galleryInstance, field: 'creatorEmail')}" />
	          <g:hasErrors bean="${galleryInstance}" field="creatorEmail">
	              Please provide a valid e-mail address.
	          </g:hasErrors>
	      </div>
              <br />

              <g:submitButton name="create-button" value="Create!" />

          </p>
      </g:form>
      </div>
  </body>
</html>

<%--
  User: peterandreaschronz
  Date: 21.05.11
  Time: 11:08
  To change this template use File | Settings | File Templates.
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
              <g:textField name="title" />
              <br />

              Location<br />
              <g:textField name="location" />
              <br />

              Date
              <g:datePicker name="date" precision="day" ></g:datePicker>
              <br />

              Your first name
              <br />
              <g:textField name="firstName" />
              <br />
              Your last name
              <br />
              <g:textField name="lastName" />
              <br />
              Your e-mail address (Will be displayed in the gallery)
              <br />
              <g:textField name="email" />
              <br />

              <g:submitButton name="create-button" value="Create!" />

          </p>
      </g:form>
      </div>
  </body>
</html>

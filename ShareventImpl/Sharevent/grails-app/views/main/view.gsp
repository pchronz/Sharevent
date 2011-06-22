<%--
  User: peterandreaschronz
  Date: 21.05.11
  Time: 10:18
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
    <div id="mainViewport">

        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>

        <p>
            <img src="${resource(dir:"images",file:"ThreeSteps.jpg")}" alt="3 steps to share photos" width="400" />
        </p>

        <p>
            <g:link controller="gallery" action="createFree">Create a gallery</g:link>
            <g:link controller="gallery" action="viewExample">View an example gallery</g:link>
        </p>
    </div>
</body>
</html>

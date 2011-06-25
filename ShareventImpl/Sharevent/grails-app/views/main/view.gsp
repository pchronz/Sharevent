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
    <title><g:message code="userDef.companyName" args="${[]}" />: <g:message code="userDef.companySlogan" args="${[]}" /></title>
  </head>
  <body>
    <div id="mainViewport">

        <p>
            <img src="${resource(dir:"images",file:"ThreeSteps.jpg")}" alt="3 steps to share photos" width="400" />
        </p>

        <p>
            <g:link controller="gallery" action="createFree"><g:message code="userDef.createFreeGallery" args="${[]}" /></g:link>
            <g:link controller="gallery" action="viewExample"><g:message code="userDef.viewExampleGallery" args="${[]}" /></g:link>
        </p>
    </div>
</body>
</html>

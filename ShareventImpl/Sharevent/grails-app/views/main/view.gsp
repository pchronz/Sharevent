<%--
  Created by IntelliJ IDEA.
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
    <div style="width: 500px; margin-left: auto; margin-right: auto;">

        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>

        <p>
            <img src="${resource(dir:"images",file:"SharingPeople.jpg")}" alt="People sharing photos in a gallery" width="400"/>
        </p>

        <p>
            <img src="${resource(dir:"images",file:"ThreeSteps.jpg")}" alt="3 steps to share photos" width="400" />
        </p>

        <p>
            <g:link controller="gallery" action="createFree"><img src="${resource(dir:"images",file:"FreeGallery.jpg")}" alt="Create a free gallery" /></g:link>
            <g:link controller="gallery" action="createPremium"><img src="${resource(dir:"images",file:"PremiumGallery.jpg")}" alt="Create a premium gallery" /></g:link>
        </p>
    <div id="AdDiv" style="width: 501px; margin-left: auto; margin-right: auto;">
                <p style="color: blue;">Advertisement</p>
                <g:link controller="ads" action="view"><img src="${resource(dir:"images",file:"MindConnect.jpg")}" /></a></g:link>
    </div>
    </div>
</body>
</html>
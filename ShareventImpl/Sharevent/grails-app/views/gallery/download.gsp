<%--
  Created by IntelliJ IDEA.
  User: peterandreaschronz
  Date: 21.05.11
  Time: 11:50
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
    <div style="width: 850px; margin-left: auto; margin-right: auto;">
    <h2>Downloading...</h2>
    <p>
        MyGalleryName.zip
        <br />
        <div id="popImage1" style="float: left; margin: 5px;">
            <img src="${resource(dir:'images/galleryPhotos',file:'1.jpg')}" width="200" />
        </div>
        <div id="popImage2" style="float: left; margin: 5px;">
            <img src="${resource(dir:'images/galleryPhotos',file:'2.jpg')}" width="200" />
        </div>
        <div id="popImage3" style="float: left; margin: 5px;">
            <img src="${resource(dir:'images/galleryPhotos',file:'3.jpg')}" width="200" />
        </div>
        <div id="popImage4" style="float: left; margin: 5px;">
            <img src="${resource(dir:'images/galleryPhotos',file:'4.jpg')}" width="200" />
        </div>
    </p>

    <p style="clear: both;">
        Thanks for using Sharevent!
    </p>

    <p>
        <g:link controller="gallery" action="createFree"><img src="${resource(dir:'images',file:'FreeGallery.jpg')}" alt="Create a free gallery" /></g:link>
        <g:link controller="gallery" action="createPremium"><img src="${resource(dir:'images',file:'PremiumGallery.jpg')}" alt="Create a premium gallery" /></g:link>
    </p>

    <div id="AdDiv" style="width: 501px; margin-left: auto; margin-right: auto;">
        <p style="color: blue;">Advertisement</p>
        <g:link controller="ads" action="view"><img src="${resource(dir:"images",file:"MindConnect.jpg")}" /></a></g:link>
    </div>
  </body>
</html>
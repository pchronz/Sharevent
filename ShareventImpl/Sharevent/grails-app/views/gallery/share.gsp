<%--
  User: peterandreaschronz
  Date: 21.05.11
  Time: 11:32
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
    <div id="shareViewport">
    <h2>Thanks, ${galleryInstance.creatorFirstName}!</h2>
    <p>
        Your gallery "${galleryInstance.title}" has been created for you!
    </p>

    <div id="sharePublicLinkDiv">
        <p>
            Participation Link
            <br />
            <g:link controller="gallery" action="view" id="${galleryInstance.id}" title="This link brings you to your Sharevent. With it you can upload, download and share your photo gallery. Do not forget to send this link to your friends!"><g:createLink controller="gallery" action="view" id="${galleryInstance.id}" /></g:link>
        </p>
    </div>

    <div id="shareAdminLinkDiv">
        <p>
            Administration Link
            <br />
            <a href="${createLink(controller: 'gallery', action: 'view', id: galleryInstance.id) + "?key=" + galleryInstance.adminKey}" title="Here you can remove individual photos or even delete the whole gallery, you can also block further uploads to it!"><g:createLink controller="gallery" action="view" id="${galleryInstance.id}" />?key=${galleryInstance.adminKey}</a>
        </p>
    </div>

    </div>
  </body>
</html>

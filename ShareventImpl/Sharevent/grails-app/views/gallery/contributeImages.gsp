<%--
  Created by IntelliJ IDEA.
  User: peterandreaschronz
  Date: 08.06.11
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <g:javascript src="jquery-1.6.js"></g:javascript>
    <g:javascript>
        $(document).ready(function() {
            // TODO add code for creating new file input boxes as needed
        });
    </g:javascript>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Sharevent: Easy Photo Sharing</title>
  </head>
  <body>
    <div style="width: 850px; margin-left: auto; margin-right: auto;">
        <h2>Contribute to gallery: ${galleryInstance.title}</h2>
        <p>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <br />
            ${galleryInstance.location + ", " + galleryInstance.date.format("dd.MM.yyyy")}
            <br />
            Creator: <a href="mailto:${galleryInstance.creatorEmail}">${galleryInstance.creatorFirstName + " " + galleryInstance.creatorLastName}</a>
        </p>

        <g:form controller="gallery" id="${galleryInstance.id}" method="post" enctype="multipart/form-data">

            First name
            <br/>
            <g:textField name="firstName" />
            <br/>

            Last name
            <br/>
            <g:textField name="lastName" />
            <br/>

            e-mail
            <br/>
            <g:textField name="email" />
            <br/>

            <input type="file" name="image-file-1" />
            <g:actionSubmit name="UploadImage" value="UploadImage" action="uploadImage" />
        </g:form>

        <div id="AdDiv" style="width: 501px; margin-left: auto; margin-right: auto;">
            <p style="color: blue;">Advertisement</p>
            <g:link controller="ads" action="view"><img src="${resource(dir:"images",file:"MindConnect.jpg")}" /></a></g:link>
        </div>
    </div>
  </body>
</html>
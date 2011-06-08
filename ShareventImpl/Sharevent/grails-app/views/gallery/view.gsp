<%--
  Created by IntelliJ IDEA.
  User: peterandreaschronz
  Date: 21.05.11
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <g:javascript src="jquery-1.6.js"></g:javascript>
    <g:javascript>
        $(document).ready(function() {
            $(".hide").click(function(eventObject){
                $(this).parent().parent().siblings().toggle();
                var text = $(this).text();
                if(text == "Hide")
                    $(this).text("Show");
                else
                    $(this).text("Hide");
            });
        });
    </g:javascript>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Sharevent: Easy Photo Sharing</title>
  </head>
  <body>
    <div style="width: 850px; margin-left: auto; margin-right: auto;">
        <h2>${galleryInstance.title}</h2>
        <p>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:link controller="gallery" action="share"><img src="${resource(dir:'images',file:'Share.jpg')}" /></g:link>
            <br />
            ${galleryInstance.location + ", " + galleryInstance.date.format("dd.MM.yyyy")}
            <br />
            Creator: <a href="mailto:${galleryInstance.creatorEmail}">${galleryInstance.creatorFirstName + " " + galleryInstance.creatorLastName}</a>
        </p>

        <g:form controller="gallery" id="${galleryInstance.id}">

        <g:each var="user" in="${galleryInstance?.contributors}">
            <div id="user_${user.id}">
                <p><a href="mailto:${user.email}">${user.firstName} ${user.lastName}</a> <a href="#markall">all</a> <a href="#marknone">none</a></p>
                <div style="border-style: solid;">

                <div style="border-bottom-style: dotted; clear: both; margin: 10px;">
                    <p>
                        <a class="hide" href="#">Hide</a>
                    </p>
                </div>
                <g:each var="image" in="${user.imageSet?.images}" >
                    <div id="user_${user.id}_photo_${image.id}" style="float: left; margin: 10px;">
                        <!-- TODO determined path depending on gallery and user id -->
                        <img src="${createLink(controller: 'image', action: 'viewImage', params: [id: image.id])}" width="250px"/>
                        <br />
                        <g:checkBox name="image_${image.id}" value="${true}" /> Select me!
                    </div>
                </g:each>
                <div id="HidePhotos${user.id}" style="border-top-style: dotted; clear: both; margin: 10px;">
                    <p>
                        <a class="hide" href="#">Hide</a>
                    </p>
                </div>
        </div>


            </div>

        </g:each>

        <p>
            <g:link controller="gallery" action="contributeImages" id="${galleryInstance.id}"><img src="${resource(dir:'images',file:'UploadPhotos.jpg')}" alt="Upload Photos" /></g:link>

            <!-- Addding a logout-link if logged in as admin might be a good idea -->
            <g:actionSubmit name="Download" value="Download" action="download" />
            <g:if test="${session.isAdmin && session.galleryId == galleryInstance.id}">
                <!-- TODO issue warning before deleting some images or even whole gallery -->
                <g:actionSubmit name="RemoveSelection" value="Delete selection" action="deleteImages" />
                <g:actionSubmit name="DeleteGallery" value="Delete gallery" action="deleteGallery" />
            </g:if>
        </p>

        </g:form>

        <div id="AdDiv" style="width: 501px; margin-left: auto; margin-right: auto;">
            <p style="color: blue;">Advertisement</p>
            <g:link controller="ads" action="view"><img src="${resource(dir:"images",file:"MindConnect.jpg")}" /></a></g:link>
        </div>
    </div>
  </body>
</html>
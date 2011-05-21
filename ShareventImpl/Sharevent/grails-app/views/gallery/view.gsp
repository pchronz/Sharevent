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
        <h2>MyGalleryTitle</h2>
        <p>
            <g:link controller="gallery" action="share"><img src="${resource(dir:'images',file:'Share.jpg')}" /></g:link>
            <br />
            Dortmund, May 2011
            <br />
            Creator: <a href="mailto:maike@flooblemail.com">Maike Ka</a>
        </p>

        <div id="UserGallery1">
            <p>Peter C. <a href="#markall">all</a> <a href="#marknone">none</a></p>
            <div style="border-style: solid;">
                <div style="border-bottom-style: dotted; clear: both; margin: 10px;">
                    <p>
                        <a class="hide" href="#">Hide</a>
                    </p>
                </div>
                <div id="UserPhoto1_1" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'1.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto1Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_2" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'2.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto2Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_3" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'3.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto3Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_4" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'4.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto4Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_5" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'5.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto5Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_6" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'6.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto6Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_7" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'7.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto7Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_8" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'8.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto8Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_9" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'9.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto9Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_10" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'10.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto10Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_11" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'11.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto11Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_12" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'12.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto12Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_13" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'13.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto13Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_14" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'14.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto14Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_15" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'15.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto15Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_16" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'16.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto16Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_17" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'17.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto17Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_18" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'18.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto18Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_19" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'19.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto19Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_20" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'20.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto20Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_21" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'21.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto21Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_22" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'22.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto22Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_23" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'23.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto23Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_24" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'24.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto24Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto1_25" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'25.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto25Checked" value="Selected" /> Select me!
                </div>
                <div id="HidePhotos1" style="border-top-style: dotted; clear: both; margin: 10px;">
                    <p>
                        <a class="hide" href="#">Hide</a>
                    </p>
                </div>
            </div>
        </div>

        <div id="UserGallery2">
            <p>Maike Ka <a href="#markall">all</a> <a href="#marknone">none</a></p>
            <div style="border-style: solid;">
                <div style="border-bottom-style: dotted; clear: both; margin: 10px;">
                    <p>
                        <a class="hide" href="#">Hide</a>
                    </p>
                </div>
                <div id="UserPhoto2_1" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'.1jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto1Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_2" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'2.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto2Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_3" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'3.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto3Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_4" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'4.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto4Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_5" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'5.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto5Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_6" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'6.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto6Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_7" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'7.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto7Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_8" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'8.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto8Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_9" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'9.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto9Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_10" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'10.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto10Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_11" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'11.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto11Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_12" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'12.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto12Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_13" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'13.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto13Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_14" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'14.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto14Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_15" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'15.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto15Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_16" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'16.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto16Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_17" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'17.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto17Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_18" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'18.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto18Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_19" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'19.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto19Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_20" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'20.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto20Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_21" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'21.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto21Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_22" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'22.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto22Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_23" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'23.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto23Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_24" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'24.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto24Checked" value="Selected" /> Select me!
                </div>
                <div id="UserPhoto2_25" style="float: left; margin: 10px;">
                    <img src="${resource(dir:'images/galleryPhotos',file:'25.jpg')}" width="250px"/>
                    <br />
                    <input type="checkbox" name="userPhoto25Checked" value="Selected" /> Select me!
                </div>
                <div id="HidePhotos" style="border-top-style: dotted; clear: both; margin: 10px;">
                    <p>
                        <a class="hide" href="#">Hide</a>
                    </p>
                </div>
            </div>
        </div>

        <p>
            <g:link controller="gallery" action="download"><img src="${resource(dir:"images",file:"DownloadPhotos.jpg")}" alt="Download Photos" /></g:link>
            <a href="#upload"><img src="${resource(dir:'images',file:'UploadPhotos.jpg')}" alt="Upload Photos" /></a>
        </p>

        <div id="AdDiv" style="width: 501px; margin-left: auto; margin-right: auto;">
            <p style="color: blue;">Advertisement</p>
            <g:link controller="ads" action="view"><img src="${resource(dir:"images",file:"MindConnect.jpg")}" /></a></g:link>
        </div>
    </div>
  </body>
</html>
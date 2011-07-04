<%--
  Peter A. Chronz
  Sun Jun 19 19:32:20 CEST 2011
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <g:javascript library="prototype" />
        <g:javascript src="gallery-view.js" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="userDef.companyName" args="${[]}" />: <g:message code="userDef.companySlogan" args="${[]}" /></title>
    </head>
    <body>
    <div id="viewGalleryViewport">
        <h2><g:message code="userDef.exampleGallery.title" args="${[]}" /></h2>
        <p>
            
	    
	    
	    <span class="spanBold"><g:message code="userDef.participationLink" args="${[]}" /></span><a href="/Sharevent/gallery/viewExample">/Sharevent/gallery/viewExample</a>
	    <br />
	    

            <br />
            <g:message code="userDef.exampleGallery.location" args="${[]}" />,<g:message code="userDef.exampleGallery.date" args="${[]}" /> 
            <br />
            <span class="spanBold"><g:message code="userDef.creator" args="${[]}" /></span>: <a href="mailto:${message(code:'userDef.exampleGallery.creatorEmail')}"><g:message code="userDef.exampleGallery.creator" args="${[]}" /></a>
        </p>

        <form action="/Sharevent/gallery/index/4028820a30a8a7110130a909856f001b" method="post" >

        
	<!-- only show the users contribution if it is not empty -->
	
            <div>
                <p><a href="mailto:${message(code:'userDef.exampleGallery.creatorEmail')}"><g:message code="userDef.exampleGallery.creator" args="${[]}" /></a> <a class="selectAll" href="#"><g:message code="userDef.all" args="${[]}" /></a> <a class="selectNone" href="#"><g:message code="userDef.none" args="${[]}" /></a></p>
                <div id="userBoxView" >

                <div class="galleryHidePhotosDiv">
                    <p>
                        <a class="hide" href="#"><g:message code="userDef.hide" args="${[]}" /></a>
                    </p>
                </div>
                
                    <div class="galleryImageDiv" >
                        <img src="${resource(dir: 'images/exampleGallery', file: '1.jpg')}" width="250px"/>
                        <br />
                        <input type="hidden" name="_image_92" /><input type="checkbox" name="image_92" checked="checked" class="selectBox"  /><g:message code="userDef.selectMe" args="${[]}" /> 
                    </div>
                
                    <div class="galleryImageDiv" >
                        <img src="${resource(dir: 'images/exampleGallery', file: '2.jpg')}" width="250px"/>
                        <br />
                        <input type="hidden" name="_image_88" /><input type="checkbox" name="image_88" checked="checked" class="selectBox"  /><g:message code="userDef.selectMe" args="${[]}" /> 
                    </div>
                
                    <div class="galleryImageDiv" >
                        <img src="${resource(dir: 'images/exampleGallery', file: '2.jpg')}" width="250px"/>
                        <br />
                        <input type="hidden" name="_image_90" /><input type="checkbox" name="image_90" checked="checked" class="selectBox"  /><g:message code="userDef.selectMe" args="${[]}" /> 
                    </div>
                
                    <div class="galleryImageDiv" >
                        <img src="${resource(dir: 'images/exampleGallery', file: '3.jpg')}" width="250px"/>
                        <br />
                        <input type="hidden" name="_image_89" /><input type="checkbox" name="image_89" checked="checked" class="selectBox"   /> <g:message code="userDef.selectMe" args="${[]}" /> 
                    </div>
                
                    <div class="galleryImageDiv" >
                        <img src="${resource(dir: 'images/exampleGallery', file: '4.jpg')}" width="250px"/>
                        <br />
                        <input type="hidden" name="_image_91" /><input type="checkbox" name="image_91" checked="checked" class="selectBox"   /> <g:message code="userDef.selectMe" args="${[]}" /> 
                    </div>
                
                <div class="galleryHidePhotosDiv">
                    <p>
                        <a class="hide" href="#">Hide</a>
                    </p>
                </div>
        </div>


        </div>

	
        
	<!-- only show the users contribution if it is not empty -->
	
            <div id="">
                <p><a href="mailto:${message(code: 'userDef.exampleGallery.contributor2.email')}"><g:message code="userDef.exampleGallery.contributor2.name" args="${[]}" /></a> <a class="selectAll" href="#"><g:message code="userDef.all" args="${[]}" /></a> <a class="selectNone" href="#"><g:message code="userDef.none" args="${[]}" /></a></p>
                <div id="userBoxView" >

                <div class="galleryHidePhotosDiv">
                    <p>
                        <a class="hide" href="#"><g:message code="userDef.hide" args="${[]}" /></a>
                    </p>
                </div>
                
                    <div class="galleryImageDiv" >
                        <img src="${resource(dir: 'images/exampleGallery', file: '5.jpg')}" width="250px"/>
                        <br />
                        <input type="hidden" name="_image_96" /><input type="checkbox" name="image_96" checked="checked" class="selectBox"   /> <g:message code="userDef.selectMe" args="${[]}" /> 
                    </div>
                
                    <div class="galleryImageDiv" >
                        <img src="${resource(dir: 'images/exampleGallery', file: '6.jpg')}" width="250px"/>
                        <br />
                        <input type="hidden" name="_image_93" /><input type="checkbox" name="image_93" checked="checked" class="selectBox"   /> <g:message code="userDef.selectMe" args="${[]}" /> 
                    </div>
                
                    <div class="galleryImageDiv" >
                        <img src="${resource(dir: 'images/exampleGallery', file: '7.jpg')}" width="250px"/>
                        <br />
                        <input type="hidden" name="_image_97" /><input type="checkbox" name="image_97" checked="checked" class="selectBox"   /> <g:message code="userDef.selectMe" args="${[]}" /> 
                    </div>
                
                    <div class="galleryImageDiv" >
                        <img src="${resource(dir: 'images/exampleGallery', file: '8.jpg')}" width="250px"/>
                        <br />
                        <input type="hidden" name="_image_95" /><input type="checkbox" name="image_95" checked="checked" class="selectBox"   /> <g:message code="userDef.selectMe" args="${[]}" /> 
                    </div>
                
                    <div class="galleryImageDiv" >
                        <img src="${resource(dir: 'images/exampleGallery', file: '9.jpg')}" width="250px"/>
                        <br />
                        <input type="hidden" name="_image_94" /><input type="checkbox" name="image_94" checked="checked" class="selectBox"   /> <g:message code="userDef.selectMe" args="${[]}" /> 
                    </div>
                
                <div class="galleryHidePhotosDiv">
                    <p>
                        <a class="hide" href="#"><g:message code="userDef.hide" args="${[]}" /></a>
                    </p>
                </div>
        </div>


        </div>

        </form>

    </div>
  
    </body>
</html>

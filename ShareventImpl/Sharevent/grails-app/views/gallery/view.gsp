<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
	<uploader:head css="${resource(dir: 'css', file:'main.css')}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>
    	<g:message code="userDef.companyName" />: <g:message code="userDef.companySlogan" />
    </title>
    <r:require modules="foundation, jquery, gallery-view"/>
  </head>
  <body>
    <div id="viewGalleryViewport">

        <div class="row">
    		<div class="fife columns">
    			<h1>${galleryInstance.title}</h1>
    		</div>
        </div>        		

        <div class="row">

    		<div class="three columns">
    			<span class="spanBold">
		    		<g:message code="userDef.participationLink" args="${[]}" />
		    	</span>
    		</div>

    		<div class="fife columns">
    			<g:link controller="gallery" 
				    action="view" 
				    id="${galleryInstance.id}" >		
				    ${createLink(controller: 'gallery', action: 'view', id:galleryInstance.id)}
		    	</g:link>
    		</div>

        </div>

        <g:if test="${isAdmin}">
	        <div class="row">

	    		<div class="three columns">
	    			<span class="spanBold">
			    		<g:message code="userDef.administrationLink" args="${[]}" />
			    	</span>
	    		</div>

	    		<div class="fife columns">
					<g:link controller="gallery" 
							action="view" 
							id="${galleryInstance.id}" 
							params="${[key: galleryInstance.creatorId]}" >
							${createLink(controller: 'gallery', action: 'view', id: galleryInstance.id, params: [key: galleryInstance.creatorId])}
					</g:link>
	    		</div>

	        </div>
        </g:if>


        <g:form controller="gallery" id="${galleryInstance.id}">

        	<ul class="block-grid mobile one-up">
        		<li>
        			<f:actionSubmit
						class="large blue nice button radius"
						name="Download" 
						value="${message(code: 'userDef.downloadImages')}" 
						action="download" />
        		</li>
        	</ul>
		

			<!-- only show the users contribution if it is not empty -->
			<g:each var="user" in="${galleryInstance.users}">
				<div id="user_${user.id}" class="userBoxView">
					

					<div class="selectAll">
						<g:checkBox name="selectAll" /><a href="#">Select All</a>
					</div>


					<g:if test="${urls.size() > 0}">
						<ul class="block-grid mobile four-up">
							<g:each var="imageUrl" in="${urls}" >
						        <li>
							          <a href="${urlsFull[imageUrl.key]}">
							            <img 	src="${imageUrl.value}" 
							            		id="img_${imageUrl.key}"/>
							          </a>
							          <g:checkBox class="selectBox" 
						          				  name="image_${imageUrl.key}" 
						          				  value="${true}" />
							          <g:message code="userDef.selectMe" 
							          			 args="${[]}" /> 
						        </li>
							</g:each>
						</ul>
					</g:if>
					<g:else>
						<p id='emptyGalleryWarningDiv'>
							<g:message code="view.gallery.view.emptyGallery" />
						</p>
					</g:else>


					<div class="selectAll">
						<g:checkBox name="selectAll" /><a href="#">Select All</a>
					</div>
					

				</div>
			</g:each>

			<div class="row">
        		<div class="fife columns">
        			<f:actionSubmit
	        			class="large blue nice button radius"
						name="Download" 
						value="${message(code: 'userDef.downloadImages')}" 
						action="download" />

	        		<g:if test="${isAdmin}">

	        			<f:actionSubmit 
								class="large red nice button radius"
								name="removeImages"
								value="${message(code: 'userDef.deleteSelection')}"  
								action="deleteImages" />


	        			<f:actionSubmit 
								class="large black nice button radius"
								name="removeGallery" 
								value="${message(code: 'userDef.deleteGallery')}" 
								action="deleteGallery"  />

	        			<g:hiddenField name="key" value="${galleryInstance.creatorId}" />
		        	</g:if>
		        </div>
        	</div>
			
        </g:form>


			<!-- upload div -->
			<div id="upload_div">
				<uploader:uploader id="${galleryInstance.id}" url="${[controller: 'gallery', action: 'uploadImage', id: galleryInstance.id]}" multiple="true" sizeLimit="5000000">
					<uploader:onSubmit>
						ongoingUploads += 1;
						$('#divContributeSpinner').show();
						console.log('On submit done.');
					</uploader:onSubmit>
					<uploader:onComplete>
						var attrs = "";
						var url = "";
						if(responseJSON.success == true) {
							url = responseJSON.imageURL;
							attrs = "src='" + url + "' width='250px'";
						}
						else {
							url = ${resource(dir: 'images', file: 'error.png')};
							attrs = "src='" + url + "alt='Something went wrong while uploading your image.'";
						}
						
						// TODO add select box
						// TODO unique ids!
						var imgDiv = "<div id='all_images' class='galleryImageDiv'><img " + attrs + "></img></div>";
						$('#galleryHidePhotosDivBottom').before(imgDiv);

						$('#emptyGalleryWarningDiv').hide();

						ongoingUploads -= 1;
						if (ongoingUploads == 0) {
							$('#divContributeSpinner').hide();
							$('#divPostUpload').show();
						}
						console.log("On complete done.");
					</uploader:onComplete>
				</uploader:uploader>
			</div>
    </div>
  </body>
</html>

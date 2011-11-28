<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <g:javascript library="prototype" />
    <title>
      <g:message code="userDef.companyName" args="${[]}" />: 
      <g:message code="userDef.companySlogan" args="${[]}" /> 
    </title>
    <style type="text/css">
      .galHeader {
        width:80%;
        margin-left:auto;
        margin-right:auto;
        color:303030;
        font-family:sans-serif;
        font-size:large;
        padding-bottom:12px;
      }
      .galContent {
        width:80%;
        color:303030;
        margin-left:auto;
        margin-right:auto;
        font-family:sans-serif;
      }
      .galContent div div {
        border:1px solid gray;
        padding-left:10px;
      }
      .galContent .gLabel {
        border-top-left-radius: 10px 5px;
        border-top-right-radius: 10px 5px;
        padding-bottom:2px;
      }
      .galContent .gLabel img {
        margin-bottom:-8px;
      }
      .galContent .gLabel a {
        font-size:medium;
        border-bottom:0px;
        text-decoration:none;
        color:303030;
      }
      .galContent .gBody {
        border:1px solid gray;
        border-top:0px;
        border-bottom:0px;
      }
      .galCount .gBody img {
        padding:10px;
      }
      .galContent .gBy {
        text-align:right;
        padding-right:10px;
        padding-bottom:4px;
      }
      .galContent .gBy img {
        margin-bottom:-8px;
      }

    </style>
  </head>
  <body>

    <div class="galHeader">
        Hello ${galUser.firstName}, you own ${ownGalleriesCount} galleries
      <g:if test="${galUser.galleries.size() - ownGalleriesCount > 0}">
        and you are involved in addional ${galUser.galleries.size()-ownGalleriesCount}.
      </g:if>
      <g:else>
      .
      </g:else>
    </div>

    <div class="galContent">
      <g:each in="${galUser.galleries}" var="gallery" status="galCount">
        <div>

          <div class="gLabel">
            <table border="0">
              <tr>
                <td>
                    <g:remoteLink action="loadGalleryImages" update="gI_${gallery.id}" id="${gallery.id}">
                      <img  src="${resource(dir:'images', file:'arrow-round-down.png')}" />
                      Gallery ${gallery.title}  &#160;&#160;
                      <g:formatDate date="${gallery.date}" type="datetime" style="MEDIUM"/> &#160;&#160;
                      ${gallery.location}
                    </g:remoteLink>
                </td>
                <td align="right" style="vertical-align:bottom">
                    <span >${gallery.images.size()} pics</span>
                </td>
              </tr>
            </table>
          </div>
          
          <div class="gBody" id="gI_${gallery?.id}">
            <g:render template="gImages" />
          </div>
          
          <div class="gBy">
            <g:if test="${galUser.id == gallery.creatorId}">
              <a href="mailto:${galUser.email}">owned by ${galUser.firstName} ${galUser.lastName}</a>
            </g:if>
            <g:else>
              <sv:galleryOwner id="${gallery.creatorId}" />
            </g:else>
            <g:remoteLink action="loadGalleryImages" update="gI_${gallery.id}" id="${gallery.id}" >
              <img src="${resource(dir:'images', file:'arrow-round-down.png')}"/>
            </g:remoteLink>
          </div>

        </div>
        <br />
      </g:each>
    </div>
</body>
</html>

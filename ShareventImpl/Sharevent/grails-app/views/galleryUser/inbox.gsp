<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>
      <g:message code="userDef.companyName" args="${[]}" />: <g:message code="userDef.companySlogan" args="${[]}" /></title>
  </head>
  <body>
    <div id="inbox">
      Hello ${galUser.email}, ${galUser.galleries.size()}
      <br /><br />
      <g:each in="${galUser.galleries}" var="gallery" status="galCount">
        Gallery ${gallery.title}: loopCount ${galCount} <br/>
        <g:each in="${gallery.images}" var="image" status="imgCount">
          <img src="<g:createLink controller='image' action='viewImageThumbnail' id='${image.id}'/>" />
          ${imgCount},
        </g:each>
        <br />
      </g:each>
		
    </div>
</body>
</html>

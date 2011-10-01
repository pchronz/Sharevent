<%--
  User: peterandreaschronz
  Date: 21.05.11
  Time: 10:18
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title><g:message code="userDef.companyName" args="${[]}" />: <g:message code="userDef.companySlogan" args="${[]}" /></title>
  </head>
  <body>
    <div id="mainViewport">

		<g:form controller="gallery" action="createNew">
			<label for="gallery_title_input" style="display: block;">Title</label>
			<input type="text" id="gallery_title_input" name="gallery_title" style="width: 70%;" /> 
			<input type="submit" value="Create"  style="width: 20%;"/>
		</g:form>
		<g:link controller="gallery" action="viewExample"><g:message code="userDef.viewExampleGallery" args="${[]}" /></g:link>
    </div>
</body>
</html>

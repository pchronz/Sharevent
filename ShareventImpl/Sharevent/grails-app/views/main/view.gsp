<%--
  User: peterandreaschronz
  Date: 21.05.11
  Time: 10:18
  To change this template use File | Settings | File Templates.
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

		<ol id="shareventSteps">
			<li>
			<g:message code="userDef.firstStep" args="${[]}" />
			</li>
			<li>
			<g:message code="userDef.secondStep" args="${[]}" />
			</li>
			<li>
			<g:message code="userDef.thirdStep" args="${[]}" />
			</li>
		</ol>

        <p>
			<div id="divMainFooterLinks">
				<g:link controller="gallery" action="createFree" class="buttons"><g:message code="userDef.createFreeGallery" args="${[]}" /></g:link>
				|
				<g:link controller="gallery" action="viewExample" class="buttons"><g:message code="userDef.viewExampleGallery" args="${[]}" /></g:link>
			</div>
        </p>
    </div>
</body>
</html>

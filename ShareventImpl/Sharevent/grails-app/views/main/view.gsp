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
    <title>
      <g:message code="userDef.companyName" args="${[]}" />: 
      <g:message code="userDef.companySlogan" args="${[]}" />
    </title>
    <r:require modules="foundation"/>
  </head>
  <body>
    <div id="mainViewport" class="container">

		<g:form controller="gallery" action="createNew">
      <div class="row">
        <div class="eight columns">
          <input class="large nice input-text" placeholder="Geben sie hier den Ihrer Galerie ein" type="text" id="gallery_title_input" name="gallery_title" />
        </div>
         <div class="two columns">
          <input class="btn primary" class="large blue nice button radius" type="submit" value="${message(code:'view.main.view.create')}" />
         </div>
      </div>
		</g:form>
    </div>
</body>
</html>

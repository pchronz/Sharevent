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
    <r:require modules="bootstrap,jquery"/>
  </head>
  <body>
    <div id="mainViewport" class="container">
  		<g:form controller="gallery" action="createNew">
        <div class="row">
          <div class="span12">
            <input class="span12" placeholder="Geben sie hier den Ihrer Galerie ein" type="text" id="gallery_title_input" name="gallery_title" />
          </div>
           <div class="span4">
            <input class="btn primary" type="submit" value="${message(code:'view.main.view.create')}" />
           </div>
        </div>
  		</g:form>
    </div>
</body>
</html>

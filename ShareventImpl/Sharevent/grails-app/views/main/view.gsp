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
  </head>
  <body>
	<div class="row">
		<div class="span8">
			<g:if test="${flash.error}">
				<div class="alert alert-error">
					 <a class="close" data-dismiss="alert">×</a>
					${flash.error}
				</div>
			</g:if>
		</div>
		<div class="span8">
			<g:if test="${flash.message}">
				<div class="alert alert-info">
					 <a class="close" data-dismiss="alert">×</a>
					${flash.message}
				</div>
			</g:if>
		</div>
	</div>
	
	<g:form controller="gallery" action="createNew">
		<div class="row">
			<div class="span8">
				<input class="span8" placeholder="Geben sie hier den Namen Ihrer Galerie ein" type="text" id="gallery_title_input" name="gallery_title" />
			</div>
			<div class="span2">
				<input class="btn-primary span2" type="submit" value="${message(code:'view.main.view.create')}" />
			</div>
		</div>
	</g:form>

</body>
</html>

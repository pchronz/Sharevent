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
	  <div style="position: absolute; top: 20%;">
		<div class="row">
			<div class="span8 offset1">
				<g:link controller="main" action="view" style="text-decoration: none;">
					<span style="font-family: 'Sonsie One', cursive; font-size: 96px; text-decoration: none; margin-left: 20px">SharEvent</span>
				</g:link>
			</div>
		</div>
	<div class="row">
		<div class="span6">
			<g:if test="${flash.error}">
				<div class="alert alert-error">
					 <a class="close" data-dismiss="alert">×</a>
					${flash.error}
				</div>
			</g:if>
		</div>
		<div class="span6">
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
			<div class="span8 offset1">
				<input class="span8" placeholder="Please enter the gallery title..." type="text" id="gallery_title_input" name="gallery_title" style="height: 50px; font-size: 25px; margin: 15px;"/>
			</div>
			<div class="span2">
				<input class="btn-primary span2" type="submit" value="${message(code:'view.main.view.create')}" style="height: 55px; margin: 15px;" />
			</div>
		</div>
	</g:form>

	<div class="row">
		<div class="span12 offset1" style="margin-left: 150px">
			<%-- Twitter --%>
			<a href="https://twitter.com/share" class="twitter-share-button" data-related="jasoncosta" data-lang="en" data-size="small" data-count="none">Tweet</a>
			<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>

			<%-- Google plus one --%>
			<div class="g-plusone" data-size="medium" data-annotation="none"></div>
			<script type="text/javascript">
			  (function() {
				var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
				po.src = 'https://apis.google.com/js/plusone.js';
				var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
			  })();
			</script>
		
			<%-- Facebook like --%>
			<script>(function(d, s, id) {
			  var js, fjs = d.getElementsByTagName(s)[0];
			  if (d.getElementById(id)) return;
			  js = d.createElement(s); js.id = id;
			  js.src = "//connect.facebook.net/en_GB/all.js#xfbml=1";
			  fjs.parentNode.insertBefore(js, fjs);
			}(document, 'script', 'facebook-jssdk'));</script>
			<div class="fb-like" style="display: inline; position: relative; top: -2px;" data-send="false" data-layout="button_count" data-width="450" data-show-faces="false"></div>
			<div id="fb-root"></div>
		</div>
	</div>

</div>
</body>
</html>

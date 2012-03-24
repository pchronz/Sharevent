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
					<span style="font-family: 'Sonsie One', cursive; font-size: 96px; text-decoration: none; margin-left: 20px; color: #FFFFFF;text-shadow: 2px 2px #000077;">SharEvent</span>
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
				<input class="btn span2" type="submit" value="${message(code:'view.main.view.create')}" style="height: 55px; margin: 15px;" />
			</div>
		</div>
	</g:form>

	<div class="row">
		<div class="span12 offset1" style="margin-left: 150px">
			<%-- Twitter --%>
			<a href="https://twitter.com/share" class="twitter-share-button" data-related="jasoncosta" data-lang="en" data-size="small" data-count="none" data-url="http://www.sharevent.com">Tweet</a>
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
			<div class="fb-like" style="display: inline; position: relative; top: -3px;" data-send="false" data-layout="button_count" data-width="450" data-show-faces="false"></div>
			<div id="fb-root"></div>
		</div>
	</div>


</div>

	<script type="text/javascript" charset="utf-8">
		$(function()
		{
			if(document.createElement('canvas') && navigator && !(navigator.userAgent.match(/iPhone/i) || navigator.userAgent.match(/iPod/i) || navigator.userAgent.match(/iPad/i)))
			{
				function Bit(size, color){
					this.size = size;
					if (size < 20)
					{
						this.speed = 1.5;
					}
					else if(size < 30)
					{
						this.speed = 1;
					}
					else
					{
						this.speed = 0.5;
					}
					this.color = color;
					// horizontal position is at random on the canvas
					this.xpos = Math.random() * screen.availWidth - this.size;
					// vertical position is linear combination of 100, linear function of horizontal position, and sine that depends on horizontal position
					//this.ypos = 100 + 500 * (canvas.width-this.xpos)/canvas.width + 100 * Math.sin(this.offset + this.xpos / 500);
					this.ypos = 100 + 100 * Math.sin(this.offset + this.xpos / 500);
					this.offset = new Number(100 * Math.random());
				}

				function tick(bit)
				{
					if(bit.xpos > maxX)
					{
						bit.xpos = -bit.size;
						bit.offset = new Number(100 * Math.random());
					}
					else
					{
						bit.xpos += bit.speed;
					}
					var scrollX = window.pageXOffset;
					var scrollY = window.pageYOffset;
					//bit.ypos = scrollY + 100 + 500 * (canvas.width-bit.xpos)/canvas.width + 100 * Math.sin(bit.offset + bit.xpos / 500);
					bit.ypos = scrollY + 500 - Math.exp(7.4 * (1-(canvas.width-bit.xpos)/canvas.width)) + 100 * Math.sin(bit.offset + bit.xpos / 500);
					context.shadowColor = bit.color;
					context.fillStyle = bit.color;

					context.beginPath();  
					context.arc(bit.xpos,bit.ypos,bit.size,0,Math.PI*2,true); // Outer circle  
					context.fill();
					
				}

				function reDraw()
				{
					var scrollX = window.pageXOffset;
					var scrollY = window.pageYOffset;
					context.clearRect(scrollX,scrollY,canvas.width, canvas.height);
					bits.map(tick);
				}

				document.body.style['background-image'] = 'url("${resource(dir:'images', file:'blue_bg.jpg')}")';
				document.body.style['backgroundImage'] = 'url("${resource(dir:'images', file:'blue_bg.jpg')}")';
				document.body.style['background-attachment'] = 'fixed';

				// object colors, chosen at random
				var colours = ['#CFFFFF', '#3366ff', '#0099ff', '#0074CC'];

				// preparing the canvas
				maxX = screen.availWidth + 40;
				var canvas = document.getElementById('background');
				canvas.width = screen.availWidth;
				console.log(canvas.width)
				canvas.height = 800;
				var context = canvas.getContext('2d');
				context.globalAlpha = 0.7;
				context.shadowBlur = 7;

				// creating the objects
				var numObjects = 40;
				var bits = new Array(numObjects);
				var bit;
				for(var i = 0; i < numObjects; ++i)
				{
					var minSize = 10;
					var maxDelta = 30;
					// get a random size and a random from our list
					bit = new Bit(minSize + maxDelta * Math.random(), colours[Math.floor(colours.length * Math.random())]);
					bits[i] = bit;
				}

				setInterval(reDraw, 50);
			}
		});
	</script>

</body>
</html>

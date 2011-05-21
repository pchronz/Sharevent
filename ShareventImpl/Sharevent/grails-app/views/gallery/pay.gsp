<%--
  Created by IntelliJ IDEA.
  User: peterandreaschronz
  Date: 21.05.11
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Sharevent: Easy Photo Sharing</title>
  </head>
  <body>
  <div style="width: 500px; margin-left: auto; margin-right: auto;">
      <h2>Start a premium gallery</h2>

      <h3>Bill</h3>
      <p>
          1 premium gallery with 150 images (up to 500 MB in total) included.
          <br />
          Guaranteed uptime: 30 days.
          <br />
          Price 5€ incl. VAT
          <br />
      </p>

      <h3>Payment Options</h3>
      <p>
          <g:link controller="gallery" action="share"><img src="${resource(dir:'images',file:'googlecheckout.jpg')}" width="150" /> </g:link>
          <br />
          <g:link controller="gallery" action="share"><img src="${resource(dir:'images',file:'paypal.png')}" width="150" /></g:link>
      </p>
      </div>
  </body>
</html>
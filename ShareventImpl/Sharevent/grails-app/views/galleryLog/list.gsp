
<%@ page import="com.sharevent.GalleryLog" %>
<%@ page import="com.sharevent.Gallery" %>
<%@ page import="com.sharevent.Image" %>
<%@ page import="com.sharevent.ClickLogEntry" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'galleryLog.label', default: 'GalleryLog')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>Gallery Statistics</h1>
			<div>
				<table>
					<tr>
						<td>Gallery Count:</td>
						<td>${Gallery.list().size()}</td>
					</tr>
					<tr>
						<td>Gallery Click Count:</td>
						<td>${ClickLogEntry.list().size()}</td>
					</tr>
					<tr>
						<td>Image Count:</td>
						<td>${Image.list().size()}</td>
					</tr>
				</table>
			</div>
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'galleryLog.id.label', default: 'Id')}" />
                        
                            <th><g:message code="galleryLog.gallery.label" default="Gallery" /></th>
                            <th>Gallery Title</th>
                            <th>Click Count</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${galleryLogInstanceList}" status="i" var="galleryLogInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${galleryLogInstance.id}">${fieldValue(bean: galleryLogInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: galleryLogInstance, field: "gallery")}</td>
                            <td>${galleryLogInstance.gallery.title}</td>
                            <td>${galleryLogInstance.clickLogEntries.size()}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${galleryLogInstanceTotal}" />
            </div>
        </div>
    </body>
</html>


<%@ page import="com.sharevent.Gallery" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'gallery.label', default: 'Gallery')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'gallery.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="date" title="${message(code: 'gallery.date.label', default: 'Date')}" />
                        
                            <g:sortableColumn property="title" title="${message(code: 'gallery.title.label', default: 'Title')}" />
                        
                            <g:sortableColumn property="location" title="${message(code: 'gallery.location.label', default: 'Location')}" />
                        
                            <g:sortableColumn property="creatorFirstName" title="${message(code: 'gallery.creatorFirstName.label', default: 'Creator First Name')}" />
                        
                            <g:sortableColumn property="creatorLastName" title="${message(code: 'gallery.creatorLastName.label', default: 'Creator Last Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${galleryInstanceList}" status="i" var="galleryInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${galleryInstance.id}">${fieldValue(bean: galleryInstance, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${galleryInstance.date}" /></td>
                        
                            <td>${fieldValue(bean: galleryInstance, field: "title")}</td>
                        
                            <td>${fieldValue(bean: galleryInstance, field: "location")}</td>
                        
                            <td>${fieldValue(bean: galleryInstance, field: "creatorFirstName")}</td>
                        
                            <td>${fieldValue(bean: galleryInstance, field: "creatorLastName")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${galleryInstanceTotal}" />
            </div>
        </div>
    </body>
</html>



<%@ page import="com.sharevent.Gallery" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'gallery.label', default: 'Gallery')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${galleryInstance}">
            <div class="errors">
                <g:renderErrors bean="${galleryInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${galleryInstance?.id}" />
                <g:hiddenField name="version" value="${galleryInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="date"><g:message code="gallery.date.label" default="Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: galleryInstance, field: 'date', 'errors')}">
                                    <g:datePicker name="date" precision="day" value="${galleryInstance?.date}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="title"><g:message code="gallery.title.label" default="Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: galleryInstance, field: 'title', 'errors')}">
                                    <g:textField name="title" value="${galleryInstance?.title}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="location"><g:message code="gallery.location.label" default="Location" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: galleryInstance, field: 'location', 'errors')}">
                                    <g:textField name="location" value="${galleryInstance?.location}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="creatorFirstName"><g:message code="gallery.creatorFirstName.label" default="Creator First Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: galleryInstance, field: 'creatorFirstName', 'errors')}">
                                    <g:textField name="creatorFirstName" value="${galleryInstance?.creatorFirstName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="creatorLastName"><g:message code="gallery.creatorLastName.label" default="Creator Last Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: galleryInstance, field: 'creatorLastName', 'errors')}">
                                    <g:textField name="creatorLastName" value="${galleryInstance?.creatorLastName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="creatorEmail"><g:message code="gallery.creatorEmail.label" default="Creator Email" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: galleryInstance, field: 'creatorEmail', 'errors')}">
                                    <g:textField name="creatorEmail" value="${galleryInstance?.creatorEmail}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="contributors"><g:message code="gallery.contributors.label" default="Contributors" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: galleryInstance, field: 'contributors', 'errors')}">
                                    
<ul>
<g:each in="${galleryInstance?.contributors?}" var="c">
    <li><g:link controller="galleryUser" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="galleryUser" action="create" params="['gallery.id': galleryInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'galleryUser.label', default: 'GalleryUser')])}</g:link>

                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>

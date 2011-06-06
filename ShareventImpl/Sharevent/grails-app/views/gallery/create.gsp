

<%@ page import="com.sharevent.Gallery" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'gallery.label', default: 'Gallery')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${galleryInstance}">
            <div class="errors">
                <g:renderErrors bean="${galleryInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
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
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>

<g:each in="${gImages}" var="image" status="count">
    <img style="padding:4px" src="<g:createLink controller='image' action='viewImageThumbnail' id='${image.id}'/>" />
</g:each>
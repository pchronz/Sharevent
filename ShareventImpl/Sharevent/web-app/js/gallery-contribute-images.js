function userCreated(e) {
	alert('user created');
}
		
function userNotCreated(e) {
	alert('user not created');
}

document.observe("dom:loaded", function() {
	// initially hide the upload form if the user is unknown
	if(!isLoggedIn) {
		$('divImageUpload').hide();
	}
	else {
		$('userCreationDiv').hide();
	}
	Ajax.Responders.register({
		onComplete: function(e){
	  		// TODO check whether the request has been successful or not
	 		$('divImageUpload').show();
			$('userCreationDiv').hide();
		}
	});
});


// global var for counting the ongoing uploads
var ongoingUploads

document.observe("dom:loaded", function() {
	ongoingUploads = 0;
	$$('#divContributeSpinner').each(function(s) {
		s.hide();
	});
	$$('#divPostUpload').each(function(s) {
		s.hide();
	});

	$$('#aUploadMoreImages').each(function(s){
		s.observe('click', function(event) {
			$$('#divImageUpload').each(function(s){
				s.show();
			});
		});
	});
});


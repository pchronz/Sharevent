document.observe("dom:loaded", function() {
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


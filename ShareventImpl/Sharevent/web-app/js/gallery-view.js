var ongoingUploads = 0;
$(function() {
	$('input[type="checkbox"]').attr('checked', 'checked');
	$('.hide').click(function() {
			$('#all_images').toggle();
			var text = $(this).html();
			// TODO i18n
			if(text == "Hide") {
				$('.hide').html("Show");
			}
			else {
				$('.hide').html("Hide");
			}
	});
	
	$('.selectAll').click(function() {
		var thisChecked = $(this).children('input[type="checkbox"]').is(':checked');
		if(!thisChecked) {
			$('.selectAll').children('input[type="checkbox"]').prop('checked', false);
			$('.selectBox[type="checkBox"]').prop('checked', false);
		}
		else {
			$('.selectAll').children('input[type="checkbox"]').prop('checked', true);
			$('.selectBox[type="checkBox"]').prop('checked', true);
		}
	});

});


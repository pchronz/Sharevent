var ongoingUploads = 0;
$(function() {
	$('.hide').click(function() {
			Event.element(event).up(1).siblings().each(function(s) {
				s.toggle();
			});
			var text = Event.element(event).innerHTML;
			if(text == "Hide")
				Event.element(event).update("Show");
			else
				Event.element(event).update("Hide");
	});
	
	$('.selectAll').click(function() {
		Event.element(e).up(1).descendants('.selectBox').each(function(e) {
			e.checked = true;
		});
	});
	
	$('.selectNone').click(function() {
		Event.element(e).up(1).descendants('.selectBox').each(function(e) {
			e.checked = false;
		});
	});
	
});


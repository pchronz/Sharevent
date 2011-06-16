document.observe("dom:loaded", function() {
	$$('.hide').each(function(s) {
		s.observe('click', function(event) {
			Event.element(event).up(1).siblings().each(function(s) {
				s.toggle();
			});
			var text = Event.element(event).innerHTML;
			if(text == "Hide")
				Event.element(event).update("Show");
			else
				Event.element(event).update("Hide");
		});
	});
	
	$$('.selectAll').each(function(e) {
		e.observe('click', function(e) {
			Event.element(e).up(1).descendants('.selectBox').each(function(e) {
				e.checked = true;
			});
		});
	});
	
	$$('.selectNone').each(function(e) {
		e.observe('click', function(e) {
			Event.element(e).up(1).descendants('.selectBox').each(function(e) {
				e.checked = false;
			});
		});
	});
	
});


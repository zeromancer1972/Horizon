var source = "json_events.xsp";
if(location.href.indexOf("calendarRooms")!=-1)
	source= "json_rooms.xsp";
if(location.href.indexOf("calendarResources")!=-1)
	source= "json_resources.xsp";

$(document).ready(
		function() {

			"use strict";
			
			var today = new Date();

			var options = {
				modal : "#events-modal",
				first_day : 1,
				events_source : source,
				view : 'month',
				tmpl_path : 'calendar/tmpls/',

				day : today.format("yyyy-mm-dd"),
				onAfterEventsLoad : function(events) {
					if (!events) {
						return;
					}
					var list = $('#eventlist');
					list.html('');

					$.each(events, function(key, val) {
						$(document.createElement('li')).html(
								'<a href="' + val.url + '">' + val.title
										+ '</a>').appendTo(list);
					});
				},
				onAfterViewLoad : function(view) {
					$('.calheader').text(this.getTitle());
					$('.btn-group button').removeClass('active');
					$('button[data-calendar-view="' + view + '"]').addClass(
							'active');
				},
				classes : {
					months : {
						general : 'label'
					}
				}
			};

			var calendar = $('#widgetCalendar').calendar(options);

			$('.btn-group button[data-calendar-nav]').each( function() {
				var $this = $(this);
				$this.click( function() {
					calendar.navigate($this.data('calendar-nav'));
				});
			});

			$('.btn-group button[data-calendar-view]').each( function() {
				var $this = $(this);
				$this.click( function() {
					calendar.view($this.data('calendar-view'));
				});
			});

			$('#first_day').change( function() {
				var value = $(this).val();
				value = value.length ? parseInt(value) : null;
				calendar.setOptions( {
					first_day : value
				});
				calendar.view();
			});

			$('#language').change( function() {
				calendar.setLanguage($(this).val());
				calendar.view();
			});

			$('#events-modal .modal-header, #events-modal .modal-footer')
					.click( function(e) {
						// e.preventDefault();
							// e.stopPropagation();
						});
			$(".calendar button").click( function(e) {
				// prevent the xpage from being refreshed as these are buttons, not links
				e.preventDefault();
				e.stopPropagation();
			});
		})
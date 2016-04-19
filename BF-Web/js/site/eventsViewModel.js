function EventsViewModel() {
	var self = this;

	self.eventUrl = "http://localhost:8080/event";
	self.imgUrl = "http://ec2-52-38-43-166.us-west-2.compute.amazonaws.com/"
	self.eventList = ko.observableArray();

	self.test;

	self.getEvents = function() {
		$.ajax({
			type: "GET",
			url: self.eventUrl,
			dataType: "json",
			success: function(data) 
			{
				console.log("Got data!");
				console.log(data);

				if(data.status === 0) {
					for(var key in data.eventMap) {
						data.eventMap[key].forEach(function(event) {
							event.tag = event.name.replace(/\s+/g, '').replace(/[^a-zA-Z ]/g, "").toLowerCase() + event.day;
							event.breweryNum = key;
							self.eventList.push(event);
						});
					}

					self.eventList.sort(function(a, b) {
						var dateA = Date.parse(a.startDate);
						var dateB = Date.parse(b.startDate);
						return (dateA < dateB) ? -1 : (dateA > dateB) ? 1 : 0;
					});

					console.log(self.eventList());

				} else {
					console.log("Something went wrong...");
					console.log(data.description);
				}
			},
			error: function(err)
			{
				console.log("Didn't get data...");
				console.log(err);
			}
		});
	}();
}
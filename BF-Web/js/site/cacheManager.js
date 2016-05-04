function fetchBreweries(brewList, brewGroups) {

	$.ajax({
		type: "GET",
		url: resources.breweryEnd,
		dataType: "json",
		success: function(data) 
		{
			console.log("Got data!");
			console.log(data);

			if(data.status === 0) {
				data.rObj.forEach(function(entry) 
				{
					console.log("Pushing " + entry.b_name);
					brewList.splice(entry.b_brewNum, 0, entry);
				});

				brewList.sort(function(a, b) {
					var nameA = a.b_name.toUpperCase();
					if(nameA.startsWith("THE ")) {
						nameA = nameA.substring(4, nameA.length);
					}

					var nameB = b.b_name.toUpperCase();
					if(nameB.startsWith("THE ")) {
						nameB = nameB.substring(4, nameB.length);
					}
					return (nameA < nameB) ? -1 : (nameA > nameB) ? 1 : 0;
				});

				if(brewGroups != null) {
					var current = [];
					var count = 0;
					brewList().forEach(function(brewery) {
					
						if(((count % 6) == 0) && (count != 0))  {
							brewGroups.push(current);
							current = [];
						}

						current.push(brewery);

						if(count === (brewList().length - 1)) {
							brewGroups.push(current);
						}

						count++;
					})

					console.log(brewGroups());
				}
				

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
}

function makeDateTimeString(str) {
	var d = new Date(str);
	var hrDate = d.toLocaleDateString();
	var time = d.toLocaleTimeString();
	if(time.length > 10) {
		var hrTime = time.substring(0, 5) + time.substring(9, 11);
	} else {
		var hrTime = time.substring(0, 4) + time.substring(8, 10);
	}

	return hrDate + " at " + hrTime;
}

function fetchEvents(eventList) {

	$.ajax({
		type: "GET",
		url: resources.eventEnd,
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
						event.startString = makeDateTimeString(event.startDate);
						event.endString = makeDateTimeString(event.endDate);
						if(event.famFriendly != null && event.famFriendly) {
							event.famString = "Family friendly";
							event.famColor = "green";
						} else {
							event.famString = "NOT family friendly";
							event.famColor = "red";
						}

						if(event.petFriendly != null && event.petFriendly) {
							event.petString = "Pet friendly";
							event.petColor = "green";
						} else {
							event.petString= "NOT pet friendly";
							event.petColor = "red";
						}

						if(event.outdoor != null && event.outdoor) {
							event.outdoorString = "Outdoors";
							event.outdoorColor = "green";
						} else {
							event.outdoorString = "Indoors";
							event.outdoorColor = "red";
						}

						if(event.ticketCost == null) {
							event.priceString = "TBD";
						} else {
							if(event.ticketCost === 0) {
								event.priceString = "Free";
							} else {
								event.priceString = event.ticketCost + " DOLLARS" ;
							}
						}

						eventList.push(event);
					});
				}

				eventList.sort(function(a, b) {
					var dateA = Date.parse(a.startDate);
					var dateB = Date.parse(b.startDate);
					return (dateA < dateB) ? -1 : (dateA > dateB) ? 1 : 0;
				});

				document.getElementById("eventLoadingBar").remove();
				document.getElementById("titleBox").insertAdjacentHTML('beforeend', '<div class="col-md-12 text-center"><hr><h2>Brewery Events</h2><hr></div>');

				console.log(eventList());

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
}

function fetchDrinks(brewNum, allDrinks, curDrinks) {
	$.ajax({
		type: "GET",
		url: resources.drinkEnd + "/" + brewNum,
		dataType: "json",
		success: function(data) 
		{
			console.log("Got data!");
			console.log(data);

			var brewDrinks = [];
			if(data.status === 0) {
				data.rObj[0].u_drinkList.forEach(function(entry)
				{
					console.log("Pushing " + entry.d_name);
					entry["tag"] = entry.d_name.replace(/\s+/g, '').replace(/[^a-zA-Z ]/g, "").toLowerCase();
					if(entry.d_description === null || entry.d_description === "") {
						entry.d_description = "No description available";
					}
					brewDrinks.push(entry);
				});

				brewDrinks.sort(function(a, b) {
					var nameA = a.d_name.toUpperCase();
					var nameB = b.d_name.toUpperCase();
					return (nameA < nameB) ? -1 : (nameA > nameB) ? 1 : 0;
				});

				brewDrinks.forEach(function(entry) {
					curDrinks.push(entry);
				})

				allDrinks.splice(brewNum, 0, brewDrinks);

				console.log(allDrinks);

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
}


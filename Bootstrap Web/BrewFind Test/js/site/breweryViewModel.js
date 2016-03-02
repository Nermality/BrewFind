
function BreweryViewModel() {
	var self = this;

	self.breweries = ko.observableArray();

	self.populateBrewery = function(brewery) {
		console.log("Populating " + brewery.b_name );
		
		document.getElementById("bAddr1").innerHTML = brewery.b_addr1;

		if(brewery.b_addr2 == null) {
			document.getElementById("bAddr2").visibility = "hidden";
		} else {
			document.getElementById("bAddr2").visibility = "visible";
			document.getElementById("bAddr2").innerHTML = brewery.b_addr2;
		}

		var csz = brewery.b_city + ", " + brewery.b_state + " " + brewery.b_zip;
		document.getElementById("bCSZ").innerHTML = csz;
		document.getElementById("bPhone").innerHTML = brewery.b_phone;
		document.getElementById("bEmail").innerHTML = brewery.b_email;

		document.getElementById("bLink").href = brewery.b_url;

		document.getElementById("brewTitle").innerHTML = brewery.b_name;
	}

	self.makePin = function(brewery) {
		console.log("Making a pin for" + brewery.b_name);
		if(marker != null){ marker.setMap(null); }
		
		var brewLoc = new google.maps.LatLng(brewery.b_lat, brewery.b_long);
		marker = new google.maps.Marker(
		{
			position: brewLoc,
			title: brewery.b_name
		});

		marker.setMap(map);
	}

	self.getBreweries = function() {
		var apiUrl = "http://localhost:8080/brewery";

		$.ajax({
			type: "GET",
			url: apiUrl,
			dataType: "json",
			success: function(data) 
			{
				console.log("Got data!");
				console.log(data);

				data.rObj.forEach(function(entry) 
				{
					console.log("Pushing " + entry.b_name);
					self.breweries.push(entry);
				});

				self.breweries.sort(function(a, b) {
					var nameA = a.b_name.toUpperCase();
					var nameB = b.b_name.toUpperCase();
					return (nameA < nameB) ? -1 : (nameA > nameB) ? 1 : 0;
				});
			},
			error: function(err)
			{
				console.log("Didn't get data...");
				console.log(err);
			}
		});
	}();
}
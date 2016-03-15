
function BreweryViewModel() {
	var self = this;

	self.apiUrl = "http://52.35.37.107:8080";
	self.breweryEnd = self.apiUrl + "/brewery";
	self.eventEnd = self.apiUrl + "/event";
	self.userEnd = self.apiUrl + "/user";
	self.userAuthEnd = self.apiUrl + "/user/auth";

	self.breweries = ko.observableArray();

	self.populateBrewery = function(brewery) {
		console.log("Populating " + brewery.b_name );
		
		document.getElementById("brewTitle").innerHTML = brewery.b_name;
		document.getElementById("bDesc").innerHTML = brewery.b_description;
		document.getElementById("bAddr1").innerHTML = brewery.b_addr1;

		var logo = document.getElementById("brewLogo");
		logo.src = "img/breweries/" + brewery.b_breweryNum + "/brewery_profile_pic.jpg";
		logo.onerror = function() {
			logo.src = "img/breweries/" + brewery.b_breweryNum + "/brewery_profile_pic.png";
		}

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

		document.getElementById("bLink").href = brewery.b_url ? brewery.b_url : "#";

		document.getElementById("brewTitle").innerHTML = brewery.b_name;
	}

	
	self.makePin = function(brewery) {
		console.log("Making a pin for " + brewery.b_name);
		if(marker != null){ marker.setMap(null); }
		
		var contentString = '<div id="content">'+
      '<div id="siteNotice">'+
      '</div>'+
      '<h1 id="firstHeading" class="firstHeading">'+brewery.b_name+'</h1>'+
      '<div id="bodyContent">'+
      '<p>'+brewery.b_phone+'</p>'+
	  '<p>'+brewery.b_email+'</p>'+
	  '<p>'+brewery.b_addr1+'</p>'+
      '</div>'+
      '</div>';

		
		var infowindow = new google.maps.InfoWindow({
    content: contentString
		});
		
		var brewLoc = new google.maps.LatLng(brewery.b_lat, brewery.b_long);
		marker = new google.maps.Marker(
		{
			position: brewLoc,
			title: brewery.b_name
		});

		marker.addListener('click', function() {
    infowindow.open(map, marker);
  });
		
		marker.setMap(map);	
		}

	self.updateBrewery = function(updateForm) {

	}

	self.createNewBrewery = function(breweryForm) {
		//var newBrewery = JSON.stringify($(breweryForm).serializeArray());
		console.log(breweryForm);
		var newQuery = {};
		var token = {};
		var newBrews = [];
		var toAdd = {};

		token["access"] = 3;
		token["token"] = "abcdefg";
		token["stamp"] = 123983223;

		toAdd["b_name"] = breweryForm.name.value;
		toAdd["b_addr1"] = breweryForm.addr1.value;
		toAdd["b_addr2"] = breweryForm.addr2.value;
		toAdd["b_city"] = breweryForm.city.value;
		toAdd["b_state"] = breweryForm.state.value;
		toAdd["b_zip"] = breweryForm.zip.value;
		toAdd["b_phone"] = breweryForm.phone.value;
		toAdd["b_email"] = breweryForm.email.value;
		toAdd["b_url"] = breweryForm.url.value;

		toAdd["b_hasTour"] = breweryForm.tours.value;
		toAdd["b_hasFood"] = breweryForm.food.value;
		//toAdd["b_hasTap"] = breweryForm.tap.value;
		//toAdd["b_hasGrowler"] = breweryForm.growler.value;
		toAdd["b_rating"] = breweryForm.rating.value;

		newBrews.push(toAdd);
		newQuery["list"] = newBrews;
		newQuery["token"]= token;

		// GET BOOLEANS AND RATING

	    // var data = {};
	    // var a = $(brewer).serializeArray();
	    // $.each(a, function() {
	    //     if (data[$(brewer).name] !== undefined) {
	    //         if (!data[$(brewer).name].push) {
	    //             data[$(brewer).name] = [data[$(brewer).name]];
	    //         }
	    //         data[$(brewer).name].push($(brewer).value || '1');
	    //     } else {
	    //         data[$(brewer).name] = $(brewer).value || '1';
	    //     }
	    // });

	    console.log(newQuery);
	
		$.ajax({
			type: "PUT",
			url: apiUrl,
			data: toAdd,
			contentType: "json",
			success: function(newBrewery) 
			{
				console.log("Got response! (new brewery)");
				console.log(newBrewery);

				if(newBrwery.status === 0) {
					rawBrews = newBrewery.rObj;
					rawBrews.forEach(function(entry) {
						console.log("Pushing " + entry.b_name);
						self.breweries.splice(entry.b_brewNum, 0, entry);
					});
				} else {
					console.log("Something went wrong...");
					console.log(newBrewery.description);
				}
				
			},
			error: function(err)
			{
				console.log("Didn't get data...");
				console.log(err);
			}
		});
	}

	self.populateGodbrewForm = function(brewery) {
		document.getElementById("in_addr1").value = brewery.b_addr1;
		document.getElementById("in_addr2").value = brewery.b_addr2;
		document.getElementById("in_name").value = brewery.b_name;
		document.getElementById("in_city").value = brewery.b_city;
		document.getElementById("in_state").value = brewery.b_state;
		document.getElementById("in_zip").value = brewery.b_zip;
		document.getElementById("in_phone").value = brewery.b_phone;	
		document.getElementById("in_email").value = brewery.b_email;	
		document.getElementById("in_url").value = brewery.b_url;		
	}

	self.getBreweries = function() {

		$.ajax({
			type: "GET",
			url: self.breweryEnd,
			dataType: "json",
			success: function(data) 
			{
				console.log("Got data!");
				console.log(data);

				if(data.status === 0) {
					data.rObj.forEach(function(entry) 
					{
						console.log("Pushing " + entry.b_name);
						self.breweries.splice(entry.b_brewNum, 0, entry);
					});

					console.log(self.breweries());

					self.breweries.sort(function(a, b) {
						var nameA = a.b_name.toUpperCase();
						var nameB = b.b_name.toUpperCase();
						return (nameA < nameB) ? -1 : (nameA > nameB) ? 1 : 0;
					});
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

function BreweryViewModel() {
	var self = this;

	self.apiUrl = "http://localhost:8080";
	self.breweryEnd = self.apiUrl + "/brewery";
	self.eventEnd = self.apiUrl + "/event";
	self.userEnd = self.apiUrl + "/user";
	self.userAuthEnd = self.apiUrl + "/user/auth";

	self.testToken = {};
	self.testToken["token"] = "YmZhZG1pbg==";
	self.testToken["access"] = 3;
	self.testToken["stamp"] = 1458311957462;

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
	  '<p>'+brewery.b_url+'</p>'+
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

	self.createCORSRequest = function(method, url) {
	  var xhr = new XMLHttpRequest();
	  if ("withCredentials" in xhr) {
	    // XHR for Chrome/Firefox/Opera/Safari.
	    xhr.open(method, url, true);
	  } else if (typeof XDomainRequest != "undefined") {
	    // XDomainRequest for IE.
	    xhr = new XDomainRequest();
	    xhr.open(method, url);
	  } else {
	    // CORS not supported.
	    xhr = null;
	  }
	  return xhr;
	}

	self.makeBreweryQuery = function(form) {
		var newQuery = {};
		var newBrews = [];
		var toAdd = {};

		toAdd["b_name"] = form.name.value;

		toAdd["b_addr2"] = form.addr2.value;
		toAdd["b_city"] = form.city.value;
		toAdd["b_state"] = form.state.value;
		toAdd["b_email"] = form.email.value;
		toAdd["b_url"] = form.url.value;

		toAdd["b_hasTour"] = form.tours.value;
		toAdd["b_hasFood"] = form.food.value;
		toAdd["b_hasTap"] = form.tap.value;
		toAdd["b_hasGrowler"] = form.growler.value;

		// Can't add values that aren't strings - API doesn't like empty strings for int/doubles		
		if(form.zip.value != "") {
			toAdd["b_name"] = form.name.value;
		}

		if(form.phone.value != "") {
			toAdd["b_phone"] = form.phone.value;
		}

		if(form.rating.value != "") {
			toAdd["b_rating"] = form.rating.value;
		}
		
		newBrews.push(toAdd);
		newQuery["list"] = newBrews;
		newQuery["token"]= self.testToken;

		return newQuery;
	}

	self.createNewBrewery = function(breweryForm) {

		console.log(breweryForm);

		var newQuery = self.makeBreweryQuery(breweryForm);
	    console.log(newQuery);

	    var xhr = self.createCORSRequest('PUT', self.breweryEnd);

	    xhr.onload = function() {
			console.log("Got response!");
			var response = xhr.responseText;
			var newBrewery = JSON.parse(response);
			console.log(newBrewery);

			if(newBrewery.status != 0) {
				console.log("Something went wrong...");
				console.log(newBrewery.description);
			} else {
				rawBrews = newBrewery.rObj;
				rawBrews.forEach(function(entry) {
					console.log("Pushing " + entry.b_name);
					self.breweries.splice(entry.b_brewNum, 0, entry);
				});
			}
	    }

	    xhr.onerror = function() {
	    	console.log('XHR failure');
	    }

	    var string = JSON.stringify(newQuery);
	    console.log(string);
	    xhr.send(string);

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
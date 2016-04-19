
function BreweryViewModel() {
	var self = this;

	self.apiUrl = "http://localhost:8080";
	self.imgUrl = "http://ec2-52-38-43-166.us-west-2.compute.amazonaws.com/"
	self.breweryEnd = self.apiUrl + "/brewery";
	self.eventEnd = self.apiUrl + "/event";
	self.userEnd = self.apiUrl + "/user";
	self.drinkEnd = self.apiUrl + "/utwrapper";
	self.userAuthEnd = self.apiUrl + "/user/auth";

	self.testToken = {};
	self.testToken["token"] = "YmZhZG1pbg==";
	self.testToken["access"] = 3;
	self.testToken["stamp"] = 1458311957462;

	self.breweries = ko.observableArray();
	self.breweryGroups = ko.observableArray();
	self.drinkList = ko.observableArray();

	self.populateBrewery = function(brewery) {
		console.log("Populating " + brewery.b_name );
		
		document.getElementById("brewTitle").innerHTML = brewery.b_name;
		document.getElementById("bDesc").innerHTML = brewery.b_description;

		var logo = document.getElementById("brewLogo");
		logo.src = "img/breweries/" + brewery.b_breweryNum + "/brewery_profile_pic.jpg";
		logo.onerror = function() {
			logo.src = "img/breweries/" + brewery.b_breweryNum + "/brewery_profile_pic.png";
		}

		var addrHtml = brewery.b_addr1 ? brewery.b_addr1 + "<br/>" : "";

		if(brewery.b_addr2 != null && brewery.b_addr2 != "") {
			addrHtml += brewery.b_addr2 + "<br/>";
		}

		addrHtml += brewery.b_city + ", " + brewery.b_state + " " + brewery.b_zip;
		document.getElementById("bAddr").innerHTML = addrHtml;

		document.getElementById("bPhone").innerHTML = brewery.b_phone ? brewery.b_phone : "No phone number available";
		document.getElementById("bEmail").innerHTML = brewery.b_email ? brewery.b_email : "No email available";

		var url;
		if(brewery.b_url === null || brewery.b_url === "") {
				url = brewery.b_url;
			}
		else if(!brewery.b_url.startsWith("http://")) {
			url = "http://" + brewery.b_url;
		} else {
			url = brewery.b_url;
		}

		document.getElementById("bUrl").href = url ? url : "";
		document.getElementById("bUrl").innerHTML = url ? url : "No website available";
		document.getElementById("brewTitle").innerHTML = brewery.b_name;

		document.getElementById("breweryBox").style = "display: block"; 

		self.drinkList.removeAll();
		self.getDrinksForBrewery(brewery.b_breweryNum);
	}

	self.getDrinksForBrewery = function(brewNum) {

		$.ajax({
			type: "GET",
			url: self.drinkEnd + "/" + brewNum,
			dataType: "json",
			success: function(data) 
			{
				console.log("Got data!");
				console.log(data);

				if(data.status === 0) {
					data.rObj[0].u_drinkList.forEach(function(entry)
					{
						console.log("Pushing " + entry.d_name);
						entry["tag"] = entry.d_name.replace(/\s+/g, '').replace(/[^a-zA-Z ]/g, "").toLowerCase();
						if(entry.d_description === null || entry.d_description === "") {
							entry.d_description = "No description available";
						}
						self.drinkList.push(entry);
					});

					self.drinkList.sort(function(a, b) {
						var nameA = a.d_name.toUpperCase();
						var nameB = b.d_name.toUpperCase();
						return (nameA < nameB) ? -1 : (nameA > nameB) ? 1 : 0;
					});

					console.log(self.drinkList());

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

	self.updateBrewery = function(breweryForm) {

		var newQuery = self.makeBreweryUpdateQuery(breweryForm);
		if(newQuery === null) {
			console.log("Something went wrong generating update query - no action taken.");
			return;
		}
		console.log(newQuery);

		var xhr = self.createCORSRequest('POST', self.breweryEnd);

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
					self.breweries[entry.b_breweryNum] = entry;
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

	self.makeBreweryUpdateQuery = function(form) {
		var brewNum = form.brewNum.value;
		var brewery = self.breweries()[brewNum - 1];
		if(brewery == null) {
			console.log("Something went wrong, unable to find brewery with brewNum of " + brewNum);
			return null;
		}

		var modified = false;

		if(brewery["b_addr1"] != form.addr1.value.trim()) {
			modified = true;
			brewery["b_addr1"] = form.addr1.value.trim();
		}
		if(brewery["b_addr2"] != form.addr2.value.trim()) {
			modified = true;
			brewery["b_addr2"] = form.addr2.value.trim();
		}
		if(brewery["b_city"] != form.city.value.trim()) {
			modified = true;
			brewery["b_city"] = form.city.value.trim();
		}
		if(brewery["b_email"] != form.email.value.trim()) {
			modified = true;
			brewery["b_email"] = form.email.value.trim();
		}
		if(brewery["b_url"] != form.url.value.trim()) {
			modified = true;
			brewery["b_url"] = form.url.value.trim();
		}
		if(brewery["b_hasTour"] != form.tours.value.trim()) {
			modified = true;
			brewery["b_hasTour"] = form.tours.value.trim();
		}
		if(brewery["b_hasFood"] != form.food.value.trim()) {
			modified = true;
			brewery["b_hasFood"] = form.food.value.trim();
		}
		if(brewery["b_hasTap"] != form.tap.value.trim()) {
			modified = true;
			brewery["b_hasTap"] = form.tap.value.trim();
		}
		if(brewery["b_hasGrowler"] != form.growler.value.trim()) {
			modified = true;
			brewery["b_hasGrowler"] = form.growler.value.trim();
		}

		if(brewery["b_zip"] != form.zip.value.trim() && form.zip.value != "") {
			modified = true;
			brewery["b_zip"] = form.zip.value.trim();
		}
		if(brewery["b_phone"] != form.phone.value.trim()) {
			modified = true;
			if(form.phone.value === "") {
				brewery["b_phone"] = null;
			} else {
				brewery["b_phone"] = form.phone.value.trim();
			}			
		}

		var query = {}
		var list = []
		list.push(brewery);
		query["list"] = list;
		query["token"] = self.testToken;

		return query;
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
			toAdd["b_zip"] = form.zip.value;
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

	self.makeUserQuery= function(form) {
		var newQuery = {};
		var newUser = [];
		var toAdd = {};
		
		toAdd["u_uname"] = form.uname.value;
		toAdd["u_pass"] = form.pass.value;
		toAdd["u_firstName"] = form.fname.value;
		toAdd["u_lastName"] = form.lname.value;
		toAdd["u_access"] = form.access.value;
		
		newUser.push(toAdd);
		newQuery["list"] = newUser;
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
	    	console.log('XHR failure - adding brewery');
	    }

	    var string = JSON.stringify(newQuery);
	    console.log(string);
	    xhr.send(string);

	}

	self.addNewEvent = function(form) {
		var eventQuery = self.createEventQuery(form);
		if(eventQuery === null) {
			console.log("Something went wrong creating an event query...");
			return;
		}

		console.log(eventQuery);

		var xhr = self.createCORSRequest("POST", self.eventEnd);

		xhr.onload = function(){
			console.log("Got response!");
			var response = xhr.responseText;
			var newEvent = JSON.parse(response);

			if(newEvent.status != 0) {
				console.log("Something went wrong...");
				console.log(newEvent.description);
			} else {
				eve = newEvent.rObj;
				eve.foreach(function(entry) {
					console.log(entry);
				});
			}
		};

		xhr.onerror = function(){
			console.log("XHR failure - adding event");
		};

		var string = JSON.stringify(eventQuery);
		console.log(string);
		xhr.send(string);
	}

	self.createEventQuery = function(eventForm) {
		var newQuery = {};
		var newEvent = {};
		newEvent["id"] = null;
		newEvent["htmlLink"] = null;
		newEvent["day"] = null;
		newEvent["month"] = null;
		newEvent["year"] = null;

		newEvent["name"] = eventForm.e_name.value;
		newEvent["description"] = eventForm.e_desc.value;
		newEvent["breweryName"] = eventForm.e_host.value;
		
		if(document.getElementById("in_loc_atBrewery").checked) {
			newEvent["atBreweryLocation"] = true;
			var brew;
			self.breweries.foreach(function(b) {
				if(b.b_name === eventForm.e_host.value) {
					brew = b;
				}
			})
			var locString = brew.b_addr1 + ', ' + brew.b_city + ', VT ' + brew.b_zip;
			newEvent["location"] = locString;

		} else {
			newEvent["atBreweryLocation"] = false;
			newEvent["location"] = eventForm.customLocation.value;
		}
		
		newEvent["startDate"] = eventForm.e_startDate.value;
		newEvent["endDate"] = eventForm.e_endDate.value;
		newEvent["isFamFriendly"] = Boolean(eventForm.family.value);
		newEvent["isPetFriendly"] = Boolean(eventForm.pet.value);
		newEvent["isOutdoor"] = Boolean(eventForm.inout.value);
		newEvent["ticketCost"] = parseInt(eventForm.e_ticketCost.value);

		newQuery["token"] = self.testToken;
		newQuery["event"] = newEvent;

		return newQuery;
	};

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
		document.getElementById("in_description").value = brewery.b_description;	
		document.getElementById("in_brewNum").value = brewery.b_breweryNum;
	}
	
	self.populateUser = function(user) {
		document.getElementById("in_uname").value = user.u_uname;
		document.getElementById("in_pass").value = user.u_pass;
		document.getElementById("in_fname").value = user.u_firstName;
		document.getElementById("in_lname").value = user.u_lastName;
	}
	
	self.populateEvent = function(event) {
		document.getElementById("in_version").value = event.e_version;
		document.getElementById("in_ename").value = event.e_name;
		document.getElementById("in_edesc").value = event.e_description;
		document.getElementById("in_host").value = event.e_host;
		document.getElementById("in_eurl").value = event.e_url;
		document.getElementById("in_startDate").value = event.e_startDate;
		document.getElementById("in_startTime").value = event.e_startTime;
		document.getElementById("in_endDate").value = event.e_endDate;
		document.getElementById("in_endTime").value = event.e_endTime;
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

					self.breweries.sort(function(a, b) {
						var nameA = a.b_name.toUpperCase();
						var nameB = b.b_name.toUpperCase();
						return (nameA < nameB) ? -1 : (nameA > nameB) ? 1 : 0;
					});

					var current = [];
					var count = 0;
					self.breweries().forEach(function(brewery) {
						if(((count % 6) == 0) && (count != 0))  {
							self.breweryGroups.push(current);
							current = [];
						}

						current.push(brewery);

						if(count === (self.breweries().length - 1)) {
							self.breweryGroups.push(current);
						}

						count++;
					})

					console.log(self.breweryGroups());

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
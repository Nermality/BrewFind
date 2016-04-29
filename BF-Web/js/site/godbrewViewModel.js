function GodbrewViewModel() {
	var self = this;
	self.breweries = ko.observableArray();

	self.updateBrewery = function(breweryForm) {
		var brewNum = form.brewNum.value;
		var brewery = self.breweries()[brewNum - 1];

		var newQuery = makeBreweryUpdateQuery(breweryForm, brewery);
		if(newQuery === null) {
			console.log("Something went wrong generating update query - no action taken.");
			return;
		}
		console.log(newQuery);

		var xhr = createCORSRequest('POST', resources.breweryEnd);

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

	self.createNewBrewery = function(breweryForm) {
		console.log(breweryForm);

		var newQuery = makeBreweryQuery(breweryForm);
	    console.log(newQuery);

	    var xhr = createCORSRequest('PUT', resources.breweryEnd);

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
		var eventQuery = makeEventQuery(form);
		if(eventQuery === null) {
			console.log("Something went wrong creating an event query...");
			return;
		}

		console.log(eventQuery);
		var xhr = createCORSRequest("POST", resources.eventEnd);

		xhr.onload = function(){
			console.log("Got response!");
			var response = xhr.responseText;
			var newEvent = JSON.parse(response);

			if(newEvent.status != 0) {
				console.log("Something went wrong...");
				console.log(newEvent.description);
				// MAKE AN ALERT
			} else {
				// MAKE AN ALERT
			}
		};

		xhr.onerror = function(){
			console.log("XHR failure - adding event");
		};

		var string = JSON.stringify(eventQuery);
		console.log(string);
		xhr.send(string);
	}

	self.populateBreweryForm = function(brewery) {
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

	self.populateUserForm = function(user) {
		document.getElementById("in_uname").value = user.u_uname;
		document.getElementById("in_pass").value = user.u_pass;
		document.getElementById("in_fname").value = user.u_firstName;
		document.getElementById("in_lname").value = user.u_lastName;
	}

	self.populateEventForm = function(event) {
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

}
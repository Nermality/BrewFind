function GodbrewViewModel() {
	var self = this;
	self.breweries = ko.observableArray();

	self.updateBrewery = function(breweryForm) {
		var brewNum = breweryForm.brewNum.value;
		var brewery = self.breweries()[brewNum - 1];

		var newQuery = makeBreweryUpdateQuery(breweryForm, brewery);
		if(newQuery === null || newQuery == "null") {
			console.log("Something went wrong generating update query - no action taken.");
			document.getElementById("updateAlert").innerHTML = 
				'<div class="alert alert-warning" role="alert"><strong>Hmmm...</strong> Something went wrong processing your update. If this issue persists, contact an admin.</div>';
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
				document.getElementById("updateAlert").innerHTML = 
					'<div class="alert alert-warning" role="alert"><strong>Uh oh!</strong> Something went wrong on our end, if this keeps up contact an admin.</div>';
			} else {
				rawBrews = newBrewery.rObj;
				rawBrews.forEach(function(entry) {
					console.log("Pushing " + entry.b_name);
					self.breweries[entry.b_breweryNum] = entry;
				});
				document.getElementById("updateAlert").innerHTML = 
					'<div class="alert alert-success" role="alert"><strong>Awesome!</strong> Your update has been processed.</div>';
			}
	    }

	    xhr.onerror = function() {
	    	console.log('XHR failure');
	    	document.getElementById("updateAlert").innerHTML = 
	    		'<div class="alert alert-danger" role="alert"><strong>Oh no...</strong> Something serious went wrong, try again in a little while.</div>';
	    }

	    var string = JSON.stringify(newQuery);
	    console.log(string);
	    xhr.send(string);
	}

	self.createNewBrewery = function(breweryForm) {
		console.log(breweryForm);

		var newQuery = makeBreweryQuery(breweryForm);
		if(newQuery === null || newQuery == "null") {
			console.log("Failed to make query")
			document.getElementById("createAlert").innerHTML = 
				'<div class="alert alert-warning" role="alert"><strong>Hmmm...</strong> Something went wrong processing your brewery. If this issue persists, contact an admin.</div>';
			return;
		}
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
				document.getElementById("createAlert").innerHTML = 
					'<div class="alert alert-warning" role="alert"><strong>Uh oh!</strong> Something went wrong on our end, if this keeps up contact an admin.</div>';

			} else {
				rawBrews = newBrewery.rObj;
				rawBrews.forEach(function(entry) {
					console.log("Pushing " + entry.b_name);
					self.breweries.splice(entry.b_brewNum, 0, entry);
				});
				document.getElementById("createAlert").innerHTML = 
					'<div class="alert alert-success" role="alert"><strong>Awesome!</strong> Your new brewery has been processed.</div>';
			}
	    }

	    xhr.onerror = function() {
	    	console.log('XHR failure - adding brewery');
	    	document.getElementById("createAlert").innerHTML = 
	    		'<div class="alert alert-danger" role="alert"><strong>Oh no...</strong> Something serious went wrong, try again in a little while.</div>';
	    }

	    var string = JSON.stringify(newQuery);
	    console.log(string);
	    xhr.send(string);
	}

	self.addNewEvent = function(form) {
		var eventQuery = makeEventQuery(form, self.breweries);
		if(eventQuery === null) {
			console.log("Something went wrong creating an event query...");
			document.getElementById("eventAlert").innerHTML = 
				'<div class="alert alert-warning" role="alert"><strong>Hmmm...</strong> Something went wrong processing your event. If this issue persists, contact an admin.</div>';
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
				console.log(response);
				document.getElementById("eventAlert").innerHTML = 
					'<div class="alert alert-warning" role="alert"><strong>Uh oh!</strong> Something went wrong on our end, if this keeps up contact an admin.</div>';
			} else {
				document.getElementById("eventAlert").innerHTML = 
					'<div class="alert alert-success" role="alert"><strong>Awesome!</strong> Your event has been processed.</div>';
			}
		};

		xhr.onerror = function(){
			console.log("XHR failure - adding event");
			document.getElementById("eventAlert").innerHTML = 
	    		'<div class="alert alert-danger" role="alert"><strong>Oh no...</strong> Something serious went wrong, try again in a little while.</div>';
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

		// Booleans
		if(!brewery.b_hasFood) {
			document.getElementById("in_food_no").click();
		} else {
			document.getElementById("in_food_yes").click();
		}

		if(!brewery.b_hasTour) {
			document.getElementById("in_tours_no").click();
		} else {
			document.getElementById("in_tours_yes").click();
		}

		if(!brewery.b_hasTap) {
			document.getElementById("in_tap_no").click();
		} else {
			document.getElementById("in_tap_yes").click();
		}

		if(!brewery.b_hasGrowler) {
			document.getElementById("in_growler_no").click();
		} else {
			document.getElementById("in_growler_yes").click();
		}
	}

	self.populateUserForm = function(user) {
		document.getElementById("in_uname").value = user.u_uname;
		document.getElementById("in_pass").value = user.u_pass;
		document.getElementById("in_fname").value = user.u_firstName;
		document.getElementById("in_lname").value = user.u_lastName;
	}

}
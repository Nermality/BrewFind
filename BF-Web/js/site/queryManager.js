function createCORSRequest(method, url) {
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

function makeBreweryUpdateQuery(form, brewery) {
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
	query["token"] = resources.testToken;

	return query;
}

function makeBreweryQuery(form) {
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
	newQuery["token"]= resources.testToken;

	return newQuery;
}

function makeUserQuery(form) {
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
	newQuery["token"]= resources.testToken;

	return newQuery;
}

function makeEventQuery(eventForm, breweries) {
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
		breweries.forEach(function(b) {
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

	newQuery["token"] = resources.testToken;
	newQuery["event"] = newEvent;

	return newQuery;
}

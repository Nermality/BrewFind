
function BreweryViewModel() {
	var self = this;

	self.imgUrl = resources.imgUrl;
	self.breweries = ko.observableArray();
	self.breweryGroups = ko.observableArray();
	self.currentDrinks = ko.observableArray();
	self.drinkList = new Array(50);

	self.populateBrewery = function(brewery) {
		console.log("Populating " + brewery.b_name );
		
		document.getElementById("brewTitle").innerHTML = brewery.b_name;
		document.getElementById("bDesc").innerHTML = brewery.b_description;

		var logo = document.getElementById("brewLogo");
		logo.src = resources.imgUrl + "/img/breweries/" + brewery.b_breweryNum + "/brewery_profile_pic.jpg";
		logo.onerror = function() {
			logo.src = resources.imgUrl + "img/breweries/" + brewery.b_breweryNum + "/brewery_profile_pic.png";
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

		self.currentDrinks.removeAll();
		self.getDrinksForBrewery(brewery.b_breweryNum);
	}

	self.getDrinksForBrewery = function(brewNum) {
		if(self.drinkList[brewNum] != null) {
			console.log("Already has drinks...")
			self.drinkList[brewNum].forEach(function(entry) {
				self.currentDrinks.push(entry);
			});
		} else {
			console.log("Fetching drinks...")
			fetchDrinks(brewNum, self.drinkList, self.currentDrinks);
		}
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
}
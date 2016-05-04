function gbLogin() {

	var self = this;

	self.login = function(form) {
		var uname = form.uname.value;
		var pass = form.pass.value;

		var xhr = createCORSRequest('POST', resources.userAuthEnd);
		xhr.setRequestHeader("uname", uname);
		xhr.setRequestHeader("pass", pass);

		xhr.onload = function() {
			console.log("Got response!");
			var response = xhr.responseText;
			var uTok = JSON.parse(response);

			if(uTok.status != 0) {
				console.log("API Error " + uTok.status);
				document.getElementById("loginAlert").innerHTML = 
					'<div class="alert alert-danger text-center" role="alert">Sorry, we could not log you in.</div>';
				return;
			}

			localStorage.setItem("uTok", JSON.stringify(uTok.rObj[0]));
			window.location.replace("godbrew.html");
		}
		
		xhr.onerror = function() {
			console.log("Something went wrong...");
			return;
		}

		xhr.send();
	}	
};

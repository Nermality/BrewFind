function gbLogin() {
	var uname = document.gbLoginForm.uname.value;
	var pass = document.gbLoginForm.pass.value;

	var endpoint = "http://localhost:8080/user/auth";

	var xhr = createCORSRequest('POST', endpoint);

	xhr.upload.addEventListener("progress", updateProgress);
	xhr.upload.addEventListener("load", transferComplete);
	xhr.upload.addEventListener("error", transferFailed);
	xhr.upload.addEventListener("abort", transferCanceled);

	xhr.setRequestHeader("uname", uname);
	xhr.setRequestHeader("pass", pass);

	xhr.send();

	document.gbLoginForm.submit();
};

function updateProgress(e) {
	console.log(e);
};

function transferComplete(e) {
	console.log(e);
};

function transferFailed(e) {
	console.log(e);
};

function transferCanceled(e) {
	console.log(e);
};

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
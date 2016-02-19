<!DOCTYPE html>
<html lang="en">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/knockout/knockout-3.3.0.js"></script>
<script src="http://maps.googleapis.com/maps/api/js?AIzaSyCsIrpOH1R74PPfmCCeIbHZmfSIOzXbTgk"></script>
<script>
	function initialize() 
	{
		var mapProp = 
		{
			center:new google.maps.LatLng(44.4758,-73.2119),
			zoom:11,
			mapTypeId:google.maps.MapTypeId.ROADMAP
		};
		var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
		var marker = new google.maps.Marker(
		{
			position: myLatLng,
				map: map,
		title: 'Hello World!'
		});
	}
	google.maps.event.addDomListener(window, 'load', initialize);
</script>

<head>
<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");
</script>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>BrewFind</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/business-casual.css" rel="stylesheet">

    <!-- Fonts -->
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Josefin+Slab:100,300,400,600,700,100italic,300italic,400italic,600italic,700italic" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	
</head>

<body>
	<div class="container">
		<div class="brand">BrewFind</div>
		<!-- <div class="address-bar">3481 Melrose Place | Beverly Hills, CA 90210 | 123.456.7890</div> -->
		<!-- Navigation -->
		<nav class="navbar navbar-default" role="navigation">
		<div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <!-- navbar-brand is hidden on larger screens, but visible when the menu is collapsed -->
                <a class="navbar-brand" href="index.html">Business Casual</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
                    <li>
                        <a href="index.html">Home</a>
                    </li>
                    <li>
                        <a href="breweries.html">Breweries</a>
                    </li>
                    <li>
                        <a href="events.html">Calender</a>
                    </li>
					<li>
                        <a href="maps.html">Map</a>
                    </li>
					<li>
                        <a href="aboutUs.html">About Us</a>
                    </li>
				</ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
		</nav>
	</div>
		<div class="container">
			<div class="box">
				<div class="col-md-3">
				<strong>Search for Brewery by name:</strong>
				<p><br></p>
					<div class="col-md-2">
						<div class ="breweries" style="height:450px;width:250px;border:1px solid #ccc;font:16px/26px Georgia, Garamond, Serif;overflow:auto;" data-bind="foreach: breweries">
								<!-- this is where i was trying to get the on click function.  This is my site for reference. http://www.w3schools.com/jsref/event_onclick.asp The code is only partialy implemented. -->
								<a data-bind="text: name" ></a>
								<br>
							</div>
					</div>
				</div>
                <div class="col-md-6" id="googleMap" style="width:500px;height:500px;"></div>
        
				<div class="col-md-3" >
				
					<strong id = "mybrewery" >Brewery:</strong>
						<script>
							function makeBrewery(name, email, phone, address)
							alert('The length of the array is ' + myObservableArray().length);
							alert('The first element is ' + myObservableArray()[0]);
						</script>
					<div class ="breweries" style="height:400px;width:250px;border:1px solid #ccc;font:16px/26px Georgia, Garamond, Serif;overflow:auto;" >
					 <p id = "myaddress">Address:</p>
					 <p id = "myphone">Phone#:</p>
					 <p id = "myemail">Email Address:</p>
					   
					
					</div>
                </div>
			</div>	
		</div>
	</div>				

    <footer>
		<nav class="navbar navbar-default" role="navigation">
			<div class="container">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<!-- navbar-brand is hidden on larger screens, but visible when the menu is collapsed -->
					<a class="navbar-brand" href="index.html">Business Casual</a>
				</div>
				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><p><font size="1">Copyright &copy; BrewFind 2015</font></p></li>
						<li><p>&nbsp</p></li>
						<li><p>&nbsp</p></li>
						<li><p>&nbsp</p></li>
						<li><p>&nbsp</p></li>
						<li><p>&nbsp</p></li>
						<li><p>&nbsp</p></li>
						<li><p>&nbsp</p></li>
						<li>
						<li>  
						<form name="login">
						<table>
							<tr><br></tr>
							<tr>
							<td><font size="1">Username</font><input type="text" style="font-size: 8pt" name="userid" width="6" height="2" /></td>
							<td><input type="button" style="font-size: 8pt"  onclick="check(this.form)"  height="3" value="Login"/></td></tr>
							<tr><td><font size="1">Password</font><input type="password" style="font-size: 8pt" name="pswrd" width="6" height="2"/></td>
							<td><input type="reset" style="font-size: 8pt" value="Cancel" height="3" /></td></tr></table>
						</form></li>						
							<script>	
							function makeBrewery(name, email, phone, address) 
							{
								var self = this;
								self.name = name;
								self.email = email;
								self.phone = phone;
								self.address = address;	
							}
							var apiUrl = "http://localhost:8080/brewery";
							var breweries = ko.observableArray();
							var breweriesinfo = ko.observableArray();
							           
								$.ajax({
								type: "GET",
								url: apiUrl,
								dataType: "json",
								success: function(data) 
								{
									console.log("Got data!");
									console.log(data);
									rawBrews = data.rObj;
									rawBrews.forEach(function(entry) 
									{
										console.log("Pushing " + entry.b_name);
										breweries.push(new makeBrewery(entry.b_name, entry.b_email, entry.b_phone, entry.b_addr1 ));
									})
								},
								error: function(err)
								{
									console.log("Didn't get data...");
									console.log(err);
								}
							});
							//ko.applyBindings(breweries);
							//var anotherObservableArray = ko.observableArray([
							//	{ name: "name", type: get },
							//	{ name: "email", type: get },
							//	{ name: "address", type: get },
							//	{ name: "phone", type: get }
							//]);
							//alert('The length of the array is ' + myObservableArray().length);
							//alert('The first element is ' + myObservableArray()[0]);
							ko.applyBindings(breweries);
							
						</script>
						
						
						
						
						<script language="javascript">
							function check(form)/*function to check userid & password*/
							{
								/*the following code checkes whether the entered userid and password are matching*/
								if(form.userid.value == "myuserid" && form.pswrd.value == "mypswrd")
								{
									window.open('brewcenter.html')/*opens the target page while Id & password matches*/
								}
								else
								{
							   alert("Error Password or Username")/*displays error message*/
								}
							}
						</script>
						</li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
        <!-- /.container -->
		</nav>
    </footer>
    <!-- jQuery -->
    <script src="js/jquery.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    <!-- Script to Activate the Carousel -->
    <script>
		$('.carousel').carousel({
			interval: 5000 //changes the speed
		})
    </script>
</body>
</html>
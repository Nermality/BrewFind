<!DOCTYPE html>
<html lang="en">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/knockout/knockout-3.3.0.js"></script>
<script src="http://maps.googleapis.com/maps/api/js?AIzaSyCsIrpOH1R74PPfmCCeIbHZmfSIOzXbTgk"></script>
<script src="js/site/resources.js"></script>
<script src="js/site/cacheManager.js"></script>
<script src="js/site/breweryViewModel.js"></script>

<script>

	var mapProp;
	var map;
	var marker;

	function initialize() 
	{
		mapProp = 
		{
			center:new google.maps.LatLng(43.900234, -72.745325),
			zoom:7,
			scrollwheel: false, 
			mapTypeId:google.maps.MapTypeId.ROADMAP
		};

		map = new google.maps.Map(document.getElementById("googleMap"),mapProp);
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

    <title>BrewFind - Map</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/business-casual.css" rel="stylesheet">

    <!-- Our CSS -->
    <link href="css/site.css" rel="stylesheet">

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
<!-- <input type="button" onclick="myFunction()" value="Show alert box" /> -->

	<?php include 'header.html' ?>
	<div class="container">
		<div class="row">
			<div class="box">
				<div class="col-lg-12 text-center">
				<hr>
					<h2>Click a Brewery to get started!</h2>
				<hr>
				</div>
			</div>
		</div>
	</div>
<div class="container">
	<div class="box clearfix">
		<div class="container">
			<div class="col-md-4">
				<p style="font-size:25px">Breweries</p>
				<div class="breweryList">
					<table>
						<tbody data-bind="foreach: breweries">
							<tr class="brewListItem" data-bind="click: $parent.makePin, text: b_name" />
						</tbody>
					</table>
				</div>
			</div>
			<div class="col-md-8">
				<p style="font-size:25px" id="brewTitle"></p>
				<div id="googleMap" style="width:600px;height:500px;"></div>
			</div>	
		</div>
	</div>	
	<?php include 'footer.html' ?>
</div>	

    
    <script>
    	var bvm = new BreweryViewModel();
		fetchBreweries(bvm.breweries, bvm.breweryGroups);
		ko.applyBindings(bvm);
    </script>
    <!-- jQuery -->
    <script src="js/lib/jquery.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="js/lib/bootstrap.min.js"></script>
    <!-- Script to Activate the Carousel -->
    <script>
		$('.carousel').carousel({
			interval: 5000 //changes the speed
		})
    </script>
</body>
</html>
<!DOCTYPE html>
<html lang="en">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<script src="http://ajax.aspnetcdn.com/ajax/knockout/knockout-3.3.0.js"></script>
	<head>
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
    <?php include 'header.html' ?>
	<!-- Page Content -->
	<div class="container">
			<div class="row">
				<div class="box">
					<div id="error">
						
					</div>
			<div class="col-md-4">
			
				<p style="font-size:25px">Breweries</p>
					<div class="breweryList">
						<table>
							<tbody data-bind="foreach: breweries">
								<tr class="brewListItem" data-bind="click: $parent.populateBrewery, text: b_name"/>
							</tbody>
						</table>
					</div>
			</div>
			<div class="col-md-8">
				<p style="font-size:25px" id="brewTitle">Click a brewery to get started!</p><br/>
				<div class="row">
					<div class="col-md-6">
						<p id="bDesc"></p><br/>
						
						<!-- <a id="bLink">Visit their site!</a><br/>
						<a id="bMapLink">...or visit them in person!</a> -->
					</div>
					<div class="col-md-4">
						<img id="brewLogo" class="brewLogo" src="http://i.imgur.com/CE4r5vR.jpg"></img>
						<p id="bAddr1"></p><br/>
						<p id="bAddr2"></p><br/>
						<p id="bCSZ"></p><br/>
						<p id="bPhone"></p><br/>
						<p id="bEmail"></p><br/>
						<p id="uRating"></p><br/>
						<p id="uNumRating"></p><br/>
						<p id="uDrinks"></p><br/>
						
					</div>
				</div>
			</div>
			</div>
		</div>
	</div>
	<?php include 'footer.html' ?>
	<!-- /.container -->
	<!-- jQuery -->
	<script src="js/lib/jquery.js"></script>
	<!-- Bootstrap Core JavaScript -->
	<script src="js/lib/bootstrap.min.js"></script>
	<script src="js/site/breweryViewModel.js"></script>
	<script>
		ko.applyBindings(new BreweryViewModel());
	</script>
</body>

</html>

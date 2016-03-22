<!DOCTYPE html>
<html lang="en">
	<script src="http://maps.googleapis.com/maps/api/js?AIzaSyCsIrpOH1R74PPfmCCeIbHZmfSIOzXbTgk"></script>
	<script>
		function initialize() {
		  var mapProp = {
			center:new google.maps.LatLng(44.4758,-73.2119),
			zoom:11,
			mapTypeId:google.maps.MapTypeId.ROADMAP
		  };
		  var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
		  
		  var marker = new google.maps.Marker({
			position: myLatLng,
			map: map,
			title: 'Hello World!'
		  });
		}
		google.maps.event.addDomListener(window, 'load', initialize);
	</script>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="description" content="">
		<meta name="author" content="">
		<title>Contact - Business Casual - Start Bootstrap Theme</title>
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
		<?php include 'header.html' ?>
		<div class="container">
			<div class="row">
				<div class="box">
					<div class="col-lg-12">
						<hr>
						<h2 class="intro-text text-center">Contact
							<strong>BrewFind Contact Information</strong>
						</h2>
						<hr>
					</div>
					<div class="col-md-8">
						<div id="googleMap" style="width:500px;height:380px;"></div>
					</div>
					 <div class="col-md-4">
					 <button type="button"><a href="https://twitter.com/search?q=%40brewfindvt&src=typd&lang=en" class="button">Twitter</a></button><br><br>
					 <button type="button"><a href="https://www.facebook.com/BrewFind/?ref=aymt_homepage_panel" class="button">Facebook</a></button><br><br>
					 <button type="button"><a href="http://www.lakako.com/post/1090812875276495802" class="button">Instangram</a></button><br><br>
					 <button type="button"><a href="mailto:brefindvt@gmail.com">brefindvt@gmail.com</a></button>
					 </div>
					<div class="clearfix"></div>
				</div>
			</div>
			<div class="row">
				<div class="box">
					<div class="col-lg-12">
						<hr>
						<h2 class="intro-text text-center">Contact
							<strong>form</strong>
						</h2>
						<hr>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Fugiat, vitae, distinctio, possimus repudiandae cupiditate ipsum excepturi dicta neque eaque voluptates tempora veniam esse earum sapiente optio deleniti consequuntur eos voluptatem.</p>
						<form role="form">
							<div class="row">
								<div class="form-group col-lg-4">
									<label>Name</label>
									<input type="text" class="form-control">
								</div>
								<div class="form-group col-lg-4">
									<label>Email Address</label>
									<input type="email" class="form-control">
								</div>
								<div class="form-group col-lg-4">
									<label>Phone Number</label>
									<input type="tel" class="form-control">
								</div>
								<div class="clearfix"></div>
								<div class="form-group col-lg-12">
									<label>Message</label>
									<textarea class="form-control" rows="6"></textarea>
								</div>
								<div class="form-group col-lg-12">
									<input type="hidden" name="save" value="contact">
									<button type="submit" class="btn btn-default">Submit</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<?php include 'footer.html' ?>
		<!-- jQuery -->
		<script src="js/lib/jquery.js"></script>
		<!-- Bootstrap Core JavaScript -->
		<script src="js/lib/bootstrap.min.js"></script>
	</body>
</html>
<!DOCTYPE html>
<html lang="en">
<head>
	<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");
	</script>
	 <!-- jQuery -->
	<script src="js/lib/jquery.js"></script>
	<!-- Bootstrap Core JavaScript -->
	<script src="js/lib/bootstrap.min.js"></script>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

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
    <title>BrewFind</title>
</head>
<style>
div.ex1 {
    width:500px;
	

}
</style>
	<body>
		<?php include 'header.html' ?>
		<div class="container">
			<div class="row">
				<div class="box">
					<div class="col-lg-12 text-center">
						<h2 class="brand-before">
							<small>Welcome to</small>
						</h2>
						<h1 class="brand-name">BrewFind</h1>
						<hr class="tagline-divider">
						<center><div class ="ex1">
						<div id="carousel-example-generic" class="carousel slide" >
							<!-- Indicators -->
							<ol class="carousel-indicators hidden-xs" >
								<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
								<li data-target="#carousel-example-generic" data-slide-to="1"></li>
								<li data-target="#carousel-example-generic" data-slide-to="2"></li>
							</ol>
							<!-- Wrapper for slides -->
							<div class="carousel-inner"  height="42" width="42">
								<div class="item active"  height="42" width="42">
									<img class="img-responsive img-full" src="img/slide-1.png" alt="" height="42" width="42">
								</div>
								<div class="item"  height="42" width="42">
									<img class="img-responsive img-full" src="img/slide-2.jpg" alt="" height="42" width="42">
								</div>
								<div class="item"  height="42" width="42">
									<img class="img-responsive img-full" src="img/slide-3.jpg" alt="" height="42" width="42">
								</div>
							</div>
							<!-- Controls -->
							<a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
								<span class="icon-prev"></span>
							</a>
							<a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
								<span class="icon-next"></span>
							</a>
						</div>
						</div></center>
						
					</div>
					<br>
					<div class="col-lg-12">
						<hr>
						<h2 class="intro-text text-center">About Us
							<strong>How we started</strong>
						</h2>
						<hr>
						<img class="img-responsive img-border img-left" src="img/intro-pic.jpg" alt="">
						<hr class="visible-xs">
						<br>
						<br>
						<p>We are a bunch of guys who love Vermont and craft brews, so we thought for our senior projects why not do something that can help others find what we love so much.</p>
					</div>
				</div>
			</div>
		</div>
		<?php include 'footer.html' ?>
		<!-- /.container -->
		
		<!-- Script to Activate the Carousel -->
		<script>
			$('.carousel').carousel
			({
				interval: 5000 //changes the speed
			})
		</script>
	</body>
</html>

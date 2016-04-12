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
		<title>BrewFind - Breweries</title>
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
	<div class="container">
		<div class="row">
			<div class="box">
				<div class="col-lg-12 text-center">
				<hr>
					<h2>Select a Brewery for more info!</h2>
				<hr>
				</div>

				
			</div>
			<div id="breweries-carousel" class="carousel slide" data-ride="carousel">
				  
				  <ol class="carousel-indicators" data-bind="foreach: breweryGroups">
				    	<li data-target="#breweries-carousel" data-bind="attr: { 'data-slide-to': $index() }" ></li>
				  </ol>

				  <!-- Wrapper for slides -->
				  <div class="carousel-inner" role="listbox" data-bind="foreach: breweryGroups">
			    	<div class="item" data-bind="css: {'active': $index()==0}">
			    		<div class="row">
					        <div data-bind="foreach: $data">
					            <div class="col-md-2">
						            <div class="thumbnail" data-bind="click: $root.populateBrewery">
									      <img data-bind="attr: { 'src': $root.imgUrl + '/img/breweries/' + b_breweryNum + '/brewery_profile_pic.jpg'}" alt="" style="height: 150px">
									      <div class="caption">
									        <h4 data-bind="text: b_name"></h4>
									      </div>
									</div>				              
				        		</div>
				        	</div>
				        </div>
				    </div>
				  </div>

				  <!-- Controls -->
				  <a class="left carousel-control" href="#breweries-carousel" role="button" data-slide="prev" style="width: 75px">
				    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
				    <span class="sr-only">Previous</span>
				  </a>
				  <a class="right carousel-control" href="#breweries-carousel" role="button" data-slide="next" style="width: 75px">
				    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				    <span class="sr-only">Next</span>
				  </a>
				</div>
		</div>
	</div>
	<!-- Page Content -->
	<div class="container" id="breweryBox" style="display: none">
		<div class="box">	
			<div class="jumbotron text-center">
				<h1 id="brewTitle"></h1>
				<hr>
			</div>
			<div class="col-md-8 text-left">
				<p id="bDesc"></p>
				<hr><br/>
				<h1 class="text-center">Drink List</h1>
				<div class="panel-group" id="drinkAccordion" role="tablist" aria-multiselectable="true" data-bind="foreach: drinkList">
  					<div class="panel panel-default">
  						<div class="panel-heading" role="tab" data-bind="attr: { 'id': 'head' + tag }">
  							<h4 class="panel-title">
  								<a class="collapsed" role="button" data-toggle="collapse" data-parent="#drinkAccordion" aria-expanded="false" data-bind="attr: { 'href': '#coll' + tag, 'aria-controls': 'coll' + tag}, text: d_name"></a>
  							</h4>
  						</div>
  						<div class="panel-collapse collapse" role="tabpanel" data-bind="attr: { 'id': 'coll' + tag, 'aria-labelledby':'head' + tag}">
  							<div class="panel-body">
  								<div class="box">
	  								<div class="row">
		  								<div class="well well-sm col-md-4">
		  									<p class="text-center" data-bind="text: 'ABV: ' + d_abv"></p>
		  								</div>
		  								<div class="well well-sm col-md-4">
		  									<p class="text-center" data-bind="text: 'IBU: ' + d_ibu"></p>
		  								</div>
		  								<div class="well well-sm col-md-4">
		  									<p class="text-center" data-bind="text: 'Rating: ' + d_rating"></p>
		  								</div>
		  							</div>
		  							<div class="row">
		  								<p data-bind="text: d_description"></p>
		  							</div>
		  						</div>
      						</div>
      					</div>
      				</div>
      			</div>
			</div>
			<div class="col-md-4">
				<img id="brewLogo" class="thumbnail img-responsive center-block">
				<strong>Street Address:</strong>
				<p id="bAddr" class="text-center"></p>

				<strong>Phone:</strong>
				<p id="bPhone" class="text-center"></p>

				<strong>Email:</strong>
				<p id="bEmail" class="text-center"></p>

				<strong>Visit their site: </strong>
				<p class="text-center"><a id="bUrl"></a></p>
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

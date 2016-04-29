<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>BrewFind - About</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/business-casual.css" rel="stylesheet">

    <!-- Our own CSS -->
    <link href="css/site.css" rel="stylesheet">

    <script src="js/site/login.js"></script>

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
            <div class="box clearfix">
                <div class="col-lg-12 text-center">
				<hr>
                   <h2>BrewFind</h2>
                    <p>BrewFind started as a project for a Software Engineering assignment and developed a year later into our Senior Project. The idea behind the project came from one of our developers, Ted Moore, who was trying to plan for a visit from his sister who lives in Oregon. He knew she was a beer geek like him, and he wanted to plan a trip to some of Vermont’s amazing breweries. The trouble Ted ran into while doing this, was having to jump from site - to - site trying to get brewery hours, brewery taps and locations to lay out a good trip for himself and his sister. This gave him the idea of BrewFind - a mobile application and conjoined website where he, and other people, would be able to view breweries, their locations, and what they have on tap.
Ted proposed the idea as a senior project and we were all on board immediately. All five members of our team are long time Vermonters who have spent most, or all, of our lives in this great state and as such all developed a taste for great beer.
</p>
				<hr>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
        <div class="row">
            <div class="box clearfix">
                <div class="col-lg-12 text-center">
				<hr>
                    <h2>The Project
                    <br>
                        
                    </h2>
                    <p>BrewFind is a mobile application and website designed to help users better find local breweries, what they have on tap, and what events breweries are attending or hosting. Currently, BrewFind is in development for Android and will later be available for iOS. BrewFind will work in conjunction with Untappd to gather drink data, and the Vermont Brewer’s Association to digitalize their passport program. In addition, a big part of BrewFind will be working with Vermont’s Breweries to get accurate, up-to-date information for users to view current taps, upcoming events, and general brewery information.</p>
                <hr>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
        <div class="row">
            <div class="box clearfix">
				<div class="col-lg-12 text-center">
					<hr>
					<h2>The Team</h2>
					<hr><br>
				</div>
				<div class="col-lg-3"></div>
		        <div class="col-lg-8 text-center">
					<table><tr>
					<td><img src="img/mark.jpg" alt="Mark" class="aboutPic"></td>
					<td><ul style= "list-style-type:none"><li><p>Mark Harwood</p></li>
					<li><p>Team Lead, Application Development</p></li></ul><td>
					</tr>
					<tr>
					<td><img src="img/Ted.jpg" alt="ted" class="aboutPic"></td>
					<td><ul style= "list-style-type:none"><li><p>Ted Moore</p></li>
					<li><p>Bachelor’s - Software Engineering</p></li>
					<li><p>Application Development</p></li></ul><td>
					</tr>
					<tr>
					<td><img src="img/Spencer.jpg" alt="spencer" class="aboutPic"></td>
					<td><ul style= "list-style-type:none"><li><p>Spencer Rugg</p></li>
					<li><p>Business Relations, System Admin, Web Developer</p></li></ul><td>
					</tr>
					<tr>
					<td><img src="img/ken.jpg" alt="Ken" class="aboutPic"></td>
					<td><ul style= "list-style-type:none"><li><p>Ken Bernard</p></li>
					<li><p>System Admin, Database Admin</p></li></ul><td>
					</tr>
					<tr>
					<td><img src="img/tom.jpg" alt="tom" class="aboutPic"></td>
					<td><ul style= "list-style-type:none"><li><p>Tom Pelchat</p></li>
					<li><p>Web Developer</p></li></ul><td>
					</tr></table>
				</div>
			</div>
		</div>
	</div>
    <!-- /.container -->

    <div class="container">
    	<div class="box clearfix">
    		<div class="panel-group" id="gb-login" role="tablist">
    			<div class="panel panel-default">
    				<div class="panel-heading" role="tab" id="gbl-head">
    					<h3 class="panel-title">
    						<a role="button" data-toggle="collapse" data-parent="gb-login" href="#gbl-tab" aria-expanded="false" class="text-centered"> Admin Login </a>
    					</h3>
    				</div>
    				<div id="gbl-tab" class="panel-collapse collapse" role="tabpanel" aria-labelledby="gbl-head">
    					<div class="panel-body">			
							<form class="form-horizontal" name="gbLoginForm" id="gbLogin">
								<div class="form-group">
									<label class="col-sm-2 control-label" for="gbl_uname">Username:</label>
									<div class="col-sm-4">
										<input type="text" class="form-control" name="uname" id="gbl_uname" placeholder="Username" required>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label" for="gbl_pass">Password:</label>
									<div class="col-sm-4">
										<input type="password" class="form-control" name="pass" id="gbl_pass" placeholder="Password" required>
									</div>
								</div>
								<div class="col-sm-offset-2 col-sm-10">
									<button type="submit" onClick="gbLogin()" class="btn btn-default">Log in to admin portal</button>
								</div>
							</form>
						</div>
					</div>
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

<!DOCTYPE html>
<html lang="en">
<head>
    <title>BrewFind - Calendar</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="http://ajax.aspnetcdn.com/ajax/knockout/knockout-3.3.0.js"></script> 
    <script src="js/lib/bootstrap.js"></script>
    <script src="js/site/resources.js"></script>
    <script src="js/site/cacheManager.js"></script>
    <script src="js/site/eventsViewModel.js"></script>
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

    <div class="box">
        <div class="col-lg-12 text-center">
          <hr>
            <h2>Brewery Events</h2>
          <hr>
        </div>
    </div>
    
    <div class="panel-group" id="eventAccordion" role="tablist" aria-multiselectable="true" data-bind="foreach: eventList">
      <div class="panel panel-default">
        <div class="panel-heading" role="tab" data-bind="attr: { 'id': 'head' + tag }">
          <div class="media">
            <div class="media-left">
              <img data-bind="attr: { 'src': $root.imgUrl + '/img/breweries/' + breweryNum + '/brewery_profile_pic.jpg'}" alt="" style="height: 100px">
            </div>
            <div class="media-body">
              <h3 class="media-heading">
                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#eventAccordion" aria-expanded="false" data-bind="attr: { 'href': '#coll' + tag, 'aria-controls': 'coll' + tag}, text: name"></a>
              </h3>
              <h4 data-bind="text: breweryName"></h4>
              <h4 data-bind="text: startString"></h4>
            </div>
          </div>
        </div>
        <div class="panel-collapse collapse" role="tabpanel" data-bind="attr: { 'id': 'coll' + tag, 'aria-labelledby':'head' + tag}">
          <div class="panel-body">
            <div class="container-fluid">
              <strong>Description:</strong>
              <p data-bind="text: description"></p>

              <strong>Location:</strong>
              <p data-bind="text: location"></p>
              
              <div class="row">
                <div class="col-md-3">
                  <div data-bind="attr: {'style': 'border: 2px solid ' + famColor}" class="thumbnail">
                    <img id="famPic" src="img/people.png">
                    <hr>
                    <h4 class="text-center" data-bind="text: famString"></h4>
                  </div>
                </div>
                <div class="col-md-3">
                  <div data-bind="attr: {'style': 'border: 2px solid ' + petColor}" class="thumbnail">
                    <img id="petPic" src="img/animal.png">
                    <hr>
                    <h4 class="text-center" data-bind="text: petString"></h4>
                  </div>
                </div>
                <div class="col-md-3">
                  <div data-bind="attr: {'style': 'border: 2px solid ' + outdoorColor}" class="thumbnail">
                    <img id="outdoorPic" src="img/table.png">
                    <hr>
                    <h4 class="text-center" data-bind="text: outdoorString"></h4>
                  </div>
                </div>
                <div class="col-md-3">
                  <div style="border: 2px solid black" class="thumbnail">
                    <img id="pricePic" src="img/money.png">
                    <hr>
                    <h4 class="text-center" data-bind="text: priceString"></h4>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    
  </div>
    <!-- <img data-bind="attr: { 'src': $root.imgUrl + '/img/breweries/' + b_breweryNum + '/brewery_profile_pic.jpg'}" alt="" style="height: 150px"> -->

  </div>

  <?php include 'footer.html' ?>

  <script>
    var evm = new EventsViewModel();
    fetchEvents(evm.eventList);
    ko.applyBindings(evm);
  </script>
</body>
</html>
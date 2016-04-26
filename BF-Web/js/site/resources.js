var resources = {};

resources.imgUrl = "http://ec2-52-38-43-166.us-west-2.compute.amazonaws.com"
resources.apiUrl = "http://52.35.37.107:8080";
resources.breweryEnd = resources.apiUrl + "/brewery";
resources.eventEnd = resources.apiUrl + "/event";
resources.userEnd = resources.apiUrl + "/user";
resources.drinkEnd = resources.apiUrl + "/utwrapper";
resources.userAuthEnd = resources.apiUrl + "/user/auth";

resources.testToken = {};
resources.testToken["token"] = "YmZhZG1pbg==";
resources.testToken["access"] = 3;
resources.testToken["stamp"] = 1458311957462;
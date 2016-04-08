document.body.className += ' js-enabled';

// Global variables
var urllist = [];
var maxResults = 3; // The default is 3 results unless a value is sent
var JSONData = {};
var eventCount = 0;
var errorLog = "";

JSONData = { count: 0,
    value : {
    description: "Aggregates multiple Google calendar feeds into a single sorted list",
    generator: "StackOverflow communal coding - Thanks for the assist Patrick M",
    website: "http://jeramy.kruser.me",
    author: "Jeramy & Kasey Kruser",
    items: []
}};

// Prototype forEach required for IE
if ( !Array.prototype.forEach ) {
  Array.prototype.forEach = function(fn, scope) {
    for(var i = 0, len = this.length; i < len; ++i) {
      fn.call(scope, this[i], i, this);
    }
  }
}

// For putting dates from feed into a format that can be read by the Date function for calculating event length.
function parse (str) {
        // validate year as 4 digits, month as 01-12, and day as 01-31 
        str = str.match (/^(\d{4})(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])$/);
        if (str) {
           // make a date
           str[0] = new Date ( + str[1], + str[2] - 1, + str[3]);
           // check if month stayed the same (ie that day number is valid)
           if (str[0].getMonth () === + str[2] - 1) {
              return str[0];
              }
        }
        return undefined;
 }

//For outputting to HTML
function output() {
    var months, day_in_ms, summary, i, item, eventlink, title, calendar, where,dtstart, dtend, endyear, endmonth, endday, startyear, startmonth, startday, endmonthdayyear, eventlinktitle, startmonthday, length, curtextval, k;
    // Array of month names from numbers for page display.
    months = {'0':'January', '1':'February', '2':'March', '3':'April', '4':'May', '5':'June', '6':'July', '7':'August', '8':'September', '9':'October', '10':'November', '11':'December'};
    // For use in calculating event length.
    day_in_ms = 24 * 60 * 60 * 1000;
    // Instantiate HTML Arrays.
    summary = [];
    for (i = 0; i < maxResults; i+=1 ) {
        // console.log("i: "+i+" < "+"maxResults: "+ maxResults);
        if (!(JSONData.value.items[i] === undefined)) {     
            item = JSONData.value.items[i];
            // Grabbing data for each event in the feed.
            eventlink = (item.link[0].href);
            title = item.title.$t; 
            // Only display the calendar title if there is more than one 
            calendar = "";
            if (urllist.length > 1) {
                calendar = '<br />Calendar: <a href="https://www.google.com/calendar/embed?src=' + item.gd$who[0].email + '&ctz=America/New_York">' + item.gd$who[0].valueString + '<\/a> (<a href="https://www.google.com/calendar/ical/' + item.gd$who[0].email + '/public/basic.ics">iCal<\/a>)'; 
            }
            // Grabbing event location, if entered.
            if ( item.gd$where[0].valueString !== "" ) {
                where = '<br />' + (item.gd$where[0].valueString); 
                }
            else {
                where = (""); 
                }
            // Grabbing start date and putting in form YYYYmmdd. Subtracting one day from dtend without a specified end time (which contains colons) to fix Google's habit of ending an all-day event at midnight on the following day.
            dtstart = new Date(parse(((item.gd$when[0].startTime).substring(0,10)).replace(/-/g,"")));
            if((item.gd$when[0].endTime).indexOf(':') === -1) {
                  dtend = new Date(parse(((item.gd$when[0].endTime).substring(0,10)).replace(/-/g,"")) - day_in_ms);
                }
            else {
                  dtend = new Date(parse(((item.gd$when[0].endTime).substring(0,10)).replace(/-/g,"")));
                }
            // Put dates in pretty form for display.
            endyear = dtend.getFullYear();  
            endmonth = months[dtend.getMonth()];
            endday = dtend.getDate();
            startyear = dtstart.getFullYear();  
            startmonth = months[dtstart.getMonth()];
            startday = dtstart.getDate();
            //consolidate some much-used variables for HTML output.
            endmonthdayyear = endmonth + ' ' + endday + ', ' + endyear;
            eventlinktitle = '<a href="' + eventlink + '">' + title + '<\/a>';
            startmonthday = startmonth + ' ' + startday;
            // Calculates the number of days between each event's start and end dates. 
            length = ((dtend - dtstart) / day_in_ms);
            // HTML for each event, depending on which div is available on the page (different HTML applies). Only one div can exist on any one page. 
            if (document.getElementById("homeCalendar") !== null ) {
                // If the length of the event is greater than 0 days, show start and end dates. 
                if ( length > 0 && startmonth !== endmonth && startday === endday ) {       
                        summary[i] = ('<h3>' + eventlink + '">' + startmonthday + ', ' + startyear + ' - ' + endmonthdayyear + '<\/a><\/h3><p>' + title + '<\/p>'); }
                    // If the length of the event is greater than 0 and begins and ends within the same month, shorten the date display.
                    else if ( length > 0 && startmonth === endmonth && startyear === endyear ) {        
                        summary[i] = ('<h3><a href="' + eventlink + '">' + startmonthday + '-' + endday + ', ' + endyear + '<\/a><\/h3><p>' + title + '<\/p>'); }
                    // If the length of the event is greater than 0 and begins and ends within different months of the same year, shorten the date display.
                    else if ( length > 0 && startmonth !== endmonth && startyear === endyear ) {        
                        summary[i] = ('<h3><a href="' + eventlink + '">' + startmonthday + ' - ' + endmonthdayyear + '<\/a><\/h3><p>' + title + '<\/p>'); }
                    // If the length of the event is less than one day (length < = 0), show only the start date.
                    else { 
                        summary[i] = ('<h3><a href="' + eventlink + '">' + startmonthday + ', ' + startyear + '<\/a><\/h3><p>' + title + '<\/p>'); }
            }        
            else if (document.getElementById("allCalendar") !== null ) {
                // If the length of the event is greater than 0 days, show start and end dates. 
                 if ( length > 0 && startmonth !== endmonth && startday === endday ) {           
                           summary[i] = ('<li>' + eventlinktitle + '<br />' + startmonthday + ', ' + startyear + ' - ' + endmonthdayyear + where + calendar + '<\/li>'); } 
                      // If the length of the event is greater than 0 and begins and ends within the same month, shorten the date display. 
                      else if ( length > 0 && startmonth === endmonth && startyear === endyear ) {           
                           summary[i] = ('<li>' + eventlinktitle + '<br />' + startmonthday + '-' + endday + ', ' + endyear + where + calendar + '<\/li>'); } 
                      // If the length of the event is greater than 0 and begins and ends within different months of the same year, shorten the date display. 
                      else if ( length > 0 && startmonth !== endmonth && startyear === endyear ) {           
                           summary[i] = ('<li>' + eventlinktitle + '<br />' + startmonthday + ' - ' + endmonthdayyear + where + calendar + '<\/li>'); } 
                      // If the length of the event is less than one day (length < = 0), show only the start date. 
                      else {  
                           summary[i] = ('<li>' + eventlinktitle + '<br />' + startmonthday + ', ' + startyear + where + calendar + '<\/li>'); }
            } 
        }
        if (summary[i] === undefined) { summary[i] = "";}
        // console.log(summary[i]);
    }
    // console.log(JSONData);
    // Puts the HTML into the div with the appropriate id. Each page can have only one.
    if (document.getElementById("homeCalendar") !== null ) {
        curtextval = document.getElementById("homeCalendar");
        // console.log("homeCalendar: "+curtextval);
        }
    else if (document.getElementById("oneCalendar") !== null ) {
        curtextval = document.getElementById("oneCalendar");
        // console.log("oneCalendar: "+curtextval);
         }
    else if (document.getElementById("allCalendar") !== null ) {
        curtextval = document.getElementById("allCalendar");
        // console.log("allCalendar: "+curtextval.innerHTML);
         }
    for (k = 0; k<maxResults; k+=1 ) { curtextval.innerHTML = curtextval.innerHTML + summary[k]; }
    if (JSONData.count === 0) {
        errorLog += '<div id="noEvents">No events found.</div>';
    }
    if (document.getElementById("homeCalendar") === null ) {
        curtextval.innerHTML = '<ul>' + curtextval.innerHTML + '<\/ul>'; 
        }
    if (errorLog !== "") {
        curtextval.innerHTML += errorLog;
        }
}

// For taking in each feed, breaking out the events and sorting them into the object by date
function sortFeed(event) {  
    var tempEntry, i;
    tempEntry = event;
    i = 0;
    // console.log("*** New incoming event object #"+eventCount+" ***");
    // console.log(event.title.$t);
    // console.log(event);
    // console.log("i = " + i + " and maxResults " + maxResults);
    while(i<maxResults) {
        // console.log("i = " + i + " < maxResults " + maxResults);
        // console.log("Sorting event = " + event.title.$t + " by date of " + event.gd$when[0].startTime.substring(0,10).replace(/-/g,""));
        if (JSONData.value.items[i]) {
            // console.log("JSONData.value.items[" + i + "] exists and has a startTime of " + JSONData.value.items[i].gd$when[0].startTime.substring(0,10).replace(/-/g,""));
            if (event.gd$when[0].startTime.substring(0,10).replace(/-/g,"")<JSONData.value.items[i].gd$when[0].startTime.substring(0,10).replace(/-/g,"")) {
                // console.log("The incoming event value of " + event.gd$when[0].startTime.substring(0,10).replace(/-/g,"") + " is < " + JSONData.value.items[i].gd$when[0].startTime.substring(0,10).replace(/-/g,""));
                tempEntry = JSONData.value.items[i];
                // console.log("Existing JSONData.value.items[" + i + "] value " + JSONData.value.items[i].gd$when[0].startTime.substring(0,10).replace(/-/g,"") + " stored in tempEntry");
                JSONData.value.items[i] = event;
                // console.log("Position JSONData.value.items[" + i + "] set to new value: " + event.gd$when[0].startTime.substring(0,10).replace(/-/g,""));
                event = tempEntry;
                // console.log("Now sorting event = " + event.title.$t + " by date of " + event.gd$when[0].startTime.substring(0,10).replace(/-/g,""));
            }
            else {
                // console.log("The incoming event value of " + event.gd$when[0].startTime.substring(0,10).replace(/-/g,"") + " is > " + JSONData.value.items[i].gd$when[0].startTime.substring(0,10).replace(/-/g,"") + " moving on...");
            }
        }
        else {
            JSONData.value.items[i] = event;
            // console.log("JSONData.value.items[" + i + "] does not exist so it was set to the Incoming value of " + event.gd$when[0].startTime.substring(0,10).replace(/-/g,""));
            i = maxResults;
        }
        i += 1;
    }
}

// For completing the aggregation
function complete(result) {
    // Track the number of calls completed back, we're not done until all URLs have processed
    if( complete.count === undefined ){
        complete.count = urllist.length;        
    }
    // console.log("complete.count = "+complete.count);
    // console.log(result.feed);
    if(result.feed.entry){
        JSONData.count = maxResults;
        // Check each incoming item against JSONData.value.items
        // console.log("*** Begin Sorting " + result.feed.entry.length + " Events ***");
        // console.log(result.feed.entry);
        result.feed.entry.forEach(
            function(event){
                eventCount += 1;
                sortFeed(event);                
            }
        );
    }
    if( (complete.count-=1)<1 ) {
        // console.log("*** Done Sorting ***");
        output();
    }
}

// This is the main function. It takes in the list of Calendar IDs and the number of results to display
function GCalMFA(list,results){
        var i, calPreProperties, calPostProperties1, calPostProperties2;
        calPreProperties = "https://www.google.com/calendar/feeds/";
        calPostProperties1 = "/public/full?max-results=";
        calPostProperties2 = "&orderby=starttime&sortorder=ascending&futureevents=true&ctz=America/New_York&singleevents=true&alt=json&callback=?";

        if (list) {
            if (results) {
                maxResults = results;
            }
            urllist = list.split(',');
            for (i = 0; i < urllist.length; i+=1 ){
                // console.log(urllist[i]);
                if (urllist[i] === ""){ urllist.splice(i,1);}
                else{
                urllist[i] = calPreProperties + urllist[i] + calPostProperties1+maxResults+calPostProperties2;}
            }
            // console.log("There are " + urllist.length + " URLs");
            urllist.forEach(function addFeed(url){
                $.getJSON(url, complete);   
            });
        }
        else {
            errorLog += '<div id="noURLs">No calendars have been selected.</div>';
            output();
        }
}
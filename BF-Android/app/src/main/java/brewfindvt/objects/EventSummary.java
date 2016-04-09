package brewfindvt.objects;

import com.google.api.client.util.DateTime;

/**
 * Created by user on 2/26/2016.
 */
public class EventSummary {

    String name;
    String id;
    String description;
    String htmlLink;
    String location;
    String breweryName;
    String startDate;
    String endDate;
    String day;
    String month;
    String year;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtmlLink() {
        return htmlLink;
    }

    public void setHtmlLink(String htmlLink) {
        this.htmlLink = htmlLink;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBreweryName() {
        return breweryName;
    }

    public void setBreweryName(String breweryName) {
        this.breweryName = breweryName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDay(){ return this.day; }

    public void setDay (String day){ this.day = day; }

    public String getMonth() { return this.month; }

    public void setMonth(String month) { this.month = month; }

    public String getYear() { return this.year; }

    public void setYear(String year) { this.year = year; }

    // name
    // id
    // summmary
    // html link
    // start date
    // end date
    // location


}

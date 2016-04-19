package brewfindvt.objects;

import com.google.api.client.util.DateTime;

import java.util.Map;

/**
 * Created by user on 2/26/2016.
 */
public class EventSummary {

    String name;
    String id;
    String description;
    String htmlLink;

    Boolean atBreweryLocation;
    String location;
    String breweryName;
    String startDate;
    String endDate;
    int day;
    int month;
    int year;


    Boolean isFamFriendly;
    Boolean isPetFriendly;
    Boolean isOutdoor;
    Double ticketCost;

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

    public int getDay(){ return this.day; }

    public void setDay (int day){ this.day = day; }

    public int getMonth() { return this.month; }

    public void setMonth(int month) { this.month = month; }

    public int getYear() { return this.year; }

    public void setYear(int year) { this.year = year; }

    public Boolean getFamFriendly() {
        return isFamFriendly;
    }

    public void setFamFriendly(Boolean famFriendly) {
        isFamFriendly = famFriendly;
    }

    public Boolean getPetFriendly() {
        return isPetFriendly;
    }

    public void setPetFriendly(Boolean petFriendly) {
        isPetFriendly = petFriendly;
    }

    public Double getTicketCost() {
        return ticketCost;
    }

    public void setTicketCost(Double ticketCost) {
        this.ticketCost = ticketCost;
    }

    public Boolean getOutdoor() {
        return isOutdoor;
    }

    public void setOutdoor(Boolean outdoor) {
        isOutdoor = outdoor;
    }

    public Boolean isAtBreweryLocation() {
        return atBreweryLocation;
    }

    public void setAtBreweryLocation(Boolean atBreweryLocation) {
        this.atBreweryLocation = atBreweryLocation;
    }

}

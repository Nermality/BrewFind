package brewfindvt.objects;

import java.util.Map;

/**
 * Created by user on 2/2/2016.
 */
public class Brewery extends BrewFindObject {

    public String b_id;

    public String b_name;

    public String b_addr1;
    public String b_addr2;
    public String b_city;
    public String b_state;
    public String b_zip;

    public String b_phone;
    public String b_email;
    public String b_url;

    public String b_description;

    public Boolean b_hasTour;
    public Boolean b_hasFood;
    public Boolean b_hasTap;
    public Boolean b_hasGrowler;

    public int b_breweryNum;
    public int b_version;
    public int b_utId;
    //public String b_admin

    public String    b_logoImage;

    public Double b_rating;

    public Double b_lat;
    public Double b_long;


    Map<Integer, String> openDates;
    Map<Integer, String> closeDates;

    //public Set<Drink> b_drinkList;

    //facebook


    public Double getB_rating() {
        return b_rating;
    }

    public void setB_rating(Double b_rating) {
        this.b_rating = b_rating;
    }

    public String getB_description() {
        return b_description;
    }

    public void setB_description(String b_description) {
        this.b_description = b_description;
    }

    public Boolean getB_hasTour() {
        return b_hasTour;
    }

    public void setB_hasTour(Boolean b_hasTour) {
        this.b_hasTour = b_hasTour;
    }

    public Boolean getB_hasFood() {
        return b_hasFood;
    }

    public void setB_hasFood(Boolean b_hasFood) {
        this.b_hasFood = b_hasFood;
    }

    public Boolean getB_hasTap() {
        return b_hasTap;
    }

    public void setB_hasTap(Boolean b_hasTap) {
        this.b_hasTap = b_hasTap;
    }

    public Boolean getB_hasGrowler() {
        return b_hasGrowler;
    }

    public void setB_hasGrowler(Boolean b_hasGrowler) {
        this.b_hasGrowler = b_hasGrowler;
    }

    public int getB_breweryNum() {
        return b_breweryNum;
    }

    public void setB_breweryNum(int b_breweryNum) {
        this.b_breweryNum = b_breweryNum;
    }

    public int getB_version() {
        return b_version;
    }

    public void setB_version(int b_version) {
        this.b_version = b_version;
    }

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }

    public String getB_addr1() {
        return b_addr1;
    }

    public void setB_addr1(String b_addr1) {
        this.b_addr1 = b_addr1;
    }

    public String getB_addr2() {
        return b_addr2;
    }

    public void setB_addr2(String b_addr2) {
        this.b_addr2 = b_addr2;
    }

    public String getB_city() {
        return b_city;
    }

    public void setB_city(String b_city) {
        this.b_city = b_city;
    }

    public String getB_state() {
        return b_state;
    }

    public void setB_state(String b_state) {
        this.b_state = b_state;
    }

    public String getB_zip() {
        return b_zip;
    }

    public void setB_zip(String b_zip) {
        this.b_zip = b_zip;
    }

    public String getB_phone() {
        return b_phone;
    }

    public void setB_phone(String b_phone) {
        this.b_phone = b_phone;
    }

    public String getB_email() {
        return b_email;
    }

    public void setB_email(String b_email) {
        this.b_email = b_email;
    }

    public String getB_url() {
        return b_url;
    }

    public void setB_url(String b_url) {
        this.b_url = b_url;
    }

    public Double getB_lat(){
        return b_lat;
    }

    public void setB_lat(Double b_lat){
        this.b_lat=b_lat;
    }

    public Double getB_long(){
        return b_long;
    }

    public void setB_long(Double b_long){
        this.b_long=b_long;
    }

    public String getB_logoImage() { return b_logoImage; }

    public void setB_logoImage(String logoImage) { this.b_logoImage= logoImage; }

    public int getB_utId() { return b_utId; }

    public void setB_utId(int b_utId) { this.b_utId = b_utId; }

    public Map<Integer, String> getOpenDates() {
        return openDates;
    }

    public void setOpenDates(Map<Integer, String> openDates) {
        this.openDates = openDates;
    }

    public Map<Integer, String> getCloseDates() {
        return closeDates;
    }

    public void setCloseDates(Map<Integer, String> closeDates) {
        this.closeDates = closeDates;
    }
}

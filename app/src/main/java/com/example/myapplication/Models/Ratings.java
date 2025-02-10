package com.example.myapplication.Models;

public class Ratings {
    private String ratingID;
    private String userID;
    private String phoneID;
    private int ratingValue;

    public Ratings(String ratingID, String userID, String phoneID, int ratingValue) {
        this.ratingID = ratingID;
        this.userID = userID;
        this.phoneID = phoneID;
        this.ratingValue = ratingValue;
    }

    public String getRatingID() {
        return ratingID;
    }

    public void setRatingID(String ratingID) {
        this.ratingID = ratingID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(String phoneID) {
        this.phoneID = phoneID;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }
}

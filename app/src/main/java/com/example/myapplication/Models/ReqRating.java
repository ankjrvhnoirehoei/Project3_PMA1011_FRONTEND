package com.example.myapplication.Models;

public class ReqRating {
    private String phoneID;

    public ReqRating(String phoneID) {
        this.phoneID = phoneID;
    }

    public String getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(String phoneID) {
        this.phoneID = phoneID;
    }
}

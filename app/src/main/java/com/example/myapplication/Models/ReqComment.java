package com.example.myapplication.Models;

public class ReqComment {

    private String phoneID;

    public ReqComment(String phoneID) {
        this.phoneID = phoneID;
    }

    public String getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(String phoneID) {
        this.phoneID = phoneID;
    }
}

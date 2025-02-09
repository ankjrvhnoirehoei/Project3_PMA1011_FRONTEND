package com.example.myapplication.Models;

public class ReqGetUser {
    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ReqGetUser(String userID) {
        this.userID = userID;
    }
}

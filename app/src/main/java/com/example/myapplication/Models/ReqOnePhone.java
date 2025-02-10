package com.example.myapplication.Models;

public class ReqOnePhone {
    private String PhoneID;

    public ReqOnePhone(String phoneID) {
        PhoneID = phoneID;
    }

    public String getPhoneID() {
        return PhoneID;
    }

    public void setPhoneID(String phoneID) {
        PhoneID = phoneID;
    }
}

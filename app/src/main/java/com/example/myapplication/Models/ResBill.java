package com.example.myapplication.Models;

import java.io.Serializable;

public class ResBill implements Serializable {
    private String billID;
    private String userID;
    private int total;

    public ResBill(String billID, String userID, int total) {
        this.billID = billID;
        this.userID = userID;
        this.total = total;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

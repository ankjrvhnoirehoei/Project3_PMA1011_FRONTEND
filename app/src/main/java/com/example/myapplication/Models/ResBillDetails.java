package com.example.myapplication.Models;

import java.io.Serializable;

public class ResBillDetails implements Serializable {
    private String billDetailID;
    private String billID;
    private String phoneID;
    private String status;
    private String dateCreated;
    private int quantity;

    public ResBillDetails(String billDetailID, String billID, String phoneID, String status, String dateCreated, int quantity) {
        this.billDetailID = billDetailID;
        this.billID = billID;
        this.phoneID = phoneID;
        this.status = status;
        this.dateCreated = dateCreated;
        this.quantity = quantity;
    }

    public String getBillDetailID() {
        return billDetailID;
    }

    public void setBillDetailID(String billDetailID) {
        this.billDetailID = billDetailID;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(String phoneID) {
        this.phoneID = phoneID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

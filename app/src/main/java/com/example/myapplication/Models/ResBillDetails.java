package com.example.myapplication.Models;

import java.io.Serializable;

public class ResBillDetails implements Serializable {
    private String billDetailID;
    private String billID;
    private String phoneID;
    private String productName;
    private String status;
    private String dateCreated;
    private int quantity;
    private int totalPrice;

    public ResBillDetails(String billDetailID, String billID, String phoneID, String productName,
                          String status, String dateCreated, int quantity, int totalPrice) {
        this.billDetailID = billDetailID;
        this.billID = billID;
        this.phoneID = phoneID;
        this.productName = productName;
        this.status = status;
        this.dateCreated = dateCreated;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getBillDetailID() {
        return billDetailID;
    }

    public String getBillID() {
        return billID;
    }

    public String getPhoneID() {
        return phoneID;
    }

    public String getProductName() {
        return productName;
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

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Trả về ngày mua hàng
    public String getPurchaseDate() {
        return dateCreated;
    }
}

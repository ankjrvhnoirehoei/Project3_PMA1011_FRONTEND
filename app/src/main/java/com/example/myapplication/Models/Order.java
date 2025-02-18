package com.example.myapplication.Models;
public class Order {
    private String productName;
    private String status;
    private String purchaseDate;
    private int quantity;

    public Order(String productName, String status, String purchaseDate, int quantity) {
        this.productName = productName;
        this.status = status;
        this.purchaseDate = purchaseDate;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public String getStatus() {
        return status;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public int getQuantity() {
        return quantity;
    }
}
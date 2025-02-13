package com.example.myapplication.Models;

public class Order {
    private String orderName;
    private int price;
    private String status;

    public Order(String orderName, int price, String status) {
        this.orderName = orderName;
        this.price = price;
        this.status = status;
    }

    public String getOrderName() {
        return orderName;
    }

    public int getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}

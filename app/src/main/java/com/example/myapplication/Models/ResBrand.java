package com.example.myapplication.Models;

public class ResBrand {
    private String brandID;
    private String brand;

    public ResBrand(String brandID, String brand) {
        this.brandID = brandID;
        this.brand = brand;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}

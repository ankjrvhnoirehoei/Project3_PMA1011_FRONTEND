package com.example.myapplication.Models;

import java.util.ArrayList;

public class ResOnePhone {
    private String phoneID;
    private ArrayList<String> image;
    private ArrayList<String> phoneColor;
    private String phoneName;
    private float phonePrice;
    private String phoneBrand;
    private String phoneType;
    private int phoneSold;
    private String phoneDescription;
    private int phoneStock;
    private int phoneWarranty;
    private int phoneInStore;

    public ResOnePhone(String phoneID, ArrayList<String> image, ArrayList<String> phoneColor, String phoneName, float phonePrice, String phoneBrand, String phoneType, int phoneSold, String phoneDescription, int phoneStock, int phoneWarranty, int phoneInStore) {
        this.phoneID = phoneID;
        this.image = image;
        this.phoneColor = phoneColor;
        this.phoneName = phoneName;
        this.phonePrice = phonePrice;
        this.phoneBrand = phoneBrand;
        this.phoneType = phoneType;
        this.phoneSold = phoneSold;
        this.phoneDescription = phoneDescription;
        this.phoneStock = phoneStock;
        this.phoneWarranty = phoneWarranty;
        this.phoneInStore = phoneInStore;
    }

    public String getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(String phoneID) {
        this.phoneID = phoneID;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

    public ArrayList<String> getPhoneColor() {
        return phoneColor;
    }

    public void setPhoneColor(ArrayList<String> phoneColor) {
        this.phoneColor = phoneColor;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public float getPhonePrice() {
        return phonePrice;
    }

    public void setPhonePrice(float phonePrice) {
        this.phonePrice = phonePrice;
    }

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public int getPhoneSold() {
        return phoneSold;
    }

    public void setPhoneSold(int phoneSold) {
        this.phoneSold = phoneSold;
    }

    public String getPhoneDescription() {
        return phoneDescription;
    }

    public void setPhoneDescription(String phoneDescription) {
        this.phoneDescription = phoneDescription;
    }

    public int getPhoneStock() {
        return phoneStock;
    }

    public void setPhoneStock(int phoneStock) {
        this.phoneStock = phoneStock;
    }

    public int getPhoneWarranty() {
        return phoneWarranty;
    }

    public void setPhoneWarranty(int phoneWarranty) {
        this.phoneWarranty = phoneWarranty;
    }

    public int getPhoneInStore() {
        return phoneInStore;
    }

    public void setPhoneInStore(int phoneInStore) {
        this.phoneInStore = phoneInStore;
    }
}

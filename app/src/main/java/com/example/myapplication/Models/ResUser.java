package com.example.myapplication.Models;

import java.util.ArrayList;
import java.util.List;

public class ResUser {

    private String userID;
    private String username;
    private String password;
    private String address;
    private String avatarImg;
    private int starredUser;
    private String phoneNumber;
    private int boughtAmount;
    private int cancelledAmount;
    private int bannedUser;
    private ArrayList<String> vouchersOwned;
    private int vouchersCount;

    public int getVouchersCount() {
        return vouchersCount;
    }

    public void setVouchersCount(int vouchersCount) {
        this.vouchersCount = vouchersCount;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }

    public int getStarredUser() {
        return starredUser;
    }

    public void setStarredUser(int starredUser) {
        this.starredUser = starredUser;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getBoughtAmount() {
        return boughtAmount;
    }

    public void setBoughtAmount(int boughtAmount) {
        this.boughtAmount = boughtAmount;
    }

    public int getCancelledAmount() {
        return cancelledAmount;
    }

    public void setCancelledAmount(int cancelledAmount) {
        this.cancelledAmount = cancelledAmount;
    }

    public int getBannedUser() {
        return bannedUser;
    }

    public void setBannedUser(int bannedUser) {
        this.bannedUser = bannedUser;
    }

    public ArrayList<String> getVouchersOwned() {
        return vouchersOwned;
    }

    public void setVouchersOwned(ArrayList<String> vouchersOwned) {
        this.vouchersOwned = vouchersOwned;
    }
}

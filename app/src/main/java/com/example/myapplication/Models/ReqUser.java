package com.example.myapplication.Models;

import java.util.ArrayList;

public class ReqUser {
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

    // For editing user information in the user app
    public ReqUser(String userID, String username, String password, String address, String avatarImg, String phoneNumber) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.address = address;
        this.avatarImg = avatarImg;
        this.phoneNumber = phoneNumber;
    }

    public ReqUser(String userID, String username, String password, String address, String avatarImg, int starredUser, String phoneNumber, int boughtAmount, int cancelledAmount, int bannedUser, ArrayList<String> vouchersOwned) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.address = address;
        this.avatarImg = avatarImg;
        this.starredUser = starredUser;
        this.phoneNumber = phoneNumber;
        this.boughtAmount = boughtAmount;
        this.cancelledAmount = cancelledAmount;
        this.bannedUser = bannedUser;
        this.vouchersOwned = vouchersOwned;
    }

    // For updating user info in the user app
    public ReqUser(String userID, String username, String password, String address, String avatarImg) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.address = address;
        this.avatarImg = avatarImg;
    }

    // For updating the user info by the system
    public ReqUser(String userID, int boughtAmount, int cancelledAmount, ArrayList<String> vouchersOwned) {
        this.userID = userID;
        this.boughtAmount = boughtAmount;
        this.cancelledAmount = cancelledAmount;
        this.vouchersOwned = vouchersOwned;
    }

    // For updating the user info in the admin app, mainly to star or ban a user
    public ReqUser(String userID, int starredUser, int bannedUser) {
        this.userID = userID;
        this.starredUser = starredUser;
        this.bannedUser = bannedUser;
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

package com.example.myapplication.Models;
import java.util.List;

public class ReqEditUser {
    private String userID;
    private String username;
    private String password;
    private String address;
    private int starredUser;
    private String phoneNumber;
    private int boughtAmount;
    private int cancelledAmount;
    private int bannedUser;
    private List<VoucherOwned> vouchersOwned;

    public ReqEditUser(String userID, String username, String password, String address, int starredUser, String phoneNumber, int boughtAmount, int cancelledAmount, int bannedUser, List<VoucherOwned> vouchersOwned) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.address = address;
        this.starredUser = starredUser;
        this.phoneNumber = phoneNumber;
        this.boughtAmount = boughtAmount;
        this.cancelledAmount = cancelledAmount;
        this.bannedUser = bannedUser;
        this.vouchersOwned = vouchersOwned;
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

    public List<VoucherOwned> getVouchersOwned() {
        return vouchersOwned;
    }

    public void setVouchersOwned(List<VoucherOwned> vouchersOwned) {
        this.vouchersOwned = vouchersOwned;
    }
}

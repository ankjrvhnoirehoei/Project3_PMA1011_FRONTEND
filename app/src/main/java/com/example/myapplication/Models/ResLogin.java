package com.example.myapplication.Models;

public class ResLogin {
    private String status;
    private String message;
    private String token;
    private String refreshToken;
    private String userID;

    public ResLogin() {
    }

    public ResLogin(String status, String message, String token, String refreshToken, String userID) {
        this.status = status;
        this.message = message;
        this.token = token;
        this.refreshToken = refreshToken;
        this.userID = userID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}

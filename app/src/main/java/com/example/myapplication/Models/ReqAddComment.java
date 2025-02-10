package com.example.myapplication.Models;

public class ReqAddComment {

    private String commentText;
    private String userID;
    private String phoneID;

    public ReqAddComment(String commentText, String userID, String phoneID) {
        this.commentText = commentText;
        this.userID = userID;
        this.phoneID = phoneID;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(String phoneID) {
        this.phoneID = phoneID;
    }
}

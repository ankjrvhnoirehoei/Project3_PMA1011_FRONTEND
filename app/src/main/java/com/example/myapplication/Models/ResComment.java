package com.example.myapplication.Models;

public class ResComment {
    private String commentID;
    private String commentText;
    private String dateCreated;
    private int deleted;
    private String userID;
    private String phoneID;

    public ResComment(String commentID, String commentText, String dateCreated, int deleted, String userID, String phoneID) {
        this.commentID = commentID;
        this.commentText = commentText;
        this.dateCreated = dateCreated;
        this.deleted = deleted;
        this.userID = userID;
        this.phoneID = phoneID;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
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

package com.example.myapplication.Models;

import java.util.ArrayList;

public class ResComment {
    private int totalComment;
    private ArrayList<Comments> comments;

    public ResComment(int totalComment, ArrayList<Comments> comments) {
        this.totalComment = totalComment;
        this.comments = comments;
    }

    public int getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
    }

    public ArrayList<Comments> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comments> comments) {
        this.comments = comments;
    }
}

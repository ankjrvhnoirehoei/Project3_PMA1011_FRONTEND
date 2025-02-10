package com.example.myapplication.Models;

import java.util.ArrayList;

public class ResRating {
    private float averageRating;
    private ArrayList<Ratings> ratings;

    public ResRating(float averageRating, ArrayList<Ratings> ratings) {
        this.averageRating = averageRating;
        this.ratings = ratings;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public ArrayList<Ratings> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<Ratings> ratings) {
        this.ratings = ratings;
    }
}

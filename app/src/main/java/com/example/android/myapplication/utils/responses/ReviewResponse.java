package com.example.android.myapplication.utils.responses;

import com.example.android.myapplication.models.Movie;
import com.example.android.myapplication.models.Review;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {

    @SerializedName("results")
    @Expose()
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return "ReviewResponse{" +
                "reviews=" + reviews +
                '}';
    }
}

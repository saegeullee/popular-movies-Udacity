package com.example.android.myapplication.utils.responses;

import com.example.android.myapplication.models.MovieTrailer;
import com.example.android.myapplication.models.Review;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse {

    @SerializedName("results")
    @Expose()
    private List<MovieTrailer> trailerList;

    public List<MovieTrailer> getTrailerList() {
        return trailerList;
    }

    @Override
    public String toString() {
        return "TrailerResponse{" +
                "trailerList=" + trailerList +
                '}';
    }
}

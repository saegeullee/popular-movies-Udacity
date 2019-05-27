package com.example.android.myapplication.utils;

import com.example.android.myapplication.Constants;
import com.example.android.myapplication.utils.responses.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApi {

    //top_rated
    @GET(Constants.MOVIE_TOP_RATED_URL)
    Call<MovieResponse> getMovieByTopRated(
            @Query("api_key") String api_key
    );

    //popular
    @GET(Constants.MOVIE_POPULAR_URL)
    Call<MovieResponse> getMovieByPopularity(
            @Query("api_key") String api_key
    );
}

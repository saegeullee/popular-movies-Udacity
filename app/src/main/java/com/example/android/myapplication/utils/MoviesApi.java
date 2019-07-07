package com.example.android.myapplication.utils;

import com.example.android.myapplication.Constants;
import com.example.android.myapplication.utils.responses.MovieResponse;
import com.example.android.myapplication.utils.responses.ReviewResponse;
import com.example.android.myapplication.utils.responses.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
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

    //review
    @GET("{movie_id}/" + Constants.MOVIE_REVIEW_URL)
    Call<ReviewResponse> getMovieReviews(
            @Path("movie_id") String movie_id,
            @Query("api_key") String api_key
    );

    @GET("{movie_id}/" + Constants.MOVIE_VIDEOS_URL)
    Call<TrailerResponse> getMovieTrailers(
            @Path("movie_id") String movie_id,
            @Query("api_key") String api_key
    );


}

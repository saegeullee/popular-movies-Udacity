package com.example.android.myapplication.requests;

import com.example.android.myapplication.Constants;
import com.example.android.myapplication.utils.MoviesApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRequestGenerator {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();
    private static MoviesApi moviesApi = retrofit.create(MoviesApi.class);

    public static MoviesApi getMoviesApi() {
        return moviesApi;
    }
}

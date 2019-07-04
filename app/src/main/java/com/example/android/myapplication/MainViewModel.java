package com.example.android.myapplication;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.myapplication.models.Movie;
import com.example.android.myapplication.requests.MovieDataSourceClient;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> lists;
    private static final String TAG = "MainViewModel";

    public MainViewModel(@NonNull Application application) {
        super(application);

        Log.d(TAG, "Actively retrieving the favorite movie lists from the Database");
        MovieDataSourceClient.getInstance().setContext(application);

    }

    public LiveData<List<Movie>> getMovieLists() {
        return MovieDataSourceClient.getInstance().getMovieList();
    }

    public void getMoviesList(String queryMethod) {
        Log.d(TAG, "Actively retrieving the movie lists from the Api");
        MovieDataSourceClient.getInstance().getMovies(queryMethod);
    }


    public LiveData<List<Movie>> getMovieListsForTest() {
        return MovieDataSourceClient.getInstance().getTestMovieList();
    }

    public void getMoviesListTest() {
        MovieDataSourceClient.getInstance().getMovieListForTest();
    }
}

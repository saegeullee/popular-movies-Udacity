package com.example.android.myapplication;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.myapplication.models.Movie;
import com.example.android.myapplication.models.Review;
import com.example.android.myapplication.requests.MovieRepository;

import java.nio.file.Path;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> lists;
    private static final String TAG = "MainViewModel";

    public MainViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "MainViewModel: in");

        MovieRepository.getInstance().setContext(application);
        MovieRepository.getInstance().setDatabase();
        getMoviesList("popular");
    }

    public LiveData<List<Movie>> movieListObserver() {
        lists = MovieRepository.getInstance().getMovieList();
        return lists;
    }

    public void getMoviesList(String queryMethod) {
        Log.d(TAG, "getMoviesList: in");
        MovieRepository.getInstance().getMoviesFromSource(queryMethod);
    }

}

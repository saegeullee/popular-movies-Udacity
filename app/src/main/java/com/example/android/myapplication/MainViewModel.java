package com.example.android.myapplication;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.example.android.myapplication.models.Movie;
import com.example.android.myapplication.requests.MovieDataSourceClient;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> lists;
    private static final String TAG = "MainViewModel";

    public MainViewModel(@NonNull Application application) {
        super(application);

        MovieDataSourceClient.getInstance().setContext(application);
        getMoviesList("popular");
    }

    public LiveData<List<Movie>> movieListObserver() {
        lists = MovieDataSourceClient.getInstance().getMovieList();
        return lists;
    }

    public void getMoviesList(String queryMethod) {
        MovieDataSourceClient.getInstance().getMovies(queryMethod);
    }


    public LiveData<Movie> movieObserver() {
        return MovieDataSourceClient.getInstance().getMovie();
    }

    public void getMovieByTitle(String movieTitle) {
        MovieDataSourceClient.getInstance().getMovieByTitle(movieTitle);

    }


    public void insertFavoriteMovie(final Movie movie) {

        MovieDataSourceClient.getInstance().insertFavoriteMovie(movie);

    }

    public void unFavoriteMovie(final Movie movie) {

        MovieDataSourceClient.getInstance().unFavoriteMovie(movie);
    }

    public LiveData<List<Movie>> movieListObserveTester() {
        return MovieDataSourceClient.getInstance().getTestMovieList();
    }

    public void getMoviesListTest() {
        MovieDataSourceClient.getInstance().getMovieListForTest();
    }

}

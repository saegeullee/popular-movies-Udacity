package com.example.android.myapplication;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.example.android.myapplication.models.Movie;
import com.example.android.myapplication.requests.MovieRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> lists;
    private static final String TAG = "MainViewModel";

    public MainViewModel(@NonNull Application application) {
        super(application);

        MovieRepository.getInstance().setContext(application);
        getMoviesList("popular");
    }

    public LiveData<List<Movie>> movieListObserver() {
        lists = MovieRepository.getInstance().getMovieList();
        return lists;
    }

    public void getMoviesList(String queryMethod) {
        MovieRepository.getInstance().getMovies(queryMethod);
    }


    public LiveData<Movie> movieObserver() {
        return MovieRepository.getInstance().getMovie();
    }

    public void getMovieByTitle(String movieTitle) {
        MovieRepository.getInstance().getMovieByTitle(movieTitle);

    }


    public void insertFavoriteMovie(final Movie movie) {

        MovieRepository.getInstance().insertFavoriteMovie(movie);

    }

    public void unFavoriteMovie(final Movie movie) {

        MovieRepository.getInstance().unFavoriteMovie(movie);
    }

    public LiveData<List<Movie>> movieListObserveTester() {
        return MovieRepository.getInstance().getTestMovieList();
    }

    public void getMoviesListTest() {
        MovieRepository.getInstance().getMovieListForTest();
    }

}

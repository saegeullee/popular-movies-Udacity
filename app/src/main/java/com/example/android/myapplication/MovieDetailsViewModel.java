package com.example.android.myapplication;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.myapplication.models.Movie;
import com.example.android.myapplication.models.MovieTrailer;
import com.example.android.myapplication.models.Review;
import com.example.android.myapplication.requests.MovieRepository;

import java.util.List;

public class MovieDetailsViewModel extends AndroidViewModel {

    private LiveData<List<Review>> reviews;
    private LiveData<List<MovieTrailer>> trailers;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Review>> movieReviewsObserver() {
        reviews = MovieRepository.getInstance().getReviews();
        return reviews;
    }

    public void getReviews(String movieId) {
        MovieRepository.getInstance().getReviewsFromSource(movieId);
    }

    public LiveData<List<MovieTrailer>> movieTrailersObserver() {
        trailers = MovieRepository.getInstance().getTrailers();
        return trailers;
    }

    public void getMovieTrailers(String movieId) {
        MovieRepository.getInstance().getMovieTrailersFromSource(movieId);
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

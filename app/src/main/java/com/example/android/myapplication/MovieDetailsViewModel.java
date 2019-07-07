package com.example.android.myapplication;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

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

}

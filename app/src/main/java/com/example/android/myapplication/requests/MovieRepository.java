package com.example.android.myapplication.requests;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.example.android.myapplication.AppExecutors;
import com.example.android.myapplication.Constants;
import com.example.android.myapplication.database.AppDatabase;
import com.example.android.myapplication.models.Movie;
import com.example.android.myapplication.models.MovieTrailer;
import com.example.android.myapplication.models.Review;
import com.example.android.myapplication.utils.responses.MovieResponse;
import com.example.android.myapplication.utils.responses.ReviewResponse;
import com.example.android.myapplication.utils.responses.TrailerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static final String TAG = "MovieRepository";

    MutableLiveData<List<Movie>> mMovieList;
    MutableLiveData<List<Movie>> movieListForTest;
    MutableLiveData<List<Review>> mMovieReviews;
    MutableLiveData<List<MovieTrailer>> mMovieTrailers;
    MutableLiveData<Movie> mMovie;

    private static MovieRepository instance;
    private AppDatabase mDatabase;
    private Context mContext;

    public static MovieRepository getInstance() {
        if(instance == null) {
            instance = new MovieRepository();
        }

        return instance;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setDatabase() {
        if(mContext != null) {
            mDatabase = AppDatabase.getInstance(mContext);
            Log.d(TAG, "MovieRepository: DB created");
        } else {
            Log.d(TAG, "MovieRepository: DB is not instantiated");
        }
    }

    public MovieRepository() {
        mMovieList = new MutableLiveData<>();
        movieListForTest = new MutableLiveData<>();
        mMovie = new MutableLiveData<>();
        mMovieReviews = new MutableLiveData<>();
        mMovieTrailers = new MutableLiveData<>();
    }

    public void getMoviesFromSource(String queryMethod) {

        Log.d(TAG, "getMoviesFromSource: in");
        Log.d(TAG, "getMoviesFromSource: querymethod : " + queryMethod);

        if(queryMethod.equals("top_rated")) {

            getMovieByTopRated().enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                    Log.d(TAG, "onResponse: in");
                    List<Movie> movieList = response.body().getMovies();
                    mMovieList.postValue(movieList);
                    Log.d(TAG, "Actively retrieving the movie top rated lists from the Api");

                    if(movieList != null)
                        Log.d(TAG, "onResponse: movies : " + mMovieList.toString());
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            });

        } else if(queryMethod.equals("popular")) {

            getMovieByPopularity().enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                    Log.d(TAG, "onResponse: in");
                    List<Movie> movieList = response.body().getMovies();
                    Log.d(TAG, "onResponse: movieList " + movieList);
                    mMovieList.postValue(movieList);
                    Log.d(TAG, "Actively retrieving the movie popular lists from the Api");


                    if(movieList != null)
                        Log.d(TAG, "onResponse: movies : " + movieList.toString());
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());

                }
            });

        } else if(queryMethod.equals("favorites")) {

            queryDatabaseLoadAllFavoriteMovies();

        }
    }

    public void getMovieTrailersFromSource(String movieId) {


        getMovieTrailers(movieId).enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                List<MovieTrailer> trailerList = response.body().getTrailerList();
                Log.d(TAG, "onResponse: trailerList : " + trailerList);
                Log.d(TAG, "onResponse: trailerList size : " + trailerList.size());

                mMovieTrailers.postValue(trailerList);
                Log.d(TAG, "Actively retrieving the movie trailers from the Api");

            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void getReviewsFromSource(String movieId) {

        getMovieReviews(movieId).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                Log.d(TAG, "onResponse: in");
                List<Review> reviewList = response.body().getReviews();
                Log.d(TAG, "onResponse: number of reviews : " + reviewList.size());
                Log.d(TAG, "onResponse: reviewList : " + reviewList);
                mMovieReviews.postValue(reviewList);
                Log.d(TAG, "Actively retrieving the movie reviews from the Api");

            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public LiveData<List<MovieTrailer>> getTrailers() {
        return mMovieTrailers;
    }

    public LiveData<List<Review>> getReviews() {
        return mMovieReviews;
    }

    public LiveData<Movie> getMovie() {
        return mMovie;
    }

    public void getMovieByTitle(final String movieTitle) {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Movie movie = mDatabase.favoriteMovieDao().getMovieByTitle(movieTitle);
                mMovie.postValue(movie);

            }
        });
    }

    public void insertFavoriteMovie(final Movie movie) {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.favoriteMovieDao().insertFavoriteMovie(movie);
            }
        });
    }

    public void unFavoriteMovie(final Movie movie) {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.favoriteMovieDao().deleteFavoriteMovie(movie);
            }
        });

        queryDatabaseLoadAllFavoriteMovies();

    }

    public void queryDatabaseLoadAllFavoriteMovies() {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Movie> movieList = mDatabase.favoriteMovieDao().loadAllFavoriteMovies();
                Log.d(TAG, "Actively retrieving the favorite movie lists from the Room database");
                mMovieList.postValue(movieList);
            }
        });
    }


    public void getMovieListForTest() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Movie> movieList = mDatabase.favoriteMovieDao().loadAllFavoriteMovies();
                movieListForTest.postValue(movieList);
            }
        });
    }

    public LiveData<List<Movie>> getTestMovieList() {
        return movieListForTest;
    }

    public LiveData<List<Movie>> getMovieList() {
        return mMovieList;
    }

    private Call<MovieResponse> getMovieByTopRated() {
        return NetworkRequestGenerator.getMoviesApi().getMovieByTopRated(
                Constants.API_KEY
        );
    }

    private Call<MovieResponse> getMovieByPopularity() {
        return NetworkRequestGenerator.getMoviesApi().getMovieByPopularity(
                Constants.API_KEY
        );
    }

    private Call<ReviewResponse> getMovieReviews(String movieId) {
        return NetworkRequestGenerator.getMoviesApi().getMovieReviews(
                movieId, Constants.API_KEY);
    }

    private Call<TrailerResponse> getMovieTrailers(String movieId) {
        return NetworkRequestGenerator.getMoviesApi().getMovieTrailers(
                movieId, Constants.API_KEY);
    }
}

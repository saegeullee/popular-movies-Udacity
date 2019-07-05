package com.example.android.myapplication.requests;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.example.android.myapplication.AppExecutors;
import com.example.android.myapplication.Constants;
import com.example.android.myapplication.database.AppDatabase;
import com.example.android.myapplication.models.Movie;
import com.example.android.myapplication.utils.responses.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static final String TAG = "MovieRepository";

    MutableLiveData<List<Movie>> mMovieList;
    MutableLiveData<List<Movie>> movieListForTest;
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

    public MovieRepository() {
        mMovieList = new MutableLiveData<>();
        movieListForTest = new MutableLiveData<>();
        mMovie = new MutableLiveData<>();
        mDatabase = AppDatabase.getInstance(mContext);
    }

    public void getMovies(String queryMethod) {

        Log.d(TAG, "getMovies: in");
        Log.d(TAG, "getMovies: querymethod : " + queryMethod);

        if(queryMethod.equals("top_rated")) {

            getMovieByTopRated().enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                    Log.d(TAG, "onResponse: in");
                    List<Movie> movieList = response.body().getMovies();
                    mMovieList.postValue(movieList);
                    Log.d(TAG, "Actively retrieving the movie lists from the Api");

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
                    Log.d(TAG, "Actively retrieving the movie lists from the Api");


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
}

package com.example.android.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.myapplication.models.Movie;
import com.example.android.myapplication.requests.NetworkRequestGenerator;
import com.example.android.myapplication.utils.MoviesApiJsonUtils;
import com.example.android.myapplication.utils.NetworkUtils;
import com.example.android.myapplication.utils.responses.MovieResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements MovieThumbnailAdapter.OnItemClickListener {

    @Override
    public void onItemClicked(int position) {

        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        intent.putExtra(getString(R.string.movie), posterList.get(position));
        startActivity(intent);

    }

    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private MovieThumbnailAdapter mAdapter;

    private List<Movie> posterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        getMoviePoster("popular");
    }

    private void initUI() {

        mRecyclerView = findViewById(R.id.rv_movie_thumbnail);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieThumbnailAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        posterList = new ArrayList<>();

    }

    private void getMoviePoster(String queryMethod) {

        Log.d(TAG, "getMoviePoster: in");

        if(queryMethod.equals("top_rated")) {

            getMovieByTopRated().enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                    List<Movie> movies = response.body().getMovies();
                    posterList = movies;
                    mAdapter.setPosterListData(posterList);

                    if(movies != null)
                        Log.d(TAG, "onResponse: movies : " + movies.toString());
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });

        } else if(queryMethod.equals("popular")) {

            getMovieByPopularity().enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    List<Movie> movies = response.body().getMovies();
                    posterList = movies;
                    mAdapter.setPosterListData(posterList);

                    if(movies != null)
                        Log.d(TAG, "onResponse: movies : " + movies.toString());
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });
        }


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular: {
                getMoviePoster("popular");

                return true;
            }
            case R.id.top_rated: {
                getMoviePoster("top_rated");

                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

}

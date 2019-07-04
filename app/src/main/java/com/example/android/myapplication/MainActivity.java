package com.example.android.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.myapplication.database.AppDatabase;
import com.example.android.myapplication.database.FavoriteMovieEntry;
import com.example.android.myapplication.models.Movie;
import com.example.android.myapplication.requests.NetworkRequestGenerator;
import com.example.android.myapplication.utils.responses.MovieResponse;

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

    private AppDatabase mDatabase;
    private MainViewModel mainViewModel;

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

        mDatabase = AppDatabase.getInstance(getApplicationContext());

        setupViewMainModel();

    }

    private void getMoviePoster(String queryMethod) {

        Log.d(TAG, "getMoviePoster: in");

        mainViewModel.getMoviesList(queryMethod);

    }

    private void setupViewMainModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel.getMovieLists().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                Log.d(TAG, "onChanged: Retrieving database update from LiveData in ViewModel");
                posterList = movies;
                mAdapter.setPosterListData(posterList);

            }
        });
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

            case R.id.favorites: {
                getMoviePoster("favorites");
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

}

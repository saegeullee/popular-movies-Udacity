package com.example.android.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.myapplication.database.AppDatabase;
import com.example.android.myapplication.models.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * 2019-07-07 7:34pm
 * 현재 문제점 : 메인 액티비티에서 top rated 의 영화 목록에서 하나의 영화를 클릭후 다시 메인액티비티로
 * 되돌아오면 popular 영화 목록으로 바뀐다.
 *
 * -> 문제의 원인 MovieDetailsActivity 로 이동하면 여기서 mainViewModel 이 생성되면서
 * mainViewModel 생성자의 getMoviesList("popular"); 메서드를 호출하기 때문이다.
 *
 * -> 문제 해결 방법 : MovieDetailsActivity 에서는 movieDetailsViewModel 만 호출되도록 하면 된다.
 */

public class MainActivity extends AppCompatActivity
        implements MovieThumbnailAdapter.OnItemClickListener{

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
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: in");

        setContentView(R.layout.activity_main);

        initUI();

    }

    private void initUI() {

        mRecyclerView = findViewById(R.id.rv_movie_thumbnail);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieThumbnailAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        posterList = new ArrayList<>();

        setupViewMainModel();
    }

    private void getMoviePoster(String queryMethod) {

        Log.d(TAG, "getMoviePoster: in");

        mainViewModel.getMoviesList(queryMethod);

    }

    private void setupViewMainModel() {

        Log.d(TAG, "setupViewMainModel: in ");

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel.movieListObserver().observe(this, new Observer<List<Movie>>() {
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

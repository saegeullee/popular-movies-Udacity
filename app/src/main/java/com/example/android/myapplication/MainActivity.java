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
import com.example.android.myapplication.utils.MoviesApiJsonUtils;
import com.example.android.myapplication.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        new getMovieAsyncTask().execute(queryMethod);

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

    public class getMovieAsyncTask extends AsyncTask<String, Void, List<Movie>> {

        /**
         * TODO question1
         * when should I leave or delete super. method ?
         * how do I know it ?
         */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Movie> doInBackground(String... strings) {

            Log.d(TAG, "doInBackground: in");

            String queryMethod = strings[0];
            Log.d(TAG, "doInBackground: queryMethod : " + queryMethod);

            if(queryMethod.equals("popular")) {
                try {
                    String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(Constants.MOVIE_POPULAR);
                    Log.d(TAG, "doInBackground: jsonMovieResponse + " + jsonMovieResponse);

                    posterList = MoviesApiJsonUtils.getPosterListFromJson(jsonMovieResponse);

                    return posterList;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(queryMethod.equals("top_rated")) {
                try {
                    String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(Constants.MOVIE_TOP_RATED);
                    Log.d(TAG, "doInBackground: jsonMovieResponse + " + jsonMovieResponse);

                    posterList = MoviesApiJsonUtils.getPosterListFromJson(jsonMovieResponse);

                    return posterList;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> posters) {

            Log.d(TAG, "onPostExecute: in");

            if(posters != null)
                mAdapter.setPosterListData(posters);
        }
    }
}

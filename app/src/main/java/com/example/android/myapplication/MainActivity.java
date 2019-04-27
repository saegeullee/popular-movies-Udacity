package com.example.android.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

    private List<Poster> posterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        getMoviePoster();
    }

    private void initUI() {

        mRecyclerView = findViewById(R.id.rv_movie_thumbnail);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieThumbnailAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        posterList = new ArrayList<>();

    }

    private void getMoviePoster() {

        Log.d(TAG, "getMoviePoster: in");
        new getMovieAsyncTask().execute();

    }

    public class getMovieAsyncTask extends AsyncTask<Void, Void, List<Poster>> {

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
        protected List<Poster> doInBackground(Void... voids) {

            Log.d(TAG, "doInBackground: in");

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(Constants.MOVIE_POPULAR);
                Log.d(TAG, "doInBackground: jsonMovieResponse + " + jsonMovieResponse);

                posterList = MoviesApiJsonUtils.getPosterListFromJson(jsonMovieResponse);

                return posterList;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Poster> posters) {

            Log.d(TAG, "onPostExecute: in");

            if(posters != null)
                mAdapter.setPosterListData(posters);
        }
    }
}

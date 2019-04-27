package com.example.android.myapplication;

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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private MovieThumbnailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        getMoviePoster();
    }

    private void initUI() {

        mRecyclerView = findViewById(R.id.rv_movie_thumbnail);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieThumbnailAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void getMoviePoster() {

        Log.d(TAG, "getMoviePoster: in");
        new getMovieAsyncTask().execute();

    }

    public class getMovieAsyncTask extends AsyncTask<Void, Void, List<Poster>> {

        /**
         * Question
         * when should I leave or delete super. method ?
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
                
                List<Poster> posterList;
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

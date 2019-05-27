package com.example.android.myapplication.utils;

import android.util.Log;

import com.example.android.myapplication.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoviesApiJsonUtils {

    private static final String TAG = "MoviesApiJsonUtils";

    public static List<Movie> getPosterListFromJson(String posterListJsonStr) {


        try {
            JSONObject originalJsonObject = new JSONObject(posterListJsonStr);

            JSONArray resultsArray = originalJsonObject.getJSONArray("results");

            List<Movie> posterList = new ArrayList<>();

            for(int i = 0; i < resultsArray.length(); i++) {

                JSONObject movieObject = resultsArray.getJSONObject(i);

                Movie movie = new Movie();
                movie.setOriginal_title(movieObject.getString("title"));
                movie.setOverview(movieObject.getString("overview"));
                movie.setRelease_date(movieObject.getString("release_date"));
                movie.setPoster_path(movieObject.getString("poster_path"));
                movie.setVote_average(movieObject.getDouble("vote_average"));

                Log.d(TAG, "getPosterListFromJson: poster : " + movie.toString());

                posterList.add(movie);

            }

            Log.d(TAG, "getPosterListFromJson: posterList size : " + posterList.size());

            return posterList;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}

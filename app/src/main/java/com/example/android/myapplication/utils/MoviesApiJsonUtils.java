package com.example.android.myapplication.utils;

import android.util.Log;

import com.example.android.myapplication.Poster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoviesApiJsonUtils {

    private static final String TAG = "MoviesApiJsonUtils";

    public static List<Poster> getPosterListFromJson(String posterListJsonStr) {


        try {
            JSONObject originalJsonObject = new JSONObject(posterListJsonStr);

            JSONArray resultsArray = originalJsonObject.getJSONArray("results");

            List<Poster> posterList = new ArrayList<>();

            for(int i = 0; i < resultsArray.length(); i++) {

                JSONObject movieObject = resultsArray.getJSONObject(i);

                Poster poster = new Poster();
                poster.setOriginal_title(movieObject.getString("title"));
                poster.setPlot_synopsis(movieObject.getString("overview"));
                poster.setRelease_date(movieObject.getString("release_date"));
                poster.setThumbnail(movieObject.getString("poster_path"));
                poster.setUser_rating(movieObject.getDouble("vote_average"));

                Log.d(TAG, "getPosterListFromJson: poster : " + poster.toString());

                posterList.add(poster);

            }

            Log.d(TAG, "getPosterListFromJson: posterList size : " + posterList.size());

            return posterList;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}

package com.example.android.myapplication.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = "NetworkUtils";

    public static String getResponseFromHttpUrl(String urlString) throws IOException {

        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(urlString);

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            if(scanner.hasNext()) {
                return scanner.next();
            } else {
                return null;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
        }

        return null;
    }

}

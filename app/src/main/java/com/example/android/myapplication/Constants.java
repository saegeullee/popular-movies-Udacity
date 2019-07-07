package com.example.android.myapplication;

public class Constants {

    public final static String BASE_URL = "https://api.themoviedb.org";
    public final static String MOVIE_URL = BASE_URL + "/3/movie/";
    public final static String API_PARAM = "?api_key=";
    public final static String API_KEY = "";

    public final static String MOVIE_TOP_RATED_URL = "top_rated";
    public final static String MOVIE_POPULAR_URL = "popular";
    public final static String MOVIE_REVIEW_URL = "reviews";
    public final static String MOVIE_VIDEOS_URL = "videos";

    public final static String YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v=";



    public final static String MOVIE_TOP_RATED = MOVIE_URL + "/top_rated" + API_PARAM + API_KEY;
    public final static String MOVIE_POPULAR = MOVIE_URL + "/popular" + API_PARAM + API_KEY;

    public final static String MOVIE_POSTER_IMAGE_PATH = "https://image.tmdb.org/t/p/w185/";

}

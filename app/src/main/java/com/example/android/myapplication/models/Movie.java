package com.example.android.myapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Movie implements Parcelable {

    private String original_title; //overview
    private String thumbnail;
    private String plot_synopsis;
    private double user_rating; // vote_average
    private String release_date;
    private List<VideoTrailer> videoTrailers;
    private List<Review> reviews;

    public Movie() {
    }

    protected Movie(Parcel in) {
        original_title = in.readString();
        thumbnail = in.readString();
        plot_synopsis = in.readString();
        user_rating = in.readDouble();
        release_date = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPlot_synopsis() {
        return plot_synopsis;
    }

    public void setPlot_synopsis(String plot_synopsis) {
        this.plot_synopsis = plot_synopsis;
    }

    public double getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(double user_rating) {
        this.user_rating = user_rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public List<VideoTrailer> getVideoTrailers() {
        return videoTrailers;
    }

    public void setVideoTrailers(List<VideoTrailer> videoTrailers) {
        this.videoTrailers = videoTrailers;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "original_title='" + original_title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", plot_synopsis='" + plot_synopsis + '\'' +
                ", user_rating=" + user_rating +
                ", release_date='" + release_date + '\'' +
                ", videoTrailers=" + videoTrailers +
                ", reviews=" + reviews +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(original_title);
        dest.writeString(thumbnail);
        dest.writeString(plot_synopsis);
        dest.writeDouble(user_rating);
        dest.writeString(release_date);
    }
}

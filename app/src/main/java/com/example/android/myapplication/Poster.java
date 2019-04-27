package com.example.android.myapplication;

public class Poster {

    private String original_title; //overview
    private String thumbnail;
    private String plot_synopsis;
    private double user_rating; // vote_average
    private String release_date;

    public Poster() {
    }

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

    @Override
    public String toString() {
        return "Poster{" +
                "original_title='" + original_title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", plot_synopsis='" + plot_synopsis + '\'' +
                ", user_rating=" + user_rating +
                ", release_date='" + release_date + '\'' +
                '}';
    }
}

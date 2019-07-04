package com.example.android.myapplication.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

@Entity(tableName = "Movie")
public class Movie implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String original_title; //overview
    private String poster_path;
    private String overview;
    private double vote_average; // vote_average
    private String release_date;

    @ColumnInfo(name="updated_at")
    private Date updatedAt;

    public Movie() {
    }

    // Use this constructor when add new favorite movie to DB
    @Ignore
    public Movie(String original_title, String poster_path, String overview, double vote_average, String release_date, Date updatedAt) {
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.updatedAt = updatedAt;
    }

    // Use this constructor when read from DB
    public Movie(int id, String original_title, String poster_path, String overview, double vote_average, String release_date, Date updatedAt) {
        this.id = id;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.updatedAt = updatedAt;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        original_title = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        vote_average = in.readDouble();
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

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(original_title);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeDouble(vote_average);
        dest.writeString(release_date);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", original_title='" + original_title + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", overview='" + overview + '\'' +
                ", vote_average=" + vote_average +
                ", release_date='" + release_date + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

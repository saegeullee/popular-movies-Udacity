package com.example.android.myapplication.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.myapplication.models.Movie;

import java.util.List;

@Dao
public interface FavoriteMovieDao {

    @Query("SELECT * FROM Movie ORDER BY updated_at DESC")
    List<Movie> loadAllFavoriteMovies();

    @Query("SELECT * FROM Movie WHERE original_title = :title")
    Movie getMovieByTitle(String title);

    @Insert
    void insertFavoriteMovie(Movie movie);

    @Delete
    void deleteFavoriteMovie(Movie movie);

    @Query("DELETE FROM Movie")
    void  deleteAllData();


}

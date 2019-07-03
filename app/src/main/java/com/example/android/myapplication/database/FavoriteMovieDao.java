package com.example.android.myapplication.database;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import retrofit2.http.DELETE;

public interface FavoriteMovieDao {

    @Query("SELECT * FROM favoriteMovie ORDER BY updated_at DESC")
    List<FavoriteMovieEntry> loadAllFavoriteMovies();

    @Insert
    void insertFavoriteMovie(FavoriteMovieEntry movieEntry);

    @Delete
    void deleteFovoriteMovie(FavoriteMovieEntry movieEntry);


}

package com.example.android.myapplication.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoriteMovieDao {

    @Query("SELECT * FROM favoriteMovie ORDER BY updated_at DESC")
    LiveData<List<FavoriteMovieEntry>> loadAllFavoriteMovies();

    @Query("SELECT * FROM favoriteMovie WHERE original_title = :title")
    FavoriteMovieEntry getMovieByTitle(String title);

    @Insert
    void insertFavoriteMovie(FavoriteMovieEntry movieEntry);

    @Delete
    void deleteFavoriteMovie(FavoriteMovieEntry movieEntry);



    @Query("DELETE FROM favoriteMovie")
    void  deleteAllData();


}

package com.example.android.myapplication.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

@Database(entities = {FavoriteMovieEntry.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
abstract public class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "popular_movies";
    private static AppDatabase dbInstance;

    public static AppDatabase getInstance(Context context) {
        if(dbInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "getInstance: Creating new db instance");
                dbInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }

        return dbInstance;
    }

    public abstract FavoriteMovieDao favoriteMovieDao();

}

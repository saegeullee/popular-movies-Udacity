package com.example.android.myapplication;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.myapplication.database.AppDatabase;
import com.example.android.myapplication.database.FavoriteMovieEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<FavoriteMovieEntry>> lists;

    private static final String TAG = "MainViewModel";

    public MainViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the favorite movie lists from the Database");
        lists = database.favoriteMovieDao().loadAllFavoriteMovies();
    }

    public LiveData<List<FavoriteMovieEntry>> getLists() {
        return lists;
    }
}

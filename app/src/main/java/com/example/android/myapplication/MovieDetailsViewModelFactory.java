package com.example.android.myapplication;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

public class MovieDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private String mMovieId;
    private Context mContext;

    public MovieDetailsViewModelFactory(Context context, String mMovieId) {
        this.mMovieId = mMovieId;
        this.mContext = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieDetailsViewModel(mContext, mMovieId);
    }
}

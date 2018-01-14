package com.regmoraes.popularmovies.presentation.detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.regmoraes.popularmovies.data.model.Movie;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class MovieDetailsViewModelFactory implements ViewModelProvider.Factory {

    private Movie movie;

    MovieDetailsViewModelFactory(Movie movie) {
        this.movie = movie;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel.class)) {
            return (T) new MovieDetailsViewModel(movie);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

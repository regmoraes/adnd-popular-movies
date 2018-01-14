package com.regmoraes.popularmovies.presentation.home;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.regmoraes.popularmovies.domain.PopularMoviesServices;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class MoviesViewModelFactory implements ViewModelProvider.Factory {

    private PopularMoviesServices popularMoviesServices;

    MoviesViewModelFactory(PopularMoviesServices popularMoviesServices) {
        this.popularMoviesServices = popularMoviesServices;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MoviesViewModel.class)) {
            return (T) new MoviesViewModel(popularMoviesServices);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

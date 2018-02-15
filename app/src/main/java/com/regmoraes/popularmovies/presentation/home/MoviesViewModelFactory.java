package com.regmoraes.popularmovies.presentation.home;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.regmoraes.popularmovies.domain.FavoritesServices;
import com.regmoraes.popularmovies.domain.PopularMoviesServices;

import timber.log.Timber;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class MoviesViewModelFactory implements ViewModelProvider.Factory {

    private PopularMoviesServices popularMoviesServices;
    private FavoritesServices favoritesServices;

    MoviesViewModelFactory(PopularMoviesServices popularMoviesServices,
                           FavoritesServices favoritesServices) {
        this.popularMoviesServices = popularMoviesServices;
        this.favoritesServices = favoritesServices;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MoviesViewModel.class)) {

            Timber.d("New ViewModel created");

            return (T) new MoviesViewModel(popularMoviesServices, favoritesServices);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

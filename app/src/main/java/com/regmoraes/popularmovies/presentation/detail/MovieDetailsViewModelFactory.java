package com.regmoraes.popularmovies.presentation.detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.regmoraes.popularmovies.data.model.Movie;
import com.regmoraes.popularmovies.domain.FavoritesServices;
import com.regmoraes.popularmovies.domain.PopularMoviesManager;
import com.regmoraes.popularmovies.domain.PopularMoviesServices;

import timber.log.Timber;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class MovieDetailsViewModelFactory implements ViewModelProvider.Factory {

    private FavoritesServices favoritesMoviesServices;
    private PopularMoviesServices popularMoviesServices;

    MovieDetailsViewModelFactory(FavoritesServices favoritesMoviesServices,
                                 PopularMoviesServices popularMoviesServices) {
        this.favoritesMoviesServices = favoritesMoviesServices;
        this.popularMoviesServices = popularMoviesServices;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel.class)) {

            Timber.d("New ViewModel created");

            return (T) new MovieDetailsViewModel(favoritesMoviesServices, popularMoviesServices);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

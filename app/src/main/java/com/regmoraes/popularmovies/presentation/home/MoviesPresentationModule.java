package com.regmoraes.popularmovies.presentation.home;

import com.regmoraes.popularmovies.domain.FavoritesServices;
import com.regmoraes.popularmovies.domain.PopularMoviesServices;
import com.regmoraes.popularmovies.presentation.PresentationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Module
public final class MoviesPresentationModule {

    @Provides
    @PresentationScope
    public MoviesViewModelFactory providesMoviesViewModelFactory(PopularMoviesServices popularMoviesServices,
                                                                 FavoritesServices favoritesServices) {
        return new MoviesViewModelFactory(popularMoviesServices, favoritesServices);
    }
}

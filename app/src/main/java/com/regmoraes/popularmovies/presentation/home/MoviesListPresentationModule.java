package com.regmoraes.popularmovies.presentation.home;

import com.regmoraes.popularmovies.domain.PopularMoviesServices;
import com.regmoraes.popularmovies.presentation.PresentationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Module
public final class MoviesListPresentationModule {

    @Provides
    @PresentationScope
    public MoviesViewModelFactory providesPresenter(PopularMoviesServices popularMoviesServices) {

        return new MoviesViewModelFactory(popularMoviesServices);
    }
}

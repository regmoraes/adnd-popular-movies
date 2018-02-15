package com.regmoraes.popularmovies.presentation.detail;

import com.regmoraes.popularmovies.domain.FavoritesServices;
import com.regmoraes.popularmovies.domain.PopularMoviesServices;
import com.regmoraes.popularmovies.presentation.PresentationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Module
public class MovieDetailsPresentationModule {

    @Provides
    @PresentationScope
    public MovieDetailsViewModelFactory providesMovieDetailViewModelFactory(FavoritesServices favoritesServices,
                                                                            PopularMoviesServices popularMoviesServices){
        return new MovieDetailsViewModelFactory(favoritesServices, popularMoviesServices);
    }
}

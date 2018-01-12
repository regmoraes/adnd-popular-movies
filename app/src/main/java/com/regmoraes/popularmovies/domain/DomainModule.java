package com.regmoraes.popularmovies.domain;

import com.regmoraes.popularmovies.data.api.ApiModule;
import com.regmoraes.popularmovies.data.api.MoviesDbRestService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Singleton
@Module(includes = {ApiModule.class})
public class DomainModule {

    @Provides
    @Singleton
    public PopularMoviesServices providesPopularMoviesServices(MoviesDbRestService moviesDbRestService) {

        return new PopularMoviesManager(moviesDbRestService);
    }
}

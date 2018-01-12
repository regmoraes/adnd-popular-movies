package com.regmoraes.popularmovies.data.api;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Singleton
@Module
public class ApiModule {

    @Provides
    @Singleton
    public MoviesDbRestService providesMoviesDbRestService(Retrofit retrofit) {
        return retrofit.create(MoviesDbRestService.class);
    }
}

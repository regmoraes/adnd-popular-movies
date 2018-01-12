package com.regmoraes.popularmovies;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Module
final class ApplicationModule {

    private final PopularMoviesApplication mApplication;

    public ApplicationModule(PopularMoviesApplication mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    public Context providesApplicationContext() {
        return mApplication;
    }
}

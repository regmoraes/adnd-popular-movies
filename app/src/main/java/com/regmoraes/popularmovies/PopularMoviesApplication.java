package com.regmoraes.popularmovies;

import android.app.Application;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public final class PopularMoviesApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}

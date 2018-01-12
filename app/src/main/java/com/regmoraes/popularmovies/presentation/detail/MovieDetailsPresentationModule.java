package com.regmoraes.popularmovies.presentation.detail;

import com.regmoraes.popularmovies.presentation.PresentationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Module
public final class MovieDetailsPresentationModule {

    @Provides
    @PresentationScope
    public MovieDetailsContract.Presenter providesPresenter() {

        return new MovieDetailsPresenter();
    }
}

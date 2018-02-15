package com.regmoraes.popularmovies.presentation.home;

import com.regmoraes.popularmovies.presentation.PresentationScope;

import dagger.Subcomponent;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@PresentationScope
@Subcomponent(modules = {MoviesPresentationModule.class})
public interface MoviesComponent {

    void inject(MoviesActivity moviesListActivity);
}

package com.regmoraes.popularmovies.presentation.detail;

import com.regmoraes.popularmovies.presentation.PresentationScope;

import dagger.Subcomponent;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@PresentationScope
@Subcomponent(modules = {MovieDetailsPresentationModule.class})
public interface MoviesDetailsComponent {

    void inject(MovieDetailsActivity movieDetailsActivity);
}

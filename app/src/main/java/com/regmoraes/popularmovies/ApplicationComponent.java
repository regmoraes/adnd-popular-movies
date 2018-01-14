package com.regmoraes.popularmovies;

import com.regmoraes.popularmovies.data.NetworkModule;
import com.regmoraes.popularmovies.domain.DomainModule;
import com.regmoraes.popularmovies.presentation.home.MoviesListComponent;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Singleton
@Component(modules = {ApplicationModule.class, DomainModule.class, NetworkModule.class})
public interface ApplicationComponent {

    MoviesListComponent moviesListComponent();
}

package com.regmoraes.popularmovies.domain;

import com.regmoraes.popularmovies.data.api.MoviesDbRestService;
import com.regmoraes.popularmovies.data.model.Movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class PopularMoviesManager implements PopularMoviesServices {

    private final MoviesDbRestService moviesDbRestService;

    @Inject
    public PopularMoviesManager(MoviesDbRestService moviesDbRestService) {
        this.moviesDbRestService = moviesDbRestService;
    }

    @Override
    public Observable<List<Movie>> findTopRatedMovies() {

        return moviesDbRestService.findTopRatedMovies()
                .map( moviesDbResponse -> moviesDbResponse.results);
    }

    @Override
    public Observable<List<Movie>> findMostPopularMovies() {

        return moviesDbRestService.findMostPopularMovies()
                .map( moviesDbResponse -> moviesDbResponse.results);
    }
}

package com.regmoraes.popularmovies.domain;

import com.regmoraes.popularmovies.data.model.Movie;

import java.util.List;

import io.reactivex.Observable;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public interface PopularMoviesServices {

    Observable<List<Movie>> findTopRatedMovies();

    Observable<List<Movie>> findMostPopularMovies();
}

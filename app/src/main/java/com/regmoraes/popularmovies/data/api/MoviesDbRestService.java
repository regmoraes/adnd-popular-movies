package com.regmoraes.popularmovies.data.api;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public interface MoviesDbRestService {

    @GET("movie/popular")
    Observable<MoviesDbResponse> findMostPopularMovies();

    @GET("movie/top_rated")
    Observable<MoviesDbResponse> findTopRatedMovies();
}

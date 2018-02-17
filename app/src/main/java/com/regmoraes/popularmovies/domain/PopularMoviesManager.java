package com.regmoraes.popularmovies.domain;

import com.regmoraes.popularmovies.data.api.MoviesDbRestService;
import com.regmoraes.popularmovies.data.model.Movie;
import com.regmoraes.popularmovies.data.model.Review;
import com.regmoraes.popularmovies.data.model.Video;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

import static java.util.Collections.emptyList;

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
    public Single<List<Movie>> findTopRatedMovies() {

        return moviesDbRestService.findTopRatedMovies()
                .map( moviesDbResponse -> moviesDbResponse.results);
    }

    @Override
    public Single<List<Movie>> findMostPopularMovies() {

        return moviesDbRestService.findMostPopularMovies()
                .map( moviesDbResponse -> moviesDbResponse.results);
    }

    @Override
    public Single<List<Review>> findReviews(int movieId) {
        
        return moviesDbRestService.findReviews(movieId)
                .map( response -> {

                    if(response.isSuccessful()) {
                        return response.body().reviews;
                    } else {
                        return emptyList();
                    }
                });
    }

    @Override
    public Single<List<Video>> findVideos(int movieId) {

        return moviesDbRestService.findVideos(movieId)
                .map( response -> {

                    if(response.isSuccessful()) {
                        return response.body().videos;
                    } else {
                        return emptyList();
                    }
                });
    }
}

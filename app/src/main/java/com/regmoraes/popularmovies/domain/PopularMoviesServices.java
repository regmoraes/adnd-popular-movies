package com.regmoraes.popularmovies.domain;

import com.regmoraes.popularmovies.data.model.Movie;
import com.regmoraes.popularmovies.data.model.Review;
import com.regmoraes.popularmovies.data.model.Video;

import java.util.List;

import io.reactivex.Single;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public interface PopularMoviesServices {

    Single<List<Movie>> findTopRatedMovies();

    Single<List<Movie>> findMostPopularMovies();

    Single<List<Video>> findVideos(int movieId);

    Single<List<Review>> findReviews(int movieId);
}

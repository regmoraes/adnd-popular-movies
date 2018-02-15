package com.regmoraes.popularmovies.data.api;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public interface MoviesDbRestService {

    @GET("movie/popular")
    Single<MoviesResponse> findMostPopularMovies();

    @GET("movie/top_rated")
    Single<MoviesResponse> findTopRatedMovies();

    @GET("movie/{movie_id}/videos")
    Single<Response<MovieVideosResponse>> findVideos(@Path("movie_id") int movieId);

    @GET("movie/{movie_id}/reviews")
    Single<Response<MovieReviewsResponse>> findReviews(@Path("movie_id") int movieId);
}

package com.regmoraes.popularmovies.domain;

import com.regmoraes.popularmovies.data.model.Movie;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public interface FavoritesServices {

    Single<List<Movie>> findFavoritesMovies();

    Single<Boolean> getFavoriteStatus(int movieId);

    Completable favoriteMovie(Movie movie);

    Completable unFavoriteMovie(int movieId);
}

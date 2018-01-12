package com.regmoraes.popularmovies.presentation.detail;

import com.regmoraes.popularmovies.data.model.Movie;

public final class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private MovieDetailsContract.View view;

    @Override
    public void attachView(MovieDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void loadMovieDetails(Movie movie) {
        view.showMovieDetails(movie);
    }
}

package com.regmoraes.popularmovies.presentation.home;

import com.regmoraes.popularmovies.data.model.Movie;
import com.regmoraes.popularmovies.presentation.BasePresenter;

import java.util.List;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public interface MoviesListContract {

    interface View {

        void showMoviesLoading();

        void showMovies(List<Movie> movies);

        void showMoviesLoadError();

        void showMovieDetails(Movie movie);

        void showSortingOptions();
    }

    interface Presenter extends BasePresenter<View>, MoviesAdapter.MoviesClickListener {

        void loadMovies();

        void onSortByClicked();

        void onSortByRatingClicked();

        void onSortByPopularityClicked();
    }
}

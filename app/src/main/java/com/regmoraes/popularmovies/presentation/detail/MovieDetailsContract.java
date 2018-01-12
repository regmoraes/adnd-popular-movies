package com.regmoraes.popularmovies.presentation.detail;

import com.regmoraes.popularmovies.data.model.Movie;
import com.regmoraes.popularmovies.presentation.BasePresenter;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public interface MovieDetailsContract {

    interface View {

        void showMovieDetails(Movie movie);
    }

    interface Presenter extends BasePresenter<View> {

        void loadMovieDetails(Movie movie);
    }
}

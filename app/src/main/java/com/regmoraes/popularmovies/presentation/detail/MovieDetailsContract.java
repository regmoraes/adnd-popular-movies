package com.regmoraes.popularmovies.presentation.detail;

import com.regmoraes.popularmovies.data.model.Movie;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public interface MovieDetailsContract {

    interface View {

        void showMovieDetails(Movie movie);
    }
}

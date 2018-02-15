package com.regmoraes.popularmovies.presentation.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.regmoraes.popularmovies.commons.Status;
import com.regmoraes.popularmovies.data.model.Movie;

import java.util.List;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public interface MoviesContract {

    interface View {

        void showMoviesLoading();

        void showMovies(List<Movie> movies);

        void showMoviesLoadError();

        void showMovieDetails(Movie movie);
    }

    interface ViewModel extends MoviesAdapter.MoviesClickListener {

        void sortByRating();

        void sortByPopularity();

        void sortByFavorites();
    }

    class MoviesResponse {

        private final Status status;
        private final List<Movie> data;
        private Throwable error;

        public MoviesResponse(@NonNull Status status, @Nullable List<Movie> data, @Nullable Throwable error) {
            this.status = status;
            this.data = data;
            this.error = error;
        }

        public Status getStatus() {
            return status;
        }

        public List<Movie> getData() {
            return data;
        }

        public Throwable getError() {
            return error;
        }
    }
}

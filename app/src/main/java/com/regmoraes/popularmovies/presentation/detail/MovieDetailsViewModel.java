package com.regmoraes.popularmovies.presentation.detail;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.regmoraes.popularmovies.data.model.Movie;

public final class MovieDetailsViewModel extends ViewModel {

    private final MutableLiveData<Movie> mMovie = new MutableLiveData<>();

    public MutableLiveData<Movie> getMovie() {
        return mMovie;
    }

    public MovieDetailsViewModel(Movie movie) {
        mMovie.setValue(movie);
    }
}

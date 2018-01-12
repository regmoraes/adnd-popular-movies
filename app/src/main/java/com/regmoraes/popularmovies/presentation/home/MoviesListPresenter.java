package com.regmoraes.popularmovies.presentation.home;

import com.regmoraes.popularmovies.data.model.Movie;
import com.regmoraes.popularmovies.domain.PopularMoviesServices;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class MoviesListPresenter implements MoviesListContract.Presenter {

    private MoviesListContract.View view;
    private final PopularMoviesServices popularMoviesServices;

    @Inject
    public MoviesListPresenter(PopularMoviesServices popularMoviesServices) {
        this.popularMoviesServices = popularMoviesServices;
    }

    @Override
    public void attachView(MoviesListContract.View view) {
        this.view = view;
    }

    @Override
    public void loadMovies() {

        view.showMoviesLoading();

        popularMoviesServices
                .findMostPopularMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(movies -> view.showMovies(movies), error -> view.showMoviesLoadError());
    }

    @Override
    public void onMovieClicked(Movie movie) {
        view.showMovieDetails(movie);
    }

    @Override
    public void onSortByClicked() {
        view.showSortingOptions();
    }

    @Override
    public void onSortByPopularityClicked() {

        view.showMoviesLoading();

        popularMoviesServices
                .findMostPopularMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(movies -> view.showMovies(movies), error -> view.showMoviesLoadError());
    }

    @Override
    public void onSortByRatingClicked() {

        view.showMoviesLoading();

        popularMoviesServices
                .findTopRatedMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(movies -> view.showMovies(movies), error -> view.showMoviesLoadError());
    }
}

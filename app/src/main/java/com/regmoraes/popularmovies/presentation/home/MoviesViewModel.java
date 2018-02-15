package com.regmoraes.popularmovies.presentation.home;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.regmoraes.popularmovies.commons.Status;
import com.regmoraes.popularmovies.data.model.Movie;
import com.regmoraes.popularmovies.domain.FavoritesServices;
import com.regmoraes.popularmovies.domain.PopularMoviesServices;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class MoviesViewModel extends ViewModel implements MoviesContract.ViewModel {

    private final MutableLiveData<MoviesContract.MoviesResponse> mMoviesResponse = new MutableLiveData<>();
    private final MutableLiveData<Movie> mMovie = new MutableLiveData<>();

    private final CompositeDisposable disposables = new CompositeDisposable();
    private PopularMoviesServices popularMoviesServices;
    private FavoritesServices favoritesServices;

    @Inject
    public MoviesViewModel(PopularMoviesServices popularMoviesServices,
                           FavoritesServices favoritesServices) {
        this.popularMoviesServices = popularMoviesServices;
        this.favoritesServices = favoritesServices;

        sortByRating();
    }

    MutableLiveData<MoviesContract.MoviesResponse> getMoviesResponse() {
        return mMoviesResponse;
    }

    MutableLiveData<Movie> getMovie() {
        return mMovie;
    }

    @Override
    public void sortByRating() {

        disposables.add(popularMoviesServices
                .findTopRatedMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> mMoviesResponse.setValue(new MoviesContract.MoviesResponse(Status.LOADING, null, null)))
                .subscribe(movies -> mMoviesResponse.setValue(new MoviesContract.MoviesResponse(Status.SUCCESS, movies, null)),
                        error -> mMoviesResponse.setValue(new MoviesContract.MoviesResponse(Status.ERROR, null, error))));
    }

    @Override
    public void sortByPopularity() {

        disposables.add(popularMoviesServices
                .findMostPopularMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> mMoviesResponse.setValue(new MoviesContract.MoviesResponse(Status.LOADING, null, null)))
                .subscribe(movies -> mMoviesResponse.setValue(new MoviesContract.MoviesResponse(Status.SUCCESS, movies, null)),
                        error -> mMoviesResponse.setValue(new MoviesContract.MoviesResponse(Status.ERROR, null, error))));
    }

    @Override
    public void sortByFavorites() {

        disposables.add(favoritesServices
                .findFavoritesMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> mMoviesResponse.setValue(new MoviesContract.MoviesResponse(Status.LOADING, null, null)))
                .subscribe(movies -> mMoviesResponse.setValue(new MoviesContract.MoviesResponse(Status.SUCCESS, movies, null)),
                        error -> mMoviesResponse.setValue(new MoviesContract.MoviesResponse(Status.ERROR, null, error))));
    }

    @Override
    public void onMovieClicked(Movie movie) {
        mMovie.setValue(movie);
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}

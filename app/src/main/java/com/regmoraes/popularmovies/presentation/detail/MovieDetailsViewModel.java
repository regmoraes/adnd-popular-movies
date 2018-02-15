package com.regmoraes.popularmovies.presentation.detail;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import com.regmoraes.popularmovies.data.model.Movie;
import com.regmoraes.popularmovies.data.model.Review;
import com.regmoraes.popularmovies.data.model.Video;
import com.regmoraes.popularmovies.domain.FavoritesServices;
import com.regmoraes.popularmovies.domain.PopularMoviesServices;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public final class MovieDetailsViewModel extends ViewModel {

    private Movie movie;
    private FavoritesServices favoritesServices;
    private PopularMoviesServices popularMoviesServices;

    private final MutableLiveData<Movie> observableMovie = new MutableLiveData<>();
    private final MutableLiveData<List<Video>> observableVideos = new MutableLiveData<>();
    private final MutableLiveData<List<Review>> observableReviews = new MutableLiveData<>();
    private final ObservableField<Boolean> fieldIsFavorite = new ObservableField<>();

    public MutableLiveData<Movie> getObservableMovie() {
        return observableMovie;
    }

    public MutableLiveData<List<Video>> getObservableVideos() {
        return observableVideos;
    }

    public MutableLiveData<List<Review>> getObservableReviews() {
        return observableReviews;
    }

    public ObservableField<Boolean> getFieldIsFavorite() {
        return fieldIsFavorite;
    }

    private CompositeDisposable disposables = new CompositeDisposable();

    public MovieDetailsViewModel(FavoritesServices favoritesServices, PopularMoviesServices popularMoviesServices) {
        this.popularMoviesServices = popularMoviesServices;
        this.favoritesServices = favoritesServices;
    }

    public void setMovieData(Movie movie) {
        this.movie = movie;

        observableMovie.setValue(movie);
        getMovieFavoriteStatus();
        getMovieReviews();
        getMovieVideos();
    }

    private void getMovieReviews() {

        if(movie != null) {
            disposables.add(
                    popularMoviesServices.findReviews(movie.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(observableReviews::setValue)
            );
        }
    }

    private void getMovieVideos() {

        if(movie != null) {
            disposables.add(
                    popularMoviesServices.findVideos(movie.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(observableVideos::setValue)
            );
        }
    }

    private void getMovieFavoriteStatus() {

        if(movie != null) {
            disposables.add(
                    favoritesServices.getFavoriteStatus(movie.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(isFavorite -> getFieldIsFavorite().set(isFavorite))
            );
        }
    }

    public void onFavoriteClicked() {

        Boolean isFavorite = fieldIsFavorite.get();

        if(isFavorite != null) {
            if (isFavorite) {
                disposables.add(
                        favoritesServices.unFavoriteMovie(movie.getId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> fieldIsFavorite.set(!isFavorite))
                );

            } else {
                disposables.add(
                        favoritesServices.favoriteMovie(movie)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> fieldIsFavorite.set(!isFavorite))
                );
            }
        }
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }
}

package com.regmoraes.popularmovies.presentation.detail;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.net.Uri;

import com.regmoraes.popularmovies.commons.SingleLiveEvent;
import com.regmoraes.popularmovies.commons.VideoUtils;
import com.regmoraes.popularmovies.data.api.NoInternetException;
import com.regmoraes.popularmovies.data.model.Movie;
import com.regmoraes.popularmovies.data.model.Review;
import com.regmoraes.popularmovies.data.model.Video;
import com.regmoraes.popularmovies.domain.FavoritesServices;
import com.regmoraes.popularmovies.domain.PopularMoviesServices;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public final class MovieDetailsViewModel extends ViewModel implements MovieVideosAdapter.OnVideoClickListener{

    private Movie movie;
    private FavoritesServices favoritesServices;
    private PopularMoviesServices popularMoviesServices;

    private final MutableLiveData<Movie> observableMovie = new MutableLiveData<>();

    private final MutableLiveData<List<Video>> observableVideos = new MutableLiveData<>();
    private final MutableLiveData<Boolean> observableLoadingVideosError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> observableNoVideosAvailable = new MutableLiveData<>();
    private final SingleLiveEvent<Uri> eventShowVideo = new SingleLiveEvent<>();

    private final MutableLiveData<List<Review>> observableReviews = new MutableLiveData<>();
    private final MutableLiveData<Boolean> observableNoReviewsAvailable = new MutableLiveData<>();
    private final MutableLiveData<Boolean> observableLoadingReviewsError = new MutableLiveData<>();

    private final ObservableField<Boolean> fieldIsFavorite = new ObservableField<>();
    private final SingleLiveEvent<Boolean> eventAddedToFavorite = new SingleLiveEvent<>();

    public MutableLiveData<Movie> getObservableMovie() {
        return observableMovie;
    }

    public MutableLiveData<List<Video>> getObservableVideos() {
        return observableVideos;
    }

    public MutableLiveData<Boolean> getObservableLoadingVideosError() {
        return observableLoadingVideosError;
    }

    public MutableLiveData<Boolean> getObservableNoVideosAvailable() {
        return observableNoVideosAvailable;
    }

    public SingleLiveEvent<Uri> getEventShowVideo() {
        return eventShowVideo;
    }

    public MutableLiveData<List<Review>> getObservableReviews() {
        return observableReviews;
    }

    public MutableLiveData<Boolean> getObservableLoadingReviewsError() {
        return observableLoadingReviewsError;
    }

    public MutableLiveData<Boolean> getObservableNoReviewsAvailable() {
        return observableNoReviewsAvailable;
    }

    public ObservableField<Boolean> getFieldIsFavorite() {
        return fieldIsFavorite;
    }

    public SingleLiveEvent<Boolean> getEventAddedToFavorite() {
        return eventAddedToFavorite;
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
                            .subscribe(
                                    reviews -> {
                                        observableReviews.setValue(reviews);
                                        observableNoReviewsAvailable.setValue(reviews.isEmpty());
                                    },
                                    error -> {
                                        if(error instanceof NoInternetException) {
                                            observableReviews.setValue(null);
                                            observableLoadingReviewsError.setValue(true);
                                        }
                                    }
                            )
            );
        }
    }

    private void getMovieVideos() {

        if(movie != null) {
            disposables.add(
                    popularMoviesServices.findVideos(movie.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    videos -> {
                                        observableNoVideosAvailable.setValue(videos.isEmpty());
                                        observableVideos.setValue(videos);
                                    },
                                    error -> {
                                        if(error instanceof NoInternetException) {
                                            observableVideos.setValue(null);
                                            observableLoadingVideosError.setValue(true);
                                        }
                                    }
                            )
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
                                .subscribe(() -> {
                                            fieldIsFavorite.set(false);
                                            eventAddedToFavorite.setValue(false);
                                        }
                                )
                );

            } else {
                disposables.add(
                        favoritesServices.favoriteMovie(movie)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    fieldIsFavorite.set(true);
                                    eventAddedToFavorite.setValue(true);
                                })
                );
            }
        }
    }

    @Override
    public void onVideoClicked(String videoKey) {

        Uri videoUri = VideoUtils.buildVideoUri(videoKey);

        eventShowVideo.setValue(videoUri);
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }
}

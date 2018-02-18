package com.regmoraes.popularmovies.domain;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.regmoraes.popularmovies.data.database.MovieContract;
import com.regmoraes.popularmovies.data.model.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class FavoritesManager implements FavoritesServices {

    private final Context applicationContext;

    @Inject
    public FavoritesManager(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Single<List<Movie>> findFavoritesMovies() {

        return Single.fromCallable(() -> applicationContext.getContentResolver().query(
                MovieContract.FavoriteEntry.CONTENT_URI,
                null,
                null,
                null,
                null))
                .map( cursor -> {

                    List<Movie> movies = new ArrayList<>();

                    if(cursor.getCount() > 0) {
                        cursor.moveToFirst();

                        while (!cursor.isAfterLast()) {

                            Movie movie = new Movie(
                                    cursor.getInt(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_NAME_MOVIE_ID)),
                                    cursor.getFloat(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_NAME_USER_RATING)),
                                    cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_NAME_TITLE)),
                                    cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_NAME_POSTER)),
                                    cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_NAME_ORIGINAL_TITLE)),
                                    cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_NAME_SYNOPSIS)),
                                    cursor.getString(cursor.getColumnIndex(MovieContract.FavoriteEntry.COLUMN_NAME_RELEASE_DATE))
                            );

                            movies.add(movie);
                            cursor.moveToNext();
                        }
                    }

                    cursor.close();

                    return movies;
                });
    }

    @Override
    public Single<Boolean> getFavoriteStatus(int movieId) {

        final String[] COLUMNS_PROJECTION = new String[]{MovieContract.FavoriteEntry.COLUMN_NAME_MOVIE_ID};
        final String SELECT = MovieContract.FavoriteEntry.COLUMN_NAME_MOVIE_ID + " = ? ";
        final String[] SELECT_ARGS = new String[]{String.valueOf(movieId)};

        return Single.fromCallable(() -> applicationContext.getContentResolver().query(
                MovieContract.FavoriteEntry.CONTENT_URI,
                COLUMNS_PROJECTION,
                SELECT,
                SELECT_ARGS,
                null))
                .map(cursor -> cursor != null && cursor.getCount() > 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable favoriteMovie(Movie movie) {

        final ContentValues mValues = new ContentValues();
        mValues.put(MovieContract.FavoriteEntry.COLUMN_NAME_MOVIE_ID, movie.getId());
        mValues.put(MovieContract.FavoriteEntry.COLUMN_NAME_TITLE, movie.getTitle());
        mValues.put(MovieContract.FavoriteEntry.COLUMN_NAME_ORIGINAL_TITLE, movie.getOriginalTitle());
        mValues.put(MovieContract.FavoriteEntry.COLUMN_NAME_POSTER, movie.getPosterPath());
        mValues.put(MovieContract.FavoriteEntry.COLUMN_NAME_RELEASE_DATE, movie.getReleaseDate());
        mValues.put(MovieContract.FavoriteEntry.COLUMN_NAME_SYNOPSIS, movie.getOverview());
        mValues.put(MovieContract.FavoriteEntry.COLUMN_NAME_USER_RATING, movie.getVoteAverage());

        return Single.fromCallable(() -> applicationContext.getContentResolver().insert(
                MovieContract.FavoriteEntry.CONTENT_URI,
                mValues))
                .map(uri -> Integer.valueOf(uri.getLastPathSegment()) > 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toCompletable();
    }

    @Override
    public Completable unFavoriteMovie(int movieId) {

        final Uri uri = MovieContract.FavoriteEntry.CONTENT_URI
                .buildUpon().appendPath(String.valueOf(movieId)).build();

        return Single.fromCallable(() -> applicationContext.getContentResolver().delete(
                uri, null, null))
                .map(rowsDeleted -> rowsDeleted > 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toCompletable();
    }
}

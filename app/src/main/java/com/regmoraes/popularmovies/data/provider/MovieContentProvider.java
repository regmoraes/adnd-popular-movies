package com.regmoraes.popularmovies.data.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.regmoraes.popularmovies.data.database.MovieContract;
import com.regmoraes.popularmovies.data.database.MovieDatabaseHelper;

import static com.regmoraes.popularmovies.data.database.MovieContract.FavoriteEntry.TABLE_NAME;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class MovieContentProvider extends ContentProvider {

    public MovieDatabaseHelper mMovieDbHelper;

    public static final int FAVORITES = 100;
    public static final int FAVORITE_WITH_ID = 101;

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_FAVORITES, FAVORITES);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_FAVORITES_WITH_ID, FAVORITE_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mMovieDbHelper = new MovieDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        if(sUriMatcher.match(uri) == FAVORITES){
            final SQLiteDatabase mDb = mMovieDbHelper.getReadableDatabase();

            Cursor mCursor = mDb.query(TABLE_NAME, projection, selection, selectionArgs,
                    null, null, sortOrder);

            mCursor.setNotificationUri(getContext().getContentResolver(), uri);

            return mCursor;

        } else {
            throw new UnsupportedOperationException("Unknown uri " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        if(sUriMatcher.match(uri) == FAVORITES) {
            final SQLiteDatabase mDb = mMovieDbHelper.getWritableDatabase();

            long _id = mDb.insert(TABLE_NAME, null, values);

            if(_id != -1) {
                return ContentUris.withAppendedId(MovieContract.FavoriteEntry.CONTENT_URI, _id);
            } else {
                throw new SQLException("Failed to insert row into " + uri);
            }

        } else {
            throw new UnsupportedOperationException("Unknown uri " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();

        if(sUriMatcher.match(uri) == FAVORITE_WITH_ID) {

            String id = uri.getLastPathSegment();

            int favoritesDeleted = db.delete(MovieContract.FavoriteEntry.TABLE_NAME, "movie_id=?", new String[]{id});

            if (favoritesDeleted != 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }

            return favoritesDeleted;

        } else {
            throw new UnsupportedOperationException("Unknown uri " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Unknown uri " + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}

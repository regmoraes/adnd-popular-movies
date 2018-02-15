package com.regmoraes.popularmovies.data.database;

import android.net.Uri;
import android.provider.BaseColumns;

import com.regmoraes.popularmovies.BuildConfig;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public final class MovieContract {

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final Uri BASE_URI_CONTENT = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_FAVORITES = "favorites";
    public static final String PATH_FAVORITES_WITH_ID = "favorites/#";

    private MovieContract() {}

    public static final class FavoriteEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_URI_CONTENT.buildUpon().appendPath(PATH_FAVORITES).build();

        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_NAME_MOVIE_ID = "movie_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_NAME_SYNOPSIS = "synopsis";
        public static final String COLUMN_NAME_POSTER = "poster";
        public static final String COLUMN_NAME_USER_RATING = "rating";
        public static final String COLUMN_NAME_RELEASE_DATE = "release_date";
    }
}

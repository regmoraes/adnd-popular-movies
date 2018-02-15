package com.regmoraes.popularmovies.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class MovieDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Movies.db";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
            MovieContract.FavoriteEntry.TABLE_NAME + " (" +
            MovieContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE," +
            MovieContract.FavoriteEntry.COLUMN_NAME_MOVIE_ID + " INTEGER NOT NULL UNIQUE," +
            MovieContract.FavoriteEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL," +
            MovieContract.FavoriteEntry.COLUMN_NAME_ORIGINAL_TITLE + " TEXT NOT NULL," +
            MovieContract.FavoriteEntry.COLUMN_NAME_SYNOPSIS + " TEXT NOT NULL," +
            MovieContract.FavoriteEntry.COLUMN_NAME_POSTER + " TEXT NOT NULL," +
            MovieContract.FavoriteEntry.COLUMN_NAME_USER_RATING + " REAL NOT NULL," +
            MovieContract.FavoriteEntry.COLUMN_NAME_RELEASE_DATE + " TEXT NOT NULL" +
            ");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MovieContract.FavoriteEntry.TABLE_NAME;

    public MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}

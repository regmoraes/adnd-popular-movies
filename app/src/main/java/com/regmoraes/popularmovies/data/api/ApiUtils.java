package com.regmoraes.popularmovies.data.api;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public final class ApiUtils {

    private static final String MOVIES_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    public static URL buildMoviePosterUrl(String posterPath) {

        Uri builtUri = Uri.parse(MOVIES_POSTER_BASE_URL + posterPath);

        URL moviesPosterUrl = null;

        try {
            moviesPosterUrl = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return moviesPosterUrl;
    }
}

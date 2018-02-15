package com.regmoraes.popularmovies.commons;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public final class VideoUtils {

    private static final String MOVIES_POSTER_BASE_URL = "https://www.youtube.com/watch";

    public static URL buildVideoUrl(String videoKey) {

        Uri builtUri =
                Uri.parse(MOVIES_POSTER_BASE_URL)
                        .buildUpon()
                        .appendQueryParameter("v", videoKey)
                        .build();

        URL moviesPosterUrl = null;

        try {
            moviesPosterUrl = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return moviesPosterUrl;
    }
}

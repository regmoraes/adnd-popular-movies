package com.regmoraes.popularmovies.commons;

import android.net.Uri;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public final class VideoUtils {

    private static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch";

    public static Uri buildVideoUri(String videoKey) {

        return Uri.parse(YOUTUBE_BASE_URL)
                        .buildUpon()
                        .appendQueryParameter("v", videoKey)
                        .build();
    }
}

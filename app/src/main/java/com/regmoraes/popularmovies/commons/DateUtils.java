package com.regmoraes.popularmovies.commons;

import android.content.Context;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public final class DateUtils {

    public static String formatReleaseDate(Context context, String releaseDate) {

        SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Format localFormat = android.text.format.DateFormat.getDateFormat(context);
        String localPattern = ((SimpleDateFormat) localFormat).toLocalizedPattern();

        try {

            Date ymdDate = apiDateFormat.parse(releaseDate);

            SimpleDateFormat localDateFormat = new SimpleDateFormat(localPattern, Locale.getDefault());

            return localDateFormat.format(ymdDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return releaseDate;
    }
}

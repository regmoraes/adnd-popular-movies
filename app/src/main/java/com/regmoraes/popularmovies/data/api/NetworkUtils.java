package com.regmoraes.popularmovies.data.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
final class NetworkUtils {

    static boolean hasInternetConnection(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = null;

        if (connectivityManager != null) {
            netInfo = connectivityManager.getActiveNetworkInfo();
        }

        return (netInfo != null && netInfo.isConnected());
    }
}

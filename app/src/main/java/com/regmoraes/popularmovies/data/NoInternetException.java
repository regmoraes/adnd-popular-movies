package com.regmoraes.popularmovies.data;

import java.io.IOException;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class NoInternetException extends IOException {

    @Override
    public String getMessage() {
        return "There's no Internet connectivity";
    }
}

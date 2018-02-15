package com.regmoraes.popularmovies.data.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.regmoraes.popularmovies.data.model.Movie;

import java.util.List;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class MoviesResponse {

    @SerializedName("results")
    @Expose(serialize = false)
    public List<Movie> results;
}

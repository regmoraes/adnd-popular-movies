package com.regmoraes.popularmovies.data.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.regmoraes.popularmovies.data.model.Movie;
import com.regmoraes.popularmovies.data.model.Video;

import java.util.List;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class MovieVideosResponse {

    @SerializedName("results")
    @Expose(serialize = false)
    public List<Video> videos;
}

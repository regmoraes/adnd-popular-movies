package com.regmoraes.popularmovies.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class Review {

    @SerializedName("id")
    @Expose(serialize = false)
    public String id;

    @SerializedName("author")
    @Expose(serialize = false)
    public String author;

    @SerializedName("content")
    @Expose(serialize = false)
    public String content;
}

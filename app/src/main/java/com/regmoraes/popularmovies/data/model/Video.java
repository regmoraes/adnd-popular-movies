package com.regmoraes.popularmovies.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class Video {

    @SerializedName("id")
    @Expose(serialize = false)
    private String id;

    @SerializedName("name")
    @Expose(serialize = false)
    private String name;

    public Video(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

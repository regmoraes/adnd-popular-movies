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

    @SerializedName("key")
    @Expose(serialize = false)
    private String key;

    public Video(String id, String name, String key) {
        this.id = id;
        this.name = name;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}

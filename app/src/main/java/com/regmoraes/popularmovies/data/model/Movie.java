package com.regmoraes.popularmovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.regmoraes.popularmovies.data.api.ApiUtils;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public class Movie implements Parcelable {

    @SerializedName("id")
    @Expose(serialize = false)
    private final Integer id;

    @SerializedName("vote_average")
    @Expose(serialize = false)
    private final Float voteAverage;

    @SerializedName("title")
    @Expose(serialize = false)
    private final String title;

    @SerializedName("poster_path")
    @Expose(serialize = false)
    private final String posterPath;

    @SerializedName("original_title")
    @Expose(serialize = false)
    private final String originalTitle;

    @SerializedName("overview")
    @Expose(serialize = false)
    private final String overview;

    @SerializedName("release_date")
    @Expose(serialize = false)
    private final String releaseDate;

    public Movie(Integer id, Float voteAverage, String title, String posterPath, String originalTitle, String overview, String releaseDate) {
        this.id = id;
        this.voteAverage = voteAverage;
        this.title = title;
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    protected Movie(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readFloat();
        }
        title = in.readString();
        posterPath = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        if (voteAverage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(voteAverage);
        }
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getPosterUrl() {
        URL moviePosterUrl = ApiUtils.buildMoviePosterUrl(posterPath);
        return moviePosterUrl.toString();
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}

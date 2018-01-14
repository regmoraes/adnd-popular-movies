package com.regmoraes.popularmovies.commons;

/**
 * Possible status types of a response provided to the UI. <br><br>
 *
 * This class was created by James Shvarts and presented his article
 * <a href="https://proandroiddev.com/mvvm-architecture-using-livedata-rxjava-and-new-dagger-android-injection-639837b1eb6c">"Implementing MVVM using LiveData, RxJava, Dagger Android"</a>
 */
public enum Status {
    LOADING,
    SUCCESS,
    ERROR
}

package com.regmoraes.popularmovies.presentation.detail;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.regmoraes.popularmovies.PopularMoviesApplication;
import com.regmoraes.popularmovies.R;
import com.regmoraes.popularmovies.data.model.Movie;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public final class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsContract.View{

    private MoviesDetailsComponent movieDetailsComponent;

    private ImageView mMoviePoster;
    private TextView mMovieOriginalTitleTextView;
    private TextView mMovieOverviewTextView;
    private TextView mMovieReleaseDateTextView;
    private TextView mMovieRatingTextView;

    @Inject public MovieDetailsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        movieDetailsComponent = ((PopularMoviesApplication) getApplication()).getApplicationComponent().movieDetailsComponent();
        movieDetailsComponent.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mMoviePoster = findViewById(R.id.iv_movie_poster);
        mMovieOriginalTitleTextView = findViewById(R.id.tv_movie_title);
        mMovieReleaseDateTextView = findViewById(R.id.tv_movie_release_date);
        mMovieRatingTextView = findViewById(R.id.tv_movie_rating);
        mMovieOverviewTextView = findViewById(R.id.tv_movie_overview);

        Intent receivedIntent = getIntent();

        if(receivedIntent != null) {
            if (receivedIntent.hasExtra(Movie.class.getSimpleName())) {

                Movie movie = receivedIntent.getParcelableExtra(Movie.class.getSimpleName());

                presenter.attachView(this);
                presenter.loadMovieDetails(movie);
            }
        }
    }

    @Override
    public void showMovieDetails(Movie movie) {

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle(movie.getTitle());
        }

        Picasso.with(this).load(movie.getPosterPath()).into(mMoviePoster);

        mMovieOverviewTextView.setText(movie.getOverview());
        mMovieOriginalTitleTextView.setText(String.format(getString(R.string.movie_original_title), movie.getOriginalTitle()));
        mMovieReleaseDateTextView.setText(String.format(getString(R.string.movie_release_date), movie.getReleaseDate()));
        mMovieRatingTextView.setText(String.format(getString(R.string.movie_rating), String.valueOf(movie.getVoteAverage())));
    }

    @Override
    protected void onDestroy() {
        movieDetailsComponent = null;
        super.onDestroy();
    }
}

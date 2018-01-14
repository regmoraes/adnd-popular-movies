package com.regmoraes.popularmovies.presentation.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.regmoraes.popularmovies.R;
import com.regmoraes.popularmovies.data.model.Movie;
import com.squareup.picasso.Picasso;

public final class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsContract.View{

    private MovieDetailsViewModel movieDetailsViewModel;

    private ImageView mMoviePoster;
    private TextView mMovieOriginalTitleTextView;
    private TextView mMovieOverviewTextView;
    private TextView mMovieReleaseDateTextView;
    private TextView mMovieRatingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

                MovieDetailsViewModelFactory movieDetailsViewModelFactory = new MovieDetailsViewModelFactory(movie);
                movieDetailsViewModel = ViewModelProviders.of(this, movieDetailsViewModelFactory).get(MovieDetailsViewModel.class);
            }
        }

        setUpObservers();
    }

    private void setUpObservers() {
        movieDetailsViewModel.getMovie().observe(this, this::showMovieDetails);
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
}

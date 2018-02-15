package com.regmoraes.popularmovies.presentation.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.regmoraes.popularmovies.PopularMoviesApplication;
import com.regmoraes.popularmovies.R;
import com.regmoraes.popularmovies.data.model.Movie;
import com.regmoraes.popularmovies.databinding.ActivityMovieDetailsBinding;

import javax.inject.Inject;

public final class MovieDetailsActivity extends AppCompatActivity {

    private MovieDetailsComponent movieDetailsComponent;

    @Inject public MovieDetailsViewModelFactory viewModelFactory;
    public MovieDetailsViewModel viewModel;
    private ActivityMovieDetailsBinding viewBinding;

    private MovieDetailsPageAdapter pageAdapter;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        movieDetailsComponent = ((PopularMoviesApplication) getApplicationContext())
                .getApplicationComponent().movieDetailsComponent();
        movieDetailsComponent.inject(this);

        super.onCreate(savedInstanceState);

        Intent receivedIntent = getIntent();
        if(receivedIntent != null) {
            if (receivedIntent.hasExtra(Movie.class.getSimpleName())) {
                movie = receivedIntent.getParcelableExtra(Movie.class.getSimpleName());
            }
        }

        setUpView();
        setUpViewModel();
    }

    private void setUpView() {
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        setUpToolbar();
    }

    private void setUpToolbar() {

        setSupportActionBar(viewBinding.toolbar);
        getSupportActionBar().setTitle(movie.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pageAdapter = new MovieDetailsPageAdapter(this, getSupportFragmentManager());
        viewBinding.pager.setAdapter(pageAdapter);
        viewBinding.tabLayout.setupWithViewPager(viewBinding.pager);
    }

    private void setUpViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailsViewModel.class);
        viewModel.setMovieData(movie);
    }

    @Override
    protected void onDestroy() {
        movieDetailsComponent = null;
        super.onDestroy();
    }
}

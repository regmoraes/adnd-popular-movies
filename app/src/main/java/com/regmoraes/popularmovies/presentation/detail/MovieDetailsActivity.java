package com.regmoraes.popularmovies.presentation.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.regmoraes.popularmovies.PopularMoviesApplication;
import com.regmoraes.popularmovies.R;
import com.regmoraes.popularmovies.data.model.Movie;
import com.regmoraes.popularmovies.databinding.ActivityMovieDetailsBinding;

import java.net.URL;

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
        viewModel.getEventShareVideoUri().observe(this, this::shareVideoUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
        return true;
    }

    private void shareVideoUrl(URL videoUrl) {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(getString(R.string.mime_type_text));
        shareIntent.putExtra(Intent.EXTRA_TEXT, videoUrl.toString());
        startActivity(Intent.createChooser(shareIntent,getString(R.string.action_share_trailer)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_share_trailer) {
            viewModel.onShareTrailerClicked();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        movieDetailsComponent = null;
        super.onDestroy();
    }
}

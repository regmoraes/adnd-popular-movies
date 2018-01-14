package com.regmoraes.popularmovies.presentation.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.regmoraes.popularmovies.PopularMoviesApplication;
import com.regmoraes.popularmovies.R;
import com.regmoraes.popularmovies.data.model.Movie;
import com.regmoraes.popularmovies.presentation.detail.MovieDetailsActivity;

import java.util.List;

import javax.inject.Inject;

public final class MoviesActivity extends AppCompatActivity implements MoviesListContract.View, MoviesAdapter.MoviesClickListener, PopupMenu.OnMenuItemClickListener {

    private MoviesViewModel moviesViewModel;

    private MoviesListComponent moviesListComponent;
    private RecyclerView mMoviesRecyclerView;
    private TextView mLoadingErrorTextView;
    private MoviesAdapter mMoviesAdapter;
    private ProgressBar mLoadingProgress;

    @Inject public MoviesViewModelFactory moviesViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        moviesListComponent = ((PopularMoviesApplication) getApplication()).getApplicationComponent().moviesListComponent();
        moviesListComponent.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        mMoviesRecyclerView = findViewById(R.id.recyclerView_movies);
        mMoviesRecyclerView.setLayoutManager(layoutManager);
        mMoviesRecyclerView.setHasFixedSize(true);

        mMoviesAdapter = new MoviesAdapter(this);
        mMoviesRecyclerView.setAdapter(mMoviesAdapter);

        mLoadingProgress = findViewById(R.id.pb_movies_loading);

        mLoadingErrorTextView = findViewById(R.id.tv_movies_load_error);

        moviesViewModel = ViewModelProviders.of(this, moviesViewModelFactory).get(MoviesViewModel.class);

        setUpObservers();
    }

    private void setUpObservers() {

        moviesViewModel.getMoviesResponse().observe(this, moviesResponse -> {

            switch (moviesResponse.getStatus()) {

                case LOADING:
                    Log.d(this.getClass().getSimpleName(), "LOADING DATA");
                    showMoviesLoading();
                    break;

                case SUCCESS:
                    Log.d(this.getClass().getSimpleName(), "DATA LOADED");
                    showMovies(moviesResponse.getData());
                    break;

                case ERROR:
                    Log.d(this.getClass().getSimpleName(), "ERROR LOADING DATA");
                    showMoviesLoadError();
                    break;
            }
        });

        moviesViewModel.getMovie().observe(this, this::showMovieDetails);
    }

    @Override
    public void showMoviesLoading() {

        mMoviesAdapter.setMovies(null);

        mMoviesRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingErrorTextView.setVisibility(View.INVISIBLE);

        mLoadingProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMovies(List<Movie> movies) {

        mMoviesAdapter.setMovies(movies);

        mMoviesRecyclerView.setVisibility(View.VISIBLE);
        mLoadingErrorTextView.setVisibility(View.INVISIBLE);
        mLoadingProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMoviesLoadError() {

        mMoviesAdapter.setMovies(null);

        mMoviesRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingErrorTextView.setVisibility(View.VISIBLE);
        mLoadingProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMovieDetails(Movie movie) {

        Intent movieDetailsIntent = new Intent(this, MovieDetailsActivity.class);
        movieDetailsIntent.putExtra(Movie.class.getSimpleName(), movie);

        startActivity(movieDetailsIntent);
    }

    @Override
    public void onMovieClicked(Movie movie) {
        moviesViewModel.onMovieClicked(movie);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int clickedItemId = item.getItemId();

        if(clickedItemId == R.id.action_sort) {
            showSortingOptions();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showSortingOptions() {

        View anchorView = findViewById(R.id.action_sort);

        PopupMenu popup = new PopupMenu(this, anchorView);

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_movies_sort, popup.getMenu());

        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        int clickedItemId = item.getItemId();

        switch(clickedItemId) {

            case R.id.action_sort_rating:
                moviesViewModel.sortByRating();
                break;

            case R.id.action_sort_popularity:
                moviesViewModel.sortByPopularity();
                break;

            default:break;
        }

        return true;
    }

    @Override
    protected void onDestroy() {

        moviesListComponent = null;
        super.onDestroy();
    }
}

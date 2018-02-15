package com.regmoraes.popularmovies.presentation.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.regmoraes.popularmovies.PopularMoviesApplication;
import com.regmoraes.popularmovies.R;
import com.regmoraes.popularmovies.data.model.Movie;
import com.regmoraes.popularmovies.databinding.ActivityMoviesBinding;
import com.regmoraes.popularmovies.presentation.detail.MovieDetailsActivity;

import java.util.List;

import javax.inject.Inject;

public final class MoviesActivity extends AppCompatActivity implements MoviesContract.View, MoviesAdapter.MoviesClickListener, PopupMenu.OnMenuItemClickListener {

    private MoviesComponent moviesListComponent;

    @Inject public MoviesViewModelFactory viewModelFactory;
    private MoviesViewModel viewModel;
    private ActivityMoviesBinding viewBinding;

    private MoviesAdapter mMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        moviesListComponent = ((PopularMoviesApplication) getApplication()).getApplicationComponent().moviesListComponent();
        moviesListComponent.inject(this);

        super.onCreate(savedInstanceState);

        setUpView();
        setUpToolbar();
        setUpViewModel();
    }

    private void setUpView() {

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        viewBinding.recyclerViewMovies.setLayoutManager(layoutManager);
        viewBinding.recyclerViewMovies.setHasFixedSize(true);

        mMoviesAdapter = new MoviesAdapter(this);
        viewBinding.recyclerViewMovies.setAdapter(mMoviesAdapter);
    }

    private void setUpToolbar() {

        setSupportActionBar(viewBinding.toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
    }

    private void setUpViewModel() {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel.class);

        viewModel.getMoviesResponse().observe(this, moviesResponse -> {

            if (moviesResponse != null) {
                switch (moviesResponse.getStatus()) {

                    case LOADING:
                        showMoviesLoading();
                        break;

                    case SUCCESS:
                        showMovies(moviesResponse.getData());
                        break;

                    case ERROR:
                        showMoviesLoadError();
                        break;
                }
            }
        });

        viewModel.getMovie().observe(this, this::showMovieDetails);
    }

    @Override
    public void showMoviesLoading() {

        mMoviesAdapter.setMovies(null);

        viewBinding.recyclerViewMovies.setVisibility(View.INVISIBLE);
        viewBinding.tvMoviesLoadError.setVisibility(View.INVISIBLE);
        viewBinding.pbMoviesLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMovies(List<Movie> movies) {

        mMoviesAdapter.setMovies(movies);

        viewBinding.recyclerViewMovies.setVisibility(View.VISIBLE);
        viewBinding.tvMoviesLoadError.setVisibility(View.INVISIBLE);
        viewBinding.pbMoviesLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMoviesLoadError() {

        mMoviesAdapter.setMovies(null);

        viewBinding.recyclerViewMovies.setVisibility(View.INVISIBLE);
        viewBinding.tvMoviesLoadError.setVisibility(View.VISIBLE);
        viewBinding.pbMoviesLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMovieDetails(Movie movie) {

        Intent movieDetailsIntent = new Intent(this, MovieDetailsActivity.class);
        movieDetailsIntent.putExtra(Movie.class.getSimpleName(), movie);

        startActivity(movieDetailsIntent);
    }

    @Override
    public void onMovieClicked(Movie movie) {
        viewModel.onMovieClicked(movie);
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
                viewModel.sortByRating();
                break;

            case R.id.action_sort_popularity:
                viewModel.sortByPopularity();
                break;

            case R.id.action_sort_favorites:
                viewModel.sortByFavorites();
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

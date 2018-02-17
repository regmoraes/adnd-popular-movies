package com.regmoraes.popularmovies.presentation.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.regmoraes.popularmovies.R;
import com.regmoraes.popularmovies.databinding.FragmentMovieInfoBinding;
import com.regmoraes.popularmovies.presentation.home.MoviesActivity;

public final class MovieInfoFragment extends Fragment {

    public MovieDetailsViewModel viewModel;
    private FragmentMovieInfoBinding viewBinding;
    private Toast mToast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        viewBinding = FragmentMovieInfoBinding.inflate(inflater, container, false);
        viewBinding.floatingActionButton.setOnClickListener(v -> viewModel.onFavoriteClicked());

        return viewBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getActivity() != null) {

            this.viewModel = ((MovieDetailsActivity) getActivity()).viewModel;
            viewModel.getEventAddedToFavorite().observe(getActivity(), this::showAddedToFavorites);

            viewBinding.setViewModel(viewModel);
        }
    }

    private void showAddedToFavorites(Boolean added) {

        if (mToast != null) mToast.cancel();

        if(added) {
            mToast = Toast.makeText(getActivity(), getActivity().getString(R.string.movie_added_favorites), Toast.LENGTH_SHORT);
        } else {
            mToast = Toast.makeText(getActivity(), getActivity().getString(R.string.movie_removed_favorites), Toast.LENGTH_SHORT);
        }

        mToast.show();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(MoviesActivity.EXTRA_RELOAD_FAVORITES, true);
        getActivity().setResult(Activity.RESULT_OK, resultIntent);
    }
}

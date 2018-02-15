package com.regmoraes.popularmovies.presentation.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.regmoraes.popularmovies.databinding.FragmentMovieInfoBinding;

public final class MovieInfoFragment extends Fragment {

    public MovieDetailsViewModel viewModel;
    private FragmentMovieInfoBinding viewBinding;

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
            viewBinding.setViewModel(viewModel);
        }
    }
}

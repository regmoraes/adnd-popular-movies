package com.regmoraes.popularmovies.presentation.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.regmoraes.popularmovies.R;
import com.regmoraes.popularmovies.data.model.Video;
import com.regmoraes.popularmovies.databinding.FragmentVideosBinding;

import java.util.List;

public final class MovieVideosFragment extends Fragment {

    private MovieDetailsViewModel viewModel;
    private FragmentVideosBinding viewBinding;

    private MovieVideosAdapter reviewsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_videos, container, false);

        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        reviewsAdapter = new MovieVideosAdapter();

        viewBinding.recyclerViewReviews.setLayoutManager(mLayoutManager);
        viewBinding.recyclerViewReviews.setAdapter(reviewsAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getActivity() != null) {
            this.viewModel = ((MovieDetailsActivity) getActivity()).viewModel;
            viewModel.getObservableVideos().observe(getActivity(), this::setVideoData);
        }
    }

    private void setVideoData(List<Video> videos) {
        reviewsAdapter.setVideos(videos);
    }
}

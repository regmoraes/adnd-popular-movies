package com.regmoraes.popularmovies.presentation.detail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
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

import static android.content.Intent.ACTION_VIEW;

public final class MovieVideosFragment extends Fragment implements MovieVideosAdapter.OnVideoClickListener {

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
        reviewsAdapter = new MovieVideosAdapter(this);

        viewBinding.recyclerViewReviews.setLayoutManager(mLayoutManager);
        viewBinding.recyclerViewReviews.setAdapter(reviewsAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getActivity() != null) {
            this.viewModel = ((MovieDetailsActivity) getActivity()).viewModel;

            viewModel.getObservableVideos().observe(getActivity(), this::showVideoData);
            viewModel.getObservableLoadingVideosError().observe(getActivity(), this::showVideosLoadingError);
            viewModel.getObservableNoVideosAvailable().observe(getActivity(), this::showNoVideosAvailable);
            viewModel.getEventShowVideo().observe(getActivity(), this::showVideo);
        }
    }

    private void showVideoData(List<Video> videos) {
        reviewsAdapter.setVideos(videos);
    }

    private void showVideosLoadingError(Boolean show) {
        if(show != null) {
            viewBinding.recyclerViewReviews.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            viewBinding.textViewLoadFeedback.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        }
    }

    private void showNoVideosAvailable(Boolean show) {
        if(show != null) {
            viewBinding.recyclerViewReviews.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            viewBinding.textViewLoadFeedback.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            viewBinding.textViewLoadFeedback.setText(R.string.videos_no_videos);
        }
    }

    private void showVideo(Uri videoUri) {

        Intent videoIntent = new Intent();
        videoIntent.setAction(ACTION_VIEW);
        videoIntent.setData(videoUri);

        startActivity(videoIntent);
    }

    @Override
    public void onVideoClicked(String videoKey) {
        viewModel.onVideoClicked(videoKey);
    }
}

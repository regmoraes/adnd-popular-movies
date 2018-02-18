package com.regmoraes.popularmovies.presentation.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.regmoraes.popularmovies.R;
import com.regmoraes.popularmovies.data.model.Review;
import com.regmoraes.popularmovies.databinding.FragmentReviewsBinding;

import java.util.List;

public final class MovieReviewsFragment extends Fragment {

    private MovieDetailsViewModel viewModel;
    private FragmentReviewsBinding viewBinding;

    private MovieReviewsAdapter reviewsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reviews, container, false);

        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        reviewsAdapter = new MovieReviewsAdapter();

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(viewBinding.recyclerViewReviews.getContext(),
                        mLayoutManager.getOrientation());

        viewBinding.recyclerViewReviews.setLayoutManager(mLayoutManager);
        viewBinding.recyclerViewReviews.addItemDecoration(dividerItemDecoration);
        viewBinding.recyclerViewReviews.setAdapter(reviewsAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getActivity() != null) {

            this.viewModel = ((MovieDetailsActivity) getActivity()).viewModel;

            viewModel.getObservableReviews().observe(getActivity(), this::showReviewData);
            viewModel.getObservableLoadingReviewsError().observe(getActivity(), this::showReviewsLoadingError);
            viewModel.getObservableNoReviewsAvailable().observe(getActivity(), this::showNoReviewsAvailable);
        }
    }

    private void showReviewData(List<Review> reviews) {
        reviewsAdapter.setReviews(reviews);
    }

    private void showReviewsLoadingError(Boolean show) {
        if(show != null) {
            viewBinding.recyclerViewReviews.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            viewBinding.textViewLoadFeedback.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            viewBinding.textViewLoadFeedback.setText(R.string.reviews_loading_error);
        }
    }

    private void showNoReviewsAvailable(Boolean show) {
        if(show != null) {
            viewBinding.recyclerViewReviews.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            viewBinding.textViewLoadFeedback.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            viewBinding.textViewLoadFeedback.setText(R.string.reviews_no_review);
        }
    }
}

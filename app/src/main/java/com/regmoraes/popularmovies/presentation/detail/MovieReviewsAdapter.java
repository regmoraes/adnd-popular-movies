package com.regmoraes.popularmovies.presentation.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.regmoraes.popularmovies.data.model.Review;
import com.regmoraes.popularmovies.databinding.ReviewListItemBinding;

import java.util.List;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public final class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ReviewsAdapterViewHolder> {

    private List<Review> reviews;

    @Override
    public ReviewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ReviewListItemBinding itemBinding = ReviewListItemBinding.inflate(inflater, parent, false);

        return new ReviewsAdapterViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapterViewHolder holder, int position) {

        Review review = reviews.get(position);

        if(review != null) holder.bind(reviews.get(position));
    }

    @Override
    public int getItemCount() {

        if(reviews != null) {
            return reviews.size();
        } else {
            return 0;
        }
    }

    void setReviews(List<Review> reviews) {

        this.reviews = reviews;
        notifyDataSetChanged();
    }

    class ReviewsAdapterViewHolder extends RecyclerView.ViewHolder {

        private ReviewListItemBinding itemBinding;

        ReviewsAdapterViewHolder(ReviewListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        void bind(Review review) {
            itemBinding.setReview(review);
            itemBinding.executePendingBindings();
        }
    }
}

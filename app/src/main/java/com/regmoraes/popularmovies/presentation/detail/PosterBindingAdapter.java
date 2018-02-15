package com.regmoraes.popularmovies.presentation.detail;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public final class PosterBindingAdapter {

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }
}

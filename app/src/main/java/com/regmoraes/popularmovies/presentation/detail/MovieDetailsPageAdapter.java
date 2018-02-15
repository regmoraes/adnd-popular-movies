package com.regmoraes.popularmovies.presentation.detail;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.regmoraes.popularmovies.R;

public final class MovieDetailsPageAdapter extends FragmentStatePagerAdapter {

    private Context context;

    private final int FRAGMENTS_COUNT = 3;

    public MovieDetailsPageAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0: return new MovieInfoFragment();
            case 1: return new MovieVideosFragment();
            default: return new MovieReviewsFragment();
        }
    }

    @Override
    public int getCount() {
        return FRAGMENTS_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {

            case 0: return context.getString(R.string.fragment_movie_summary);
            case 1: return context.getString(R.string.fragment_movie_videos);
            default: return context.getString(R.string.fragment_movie_reviews);
        }
    }
}

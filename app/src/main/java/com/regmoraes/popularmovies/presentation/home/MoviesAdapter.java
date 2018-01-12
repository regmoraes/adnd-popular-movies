package com.regmoraes.popularmovies.presentation.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.regmoraes.popularmovies.R;
import com.regmoraes.popularmovies.data.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public final class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private List<Movie> movies;
    private final MoviesClickListener clickListener;

    MoviesAdapter(MoviesClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movies_list_item, parent, false);

        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder holder, int position) {

        Movie movie = movies.get(position);

        Picasso.with(holder.itemView.getContext()).load(movie.getPosterPath()).into(holder.mMoviePoster);
    }

    @Override
    public int getItemCount() {

        if(movies != null) {
            return movies.size();
        } else {
            return 0;
        }
    }

    void setMovies(List<Movie> movies) {

        this.movies = movies;
        notifyDataSetChanged();
    }

    public interface MoviesClickListener {

        void onMovieClicked(Movie movie);
    }

    protected class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView mMoviePoster;

        MoviesAdapterViewHolder(View itemView) {
            super(itemView);

            mMoviePoster = itemView.findViewById(R.id.iv_movie_poster);
            mMoviePoster.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Movie clickedMovie = movies.get(getAdapterPosition());

            clickListener.onMovieClicked(clickedMovie);
        }
    }
}

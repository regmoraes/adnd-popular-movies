package com.regmoraes.popularmovies.presentation.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.regmoraes.popularmovies.data.model.Video;
import com.regmoraes.popularmovies.databinding.VideoListItemBinding;

import java.util.List;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
public final class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.VideosAdapterViewHolder> {

    private List<Video> videos;

    @Override
    public VideosAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        VideoListItemBinding itemBinding = VideoListItemBinding.inflate(inflater, parent, false);

        return new VideosAdapterViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(VideosAdapterViewHolder holder, int position) {

        Video video = videos.get(position);

        if(video != null) holder.bind(videos.get(position));
    }

    @Override
    public int getItemCount() {

        if(videos != null) {
            return videos.size();
        } else {
            return 0;
        }
    }

    void setVideos(List<Video> videos) {

        this.videos = videos;
        notifyDataSetChanged();
    }

    class VideosAdapterViewHolder extends RecyclerView.ViewHolder {

        private VideoListItemBinding itemBinding;

        VideosAdapterViewHolder(VideoListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        void bind(Video movie) {
            itemBinding.setVideo(movie);
            itemBinding.executePendingBindings();
        }
    }
}

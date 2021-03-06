package com.shoaib.tvshowsmvvm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shoaib.tvshowsmvvm.R;
import com.shoaib.tvshowsmvvm.databinding.ItemContainerTVShowsBinding;
import com.shoaib.tvshowsmvvm.listeners.TVShowsListener;
import com.shoaib.tvshowsmvvm.listeners.WatchListListener;
import com.shoaib.tvshowsmvvm.models.TVShow;

import java.util.List;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.TVShowsViewHolder> {
    private List<TVShow> tvShows;
    private LayoutInflater layoutInflater;
    public WatchListListener watchListListener;

    public WatchListAdapter(List<TVShow> tvShows, WatchListListener watchListListener) {
        this.tvShows = tvShows;
        this.watchListListener = watchListListener;
    }


    @NonNull
    @Override
    public TVShowsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerTVShowsBinding tvShowsBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_container_tv_show, parent, false);
        return new TVShowsViewHolder(tvShowsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowsViewHolder holder, int position) {
        holder.bindTVShow(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public class TVShowsViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerTVShowsBinding itemContainerTVShowsBinding;

        public TVShowsViewHolder(@NonNull ItemContainerTVShowsBinding itemContainerTVShowsBinding) {
            super(itemContainerTVShowsBinding.getRoot());
            this.itemContainerTVShowsBinding = itemContainerTVShowsBinding;
        }

        public void bindTVShow(TVShow tvShow) {
            itemContainerTVShowsBinding.setTvShow(tvShow);
            itemContainerTVShowsBinding.executePendingBindings();
            itemContainerTVShowsBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    watchListListener.onTVShowClicked(tvShow);
                }
            });
            itemContainerTVShowsBinding.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    watchListListener.removeTVShowFromWatchlist(tvShow, getAdapterPosition());
                }
            });
            itemContainerTVShowsBinding.imageDelete.setVisibility(View.VISIBLE);
        }
    }
}

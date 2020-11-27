package com.shoaib.tvshowsmvvm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shoaib.tvshowsmvvm.R;
import com.shoaib.tvshowsmvvm.databinding.ItemContainerEpisodeBinding;
import com.shoaib.tvshowsmvvm.models.Episode;

import java.util.ArrayList;
import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder> {

    private List<Episode> episodeList;
    private LayoutInflater layoutInflater;

    public EpisodesAdapter(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ItemContainerEpisodeBinding episodeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_container_episode, parent, false);
        return new EpisodeViewHolder(episodeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        holder.setItem(episodeList.get(position));
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public class EpisodeViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerEpisodeBinding itemContainerEpisodeBinding;

        public EpisodeViewHolder(@NonNull ItemContainerEpisodeBinding itemContainerEpisodeBinding) {
            super(itemContainerEpisodeBinding.getRoot());
            this.itemContainerEpisodeBinding = itemContainerEpisodeBinding;

        }

        private void setItem(Episode episode) {
            if (episode != null) {

                String title = "S";
                String season = episode.getSeason();
                if (season.length() == 1) {
                    season = "0".concat(season);
                }
                String episodeNumber = episode.getEpisode();

                if (episodeNumber.length() == 1) {
                    episodeNumber = "0".concat(episodeNumber);
                }

                episodeNumber = "E".concat(episodeNumber);
                title = title.concat(season).concat(episodeNumber);
                itemContainerEpisodeBinding.setName(episode.getName());
                itemContainerEpisodeBinding.setTitle(title);
                itemContainerEpisodeBinding.setAirDate(episode.getAirDate());
            }
        }
    }
}

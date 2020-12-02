package com.shoaib.tvshowsmvvm.listeners;

import com.shoaib.tvshowsmvvm.models.TVShow;

public interface WatchListListener {

    void onTVShowClicked(TVShow tvShow);

    void removeTVShowFromWatchlist(TVShow tvShow, int position);
}

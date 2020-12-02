package com.shoaib.tvshowsmvvm.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.shoaib.tvshowsmvvm.activities.dao.TVShowDao;
import com.shoaib.tvshowsmvvm.activities.database.TVShowsDatabase;
import com.shoaib.tvshowsmvvm.models.TVShow;
import com.shoaib.tvshowsmvvm.repositories.TVShowsDetailsRepository;
import com.shoaib.tvshowsmvvm.responses.TVShowDetailsResponse;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TVShowDetailViewModel extends AndroidViewModel {


    private TVShowsDetailsRepository tvShowsDetailsRepository;
    private TVShowsDatabase tvShowsDatabase;

    public TVShowDetailViewModel(@NonNull Application application) {
        super(application);
        tvShowsDetailsRepository = new TVShowsDetailsRepository();
        this.tvShowsDatabase = TVShowsDatabase.getTvShowsDatabase(application);
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetailsVM(String tvShowId) {
        return tvShowsDetailsRepository.getTVShowDetailsRepository(tvShowId);
    }

    public Completable addToWatchList(TVShow tvShow) {
        return tvShowsDatabase.tvShowDao().addToWatchList(tvShow);
    }

    public Flowable<TVShow> getTVShowFromWatchlist(String tvShowId) {
        return tvShowsDatabase.tvShowDao().getTVShowFromWatchList(tvShowId);
    }

    public Completable removeTVShowFromWatchList(TVShow tvShow) {
        return tvShowsDatabase.tvShowDao().removeFromWatchList(tvShow);
    }
}

package com.shoaib.tvshowsmvvm.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.shoaib.tvshowsmvvm.repositories.TVShowsDetailsRepository;
import com.shoaib.tvshowsmvvm.responses.TVShowDetailsResponse;

public class TVShowDetailViewModel extends ViewModel {
    private TVShowsDetailsRepository tvShowsDetailsRepository;
    public TVShowDetailViewModel(){
        tvShowsDetailsRepository = new TVShowsDetailsRepository();
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetailsVM(String tvShowId){
        return tvShowsDetailsRepository.getTVShowDetailsRepository(tvShowId);
    }

}

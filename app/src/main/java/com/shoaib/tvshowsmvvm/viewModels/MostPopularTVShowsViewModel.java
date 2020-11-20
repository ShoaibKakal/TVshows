package com.shoaib.tvshowsmvvm.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.shoaib.tvshowsmvvm.repositories.MostPopularTVShowsRepository;
import com.shoaib.tvshowsmvvm.responses.TVShowsResponse;

public class MostPopularTVShowsViewModel extends ViewModel {

    private MostPopularTVShowsRepository mostPopularTVShowsRepository;

    public MostPopularTVShowsViewModel(){
        mostPopularTVShowsRepository = new MostPopularTVShowsRepository();
    }

    public LiveData<TVShowsResponse> getMostPopularTVShowsVM(int page){
        return mostPopularTVShowsRepository.getMostPopularTVShowsRepo(page);
    }
}

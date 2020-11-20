package com.shoaib.tvshowsmvvm.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shoaib.tvshowsmvvm.network.ApiClient;
import com.shoaib.tvshowsmvvm.network.ApiService;
import com.shoaib.tvshowsmvvm.responses.TVShowsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTVShowsRepository {
    private ApiService apiService;

    public MostPopularTVShowsRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVShowsResponse> getMostPopularTVShowsRepo(int page){

        MutableLiveData<TVShowsResponse> data = new MutableLiveData<>();
        apiService.getMostPopularTVShows(page).enqueue(new Callback<TVShowsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowsResponse> call,@NonNull Response<TVShowsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowsResponse> call,@NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}

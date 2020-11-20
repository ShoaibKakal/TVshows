package com.shoaib.tvshowsmvvm.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.shoaib.tvshowsmvvm.network.ApiClient;
import com.shoaib.tvshowsmvvm.network.ApiService;
import com.shoaib.tvshowsmvvm.responses.TVShowDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowsDetailsRepository {
    private ApiService apiService;

    public TVShowsDetailsRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetailsRepository(String tvShowId) {

        MutableLiveData<TVShowDetailsResponse> data = new MutableLiveData<>();
        apiService.getTVShowsDetails(tvShowId).enqueue(new Callback<TVShowDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowDetailsResponse> call, @NonNull Response<TVShowDetailsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowDetailsResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}

package com.shoaib.tvshowsmvvm.network;

import com.shoaib.tvshowsmvvm.responses.TVShowDetailsResponse;
import com.shoaib.tvshowsmvvm.responses.TVShowsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<TVShowsResponse> getMostPopularTVShows(@Query("page") int page);

    @GET("show-details")
    Call<TVShowDetailsResponse> getTVShowsDetails(@Query("q") String tvShowId);

}

package com.shoaib.tvshowsmvvm.responses;

import com.google.gson.annotations.SerializedName;
import com.shoaib.tvshowsmvvm.models.TVShowDetails;

public class TVShowDetailsResponse {

    @SerializedName("tvShow")
    private TVShowDetails tvShowDetails;

    public TVShowDetails getTvShowDetails() {
        return tvShowDetails;
    }
}

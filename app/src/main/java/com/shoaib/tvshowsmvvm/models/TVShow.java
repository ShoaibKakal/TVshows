package com.shoaib.tvshowsmvvm.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "tvShows")
public class TVShow implements Serializable { //Implement Serializable when need to pass the whole class data from one activity to another in intents instead of passing each variables.

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("country")
    private String country;

    @SerializedName("network")
    private String network;

    @SerializedName("status")
    private String status;

    @SerializedName("image_thumbnail_path")
    private String thumbnail;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getCountry() {
        return country;
    }

    public String getNetwork() {
        return network;
    }

    public String getStatus() {
        return status;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}

package com.shoaib.tvshowsmvvm.activities.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.shoaib.tvshowsmvvm.models.TVShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TVShowDao {

    @Query("SELECT * FROM tvshows")
    Flowable<List<TVShow>> getWatchList(); //Flowable data

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToWatchList(TVShow tvShow); //completable data

    @Delete
    Completable removeFromWatchList(TVShow tvShow); // Completable data

    @Query("SELECT * FROM tvShows WHERE id = :tvShowId")
    Flowable<TVShow> getTVShowFromWatchList(String tvShowId);

}

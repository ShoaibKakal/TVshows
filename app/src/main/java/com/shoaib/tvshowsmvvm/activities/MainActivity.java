package com.shoaib.tvshowsmvvm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.shoaib.tvshowsmvvm.R;
import com.shoaib.tvshowsmvvm.adapters.TVShowsAdapter;
import com.shoaib.tvshowsmvvm.databinding.ActivityMainBinding;
import com.shoaib.tvshowsmvvm.listeners.TVShowsListener;
import com.shoaib.tvshowsmvvm.models.TVShow;
import com.shoaib.tvshowsmvvm.viewModels.MostPopularTVShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TVShowsListener {

    private MostPopularTVShowsViewModel tvShowsViewModel;
    private ActivityMainBinding activityMainBinding;
    private final List<TVShow> tvShows = new ArrayList<>();
    private TVShowsAdapter tvShowsAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        doInitialization();



        /*
          TODO: LEARN RxJava.
          TODO: Add animated recyclerview
          TODO: Add automatic imageScroller.
          TODO: Add Upcoming Episodes section in detailed Activity.
          TODO: Add shimmer effect
         */


    }


    private void doInitialization() {

        //setHasFixedSize means that The height or width of the item won't change.
        activityMainBinding.tvShowsRecyclerView.setHasFixedSize(true);
        tvShowsViewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        tvShowsAdapter = new TVShowsAdapter(tvShows, this);
        activityMainBinding.tvShowsRecyclerView.setAdapter(tvShowsAdapter);

        activityMainBinding.tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //TODO: can't get below checkpoint.
                if (!activityMainBinding.tvShowsRecyclerView.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1;
                        getMostPopularTVShows();
                    }
                }
            }
        });

        activityMainBinding.imageWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WatchlistActivity.class));
            }
        });

        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {
        toggleLoading();
        tvShowsViewModel.getMostPopularTVShowsVM(currentPage).observe(this, mostPopularTVShowsResponse -> {
            toggleLoading();
            if (mostPopularTVShowsResponse != null) {
                totalAvailablePages = mostPopularTVShowsResponse.getTotalPages();
                if (mostPopularTVShowsResponse.getTvShows() != null) {
                    int oldCount = tvShows.size();
                    tvShows.addAll(mostPopularTVShowsResponse.getTvShows());
                    tvShowsAdapter.notifyItemRangeInserted(oldCount, tvShows.size());
                }
            }
        });

    }


    private void toggleLoading() {
        if (currentPage == 1) {
            activityMainBinding.setIsLoading((activityMainBinding.getIsLoading() == null) || !activityMainBinding.getIsLoading());
        } else {
            activityMainBinding.setIsLoadingMore(activityMainBinding.getIsLoadingMore() == null || !activityMainBinding.getIsLoadingMore());
        }
    }


    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
//        intent.putExtra("id", tvShow.getId());
//        intent.putExtra("name", tvShow.getName());
//        intent.putExtra("startDate", tvShow.getStartDate());
//        intent.putExtra("country", tvShow.getCountry());
//        intent.putExtra("network", tvShow.getNetwork());
//        intent.putExtra("status", tvShow.getStatus());
        startActivity(intent);
    }
}
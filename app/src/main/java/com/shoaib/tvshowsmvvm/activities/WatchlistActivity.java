package com.shoaib.tvshowsmvvm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.shoaib.tvshowsmvvm.R;
import com.shoaib.tvshowsmvvm.adapters.WatchListAdapter;
import com.shoaib.tvshowsmvvm.databinding.ActivityWatchlistBinding;
import com.shoaib.tvshowsmvvm.listeners.WatchListListener;
import com.shoaib.tvshowsmvvm.models.TVShow;
import com.shoaib.tvshowsmvvm.utilities.TempDataHolder;
import com.shoaib.tvshowsmvvm.viewModels.WatchlistViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchlistActivity extends AppCompatActivity implements WatchListListener {

    private ActivityWatchlistBinding activityWatchlistBinding;
    private WatchlistViewModel watchlistViewModel;
    private WatchListAdapter watchListAdapter;
    private List<TVShow> watchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        activityWatchlistBinding = DataBindingUtil.setContentView(this, R.layout.activity_watchlist);
        doInitialization();
    }

    private void doInitialization() {
        watchlistViewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);
        activityWatchlistBinding.imageBack.setOnClickListener(v -> onBackPressed());
        watchList = new ArrayList<>();
        loadWatchList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TempDataHolder.IS_WATCHLIST_UPDATED) {
            loadWatchList();
            TempDataHolder.IS_WATCHLIST_UPDATED = false;
        }
    }

    private void loadWatchList() {
        activityWatchlistBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchlistViewModel.loadWatchList().subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShows -> {
                    activityWatchlistBinding.setIsLoading(false);
                    if (watchList.size() > 0) {
                        watchList.clear();
                    }
                    watchList.addAll(tvShows);
                    watchListAdapter = new WatchListAdapter(watchList, this);
                    activityWatchlistBinding.watchListRecyclerView.setAdapter(watchListAdapter);
                    activityWatchlistBinding.watchListRecyclerView.setVisibility(View.VISIBLE);
                    compositeDisposable.dispose();
                }));
    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);
    }

    @Override
    public void removeTVShowFromWatchlist(TVShow tvShow, int position) {

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchlistViewModel.removeFromWatchList(tvShow)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    watchList.remove(position);
                    watchListAdapter.notifyItemRemoved(position);
                    watchListAdapter.notifyItemRangeChanged(position, watchListAdapter.getItemCount());
                    compositeDisposable.dispose();
                }));
    }
}
package com.shoaib.tvshowsmvvm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.shoaib.tvshowsmvvm.R;
import com.shoaib.tvshowsmvvm.databinding.ActivityTVShowDetailsBinding;
import com.shoaib.tvshowsmvvm.viewModels.MostPopularTVShowsViewModel;
import com.shoaib.tvshowsmvvm.viewModels.TVShowDetailViewModel;

public class TVShowDetailsActivity extends AppCompatActivity {

    private ActivityTVShowDetailsBinding activityTVShowDetailsBinding;
    private TVShowDetailViewModel tvShowDetailViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTVShowDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_t_v_show_details);
        doInitialization();
    }


    private void doInitialization(){
        tvShowDetailViewModel = new ViewModelProvider(this).get(TVShowDetailViewModel.class);
        getTVShowDetails();
    }

    private void getTVShowDetails(){
        activityTVShowDetailsBinding.setIsLoading(true);
        String tvShowId = String.valueOf(getIntent().getIntExtra("id",-1));
        tvShowDetailViewModel.getTVShowDetailsVM(tvShowId).observe(this,tvShowDetailsResponse -> {
            activityTVShowDetailsBinding.setIsLoading(false);
            Toast.makeText(this, tvShowDetailsResponse.getTvShowDetails().getUrl(), Toast.LENGTH_SHORT).show();
        });
    }
}
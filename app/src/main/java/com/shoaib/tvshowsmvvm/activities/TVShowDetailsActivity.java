package com.shoaib.tvshowsmvvm.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shoaib.tvshowsmvvm.R;
import com.shoaib.tvshowsmvvm.adapters.EpisodesAdapter;
import com.shoaib.tvshowsmvvm.adapters.ImageSliderAdapter;
import com.shoaib.tvshowsmvvm.databinding.ActivityTVShowDetailsBinding;
import com.shoaib.tvshowsmvvm.databinding.LayoutEpisodesBottomSheetBinding;
import com.shoaib.tvshowsmvvm.models.TVShow;
import com.shoaib.tvshowsmvvm.utilities.TempDataHolder;
import com.shoaib.tvshowsmvvm.viewModels.TVShowDetailViewModel;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TVShowDetailsActivity extends AppCompatActivity {

    private ActivityTVShowDetailsBinding activityTVShowDetailsBinding;
    private TVShowDetailViewModel tvShowDetailViewModel;
    private BottomSheetDialog episodeBottomSheetDialog;
    private LayoutEpisodesBottomSheetBinding layoutEpisodesBottomSheetBinding;
    private TVShow tvShow;
    private Boolean isTVShowAvailableInWatchList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTVShowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_t_v_show_details);
        doInitialization();
    }


    private void doInitialization() {
        activityTVShowDetailsBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvShowDetailViewModel = new ViewModelProvider(this).get(TVShowDetailViewModel.class);
        tvShow = (TVShow) getIntent().getSerializableExtra("tvShow");
        checkTVSowInWatchList();
        getTVShowDetails();
    }

    private void checkTVSowInWatchList() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(tvShowDetailViewModel.getTVShowFromWatchlist(String.valueOf(tvShow.getId()))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShow1 -> {
                    isTVShowAvailableInWatchList = true;
                    activityTVShowDetailsBinding.imageAddWatchlist.setImageResource(R.drawable.ic_added);
                    compositeDisposable.dispose();
                }));
    }

    private void getTVShowDetails() {
        activityTVShowDetailsBinding.setIsLoading(true);
        String tvShowId = String.valueOf(tvShow.getId());
        tvShowDetailViewModel.getTVShowDetailsVM(tvShowId).observe(this, tvShowDetailsResponse -> {
            activityTVShowDetailsBinding.setIsLoading(false);
            if (tvShowDetailsResponse.getTvShowDetails() != null) {
                if (tvShowDetailsResponse.getTvShowDetails().getPictures() != null) {
                    loadImagesSliders(tvShowDetailsResponse.getTvShowDetails().getPictures());
                }
                activityTVShowDetailsBinding.setTvShowImageURL(tvShowDetailsResponse.getTvShowDetails().getImagePath());
                activityTVShowDetailsBinding.setTvShowName(tvShow.getName());
                activityTVShowDetailsBinding.setNetworkCountry(tvShow.getNetwork() + " (" + tvShow.getCountry() + ")"); //+" ("+"country"+")"
                activityTVShowDetailsBinding.setStartedDate(tvShow.getStartDate());
                activityTVShowDetailsBinding.textStartedDate.setVisibility(View.VISIBLE);
                activityTVShowDetailsBinding.setStatus(tvShow.getStatus());


                //HtmlCompat used to deal with HTML Tags inside Strings.
                activityTVShowDetailsBinding.textDescription.setText(String.valueOf(HtmlCompat.fromHtml(tvShowDetailsResponse.getTvShowDetails().getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY)));
                activityTVShowDetailsBinding.textReadMore.setVisibility(View.VISIBLE);
                activityTVShowDetailsBinding.textReadMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (activityTVShowDetailsBinding.textReadMore.getText().toString().equals("Read More")) {
                            activityTVShowDetailsBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                            activityTVShowDetailsBinding.textReadMore.setText(R.string.read_less);
                        } else {
                            activityTVShowDetailsBinding.textDescription.setMaxLines(4);
                            activityTVShowDetailsBinding.textReadMore.setText(R.string.read_more);
                            activityTVShowDetailsBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);

                        }
                    }
                });

                activityTVShowDetailsBinding.setRating(String.format(Locale.getDefault(), "%.2f", Double.parseDouble(tvShowDetailsResponse.getTvShowDetails().getRating())));

                if (tvShowDetailsResponse.getTvShowDetails().getGenres() != null) {
                    activityTVShowDetailsBinding.setGenre(tvShowDetailsResponse.getTvShowDetails().getGenres()[0]);
                } else {
                    activityTVShowDetailsBinding.setGenre("N/A");
                }
                activityTVShowDetailsBinding.setRuntime(tvShowDetailsResponse.getTvShowDetails().getRuntime() + "Min");
                activityTVShowDetailsBinding.viewDivider1.setVisibility(View.VISIBLE);
                activityTVShowDetailsBinding.layoutMisc.setVisibility(View.VISIBLE);
                activityTVShowDetailsBinding.viewDivider2.setVisibility(View.VISIBLE);
                activityTVShowDetailsBinding.icDot1.setVisibility(View.VISIBLE);
                activityTVShowDetailsBinding.icDot2.setVisibility(View.VISIBLE);
                activityTVShowDetailsBinding.icStar.setVisibility(View.VISIBLE);
                activityTVShowDetailsBinding.buttonWebsite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(tvShowDetailsResponse.getTvShowDetails().getUrl()));
                        startActivity(intent);
                    }
                });
                activityTVShowDetailsBinding.buttonEpisodes.setVisibility(View.VISIBLE);
                activityTVShowDetailsBinding.buttonWebsite.setVisibility(View.VISIBLE);

                activityTVShowDetailsBinding.buttonEpisodes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (episodeBottomSheetDialog == null) {
                            episodeBottomSheetDialog = new BottomSheetDialog(TVShowDetailsActivity.this);
                            layoutEpisodesBottomSheetBinding = DataBindingUtil.inflate(LayoutInflater.from(TVShowDetailsActivity.this),
                                    R.layout.layout_episodes_bottom_sheet,
                                    findViewById(R.id.episodeContainer),
                                    false);

                            episodeBottomSheetDialog.setContentView(layoutEpisodesBottomSheetBinding.getRoot()
                            );

                            layoutEpisodesBottomSheetBinding.episodeRecyclerView.setAdapter(
                                    new EpisodesAdapter(tvShowDetailsResponse.getTvShowDetails().getEpisodes())
                            );

                            layoutEpisodesBottomSheetBinding.setName(tvShow.getName());

                            layoutEpisodesBottomSheetBinding.imageClose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    episodeBottomSheetDialog.dismiss();
                                }
                            });

                        }

                        //---- Optional part ---- This will expand bottom sheet to full screen.
//                        FrameLayout frameLayout = episodeBottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
//                        if (frameLayout != null){
//                            BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
//                            bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
//                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                        }


                        episodeBottomSheetDialog.show();
                    }
                });
                activityTVShowDetailsBinding.imageAddWatchlist.setVisibility(View.VISIBLE);
                findViewById(R.id.imageAddWatchlist).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CompositeDisposable compositeDisposable = new CompositeDisposable();
                        if (isTVShowAvailableInWatchList) {
                            compositeDisposable.add(tvShowDetailViewModel.removeTVShowFromWatchList(tvShow)
                                    .subscribeOn(Schedulers.computation())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        isTVShowAvailableInWatchList = false;
                                        activityTVShowDetailsBinding.imageAddWatchlist.setImageResource(R.drawable.ic_watchlist);
                                        Toast.makeText(TVShowDetailsActivity.this, "Removed from watchlist", Toast.LENGTH_SHORT).show();
                                        compositeDisposable.dispose();
                                    }));
                        } else {
                            compositeDisposable.add(tvShowDetailViewModel.addToWatchList(tvShow)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        TempDataHolder.IS_WATCHLIST_UPDATED = true;
                                        activityTVShowDetailsBinding.imageAddWatchlist.setImageResource(R.drawable.ic_added);
                                        Toast.makeText(TVShowDetailsActivity.this, "Added to watchlist", Toast.LENGTH_SHORT).show();
                                        compositeDisposable.dispose();
                                    })
                            );
                        }
                    }
                });
            }
        });

    }

    private void loadImagesSliders(String[] sliderImages) {
        activityTVShowDetailsBinding.sliderViewPager.setOffscreenPageLimit(1);
        activityTVShowDetailsBinding.sliderViewPager.setAdapter(new ImageSliderAdapter(sliderImages));
        activityTVShowDetailsBinding.sliderViewPager.setVisibility(View.VISIBLE);
        activityTVShowDetailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);
        setupSliderIndicator(sliderImages.length);
        activityTVShowDetailsBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });
    }

    private void setupSliderIndicator(int count) {

        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 8, 0);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            activityTVShowDetailsBinding.layoutSliderIndicators.addView(indicators[i]);
        }
        activityTVShowDetailsBinding.layoutSliderIndicators.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);
    }

    private void setCurrentSliderIndicator(int position) {
        int childCount = activityTVShowDetailsBinding.layoutSliderIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) activityTVShowDetailsBinding.layoutSliderIndicators.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive));
            }
        }

    }
}
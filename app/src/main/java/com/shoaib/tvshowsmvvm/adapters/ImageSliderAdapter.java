package com.shoaib.tvshowsmvvm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shoaib.tvshowsmvvm.R;
import com.shoaib.tvshowsmvvm.databinding.ItemContainerSliderImageBinding;
import com.shoaib.tvshowsmvvm.models.TVShow;
import com.shoaib.tvshowsmvvm.models.TVShowDetails;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder> {

    private String[] sliderImages;
    private LayoutInflater layoutInflater;

    public ImageSliderAdapter(String[] sliderImages) {
        this.sliderImages = sliderImages;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerSliderImageBinding imageBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_container_slider_image,parent, false);
        return new ImageSliderViewHolder(imageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.bindImageSlider(sliderImages[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImages.length;
    }

    public class ImageSliderViewHolder extends RecyclerView.ViewHolder {
        private ItemContainerSliderImageBinding itemContainerSliderImageBinding;

        public ImageSliderViewHolder(@NonNull ItemContainerSliderImageBinding itemContainerSliderImageBinding) {
            super(itemContainerSliderImageBinding.getRoot());
            this.itemContainerSliderImageBinding = itemContainerSliderImageBinding;
        }

        public void bindImageSlider(String imageURL) {
            itemContainerSliderImageBinding.setImageURL(imageURL);
        }
    }
}

package com.shoaib.tvshowsmvvm.utilities;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BindingAdapters {

    //TODO: how BindingAdapters works with MVVM ?
    @BindingAdapter("android:imageURL")
    public static void setImageURl(ImageView imageView, String URL){
        try{
            imageView.setAlpha(0f);
            Picasso.get().load(URL).noFade().into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    imageView.animate().setDuration(300).alpha(1f).start();
                }

                @Override
                public void onError(Exception e) {

                }
            });

        }catch (Exception e){

        }
    }
}

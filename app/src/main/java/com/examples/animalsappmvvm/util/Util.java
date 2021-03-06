package com.examples.animalsappmvvm.util;

import android.content.Context;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.examples.animalsappmvvm.R;

public class Util {
    public static void loadImage(AppCompatImageView appCompatImageView, String url, CircularProgressDrawable circularProgressDrawable){
        RequestOptions options = new RequestOptions()
                .placeholder(circularProgressDrawable)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(appCompatImageView.getContext())
                .setDefaultRequestOptions(options)
                .load(url)
                .into(appCompatImageView);
    }

    public static CircularProgressDrawable getProgressDrawable(Context context){
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(10f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();
        return circularProgressDrawable;
    }

    @BindingAdapter("android:imageUrl")
    public static void loadImage(AppCompatImageView appCompatImageView, String url){
        loadImage(appCompatImageView, url, getProgressDrawable(appCompatImageView.getContext()));
    }
}

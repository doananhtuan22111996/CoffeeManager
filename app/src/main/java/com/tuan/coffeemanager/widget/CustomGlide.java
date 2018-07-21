package com.tuan.coffeemanager.widget;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class CustomGlide {
     public static void showImage(Context context, ImageView iv, String url){
         Glide.with(context).load(url).into(iv);
     }
}

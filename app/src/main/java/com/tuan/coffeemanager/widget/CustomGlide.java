package com.tuan.coffeemanager.widget;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tuan.coffeemanager.R;

public class CustomGlide {
     public static void showImage(Context context, ImageView iv, String url){
         if (url != null){
             Glide.with(context).load(url).into(iv);
         }else {
             Glide.with(context).load(context.getDrawable(R.drawable.coffee_menu_drink)).into(iv);
         }
     }
}

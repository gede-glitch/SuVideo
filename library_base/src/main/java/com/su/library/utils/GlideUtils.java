package com.su.library.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

public class GlideUtils {
    public static void loadImage(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

    public static void loadAvatar(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))    // 加载圆型图片
                .into(imageView);
    }
}

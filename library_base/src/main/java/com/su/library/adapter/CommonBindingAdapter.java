package com.su.library.adapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.su.library.base.R;

public class CommonBindingAdapter {
    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        if (url != null && !url.isEmpty()) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
        }
    }

    @BindingAdapter("imageAvatar")
    public static void loadAvatar(ImageView imageView, String url) {
        if (url != null && !url.isEmpty()) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .placeholder(R.mipmap.touxiang)
                    .error(R.mipmap.touxiang)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))    // 加载圆型图片
                    .into(imageView);
        }
    }
}

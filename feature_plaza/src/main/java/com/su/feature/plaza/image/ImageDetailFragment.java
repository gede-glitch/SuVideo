package com.su.feature.plaza.image;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.su.feature.plaza.R;
import com.su.feature.plaza.databinding.ItemImageDetailBinding;
import com.su.library.base.BaseFragment;
import com.su.library.base.BaseViewModel;
import com.su.library.config.ARouterPath;

@Route(path = ARouterPath.Plaza.FRAGMENT_IMAGE_DETAIL)
public class ImageDetailFragment extends BaseFragment<BaseViewModel, ItemImageDetailBinding> {
    @Autowired(name = ARouterPath.Plaza.KEY_IMAGE_URL)
    public String imgUrl;


    @Override
    protected int getBindingVariableId() {
        return 0;
    }

    @Override
    protected BaseViewModel getViewModel() {
        return new ViewModelProvider(this).get(BaseViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.item_image_detail;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
//        mDataBinding.setImgUrl(imgUrl);

        ProgressBar progressBar = mDataBinding.progressBar;
        ImageView imageView = mDataBinding.imageView;

        Glide.with(this)
                .load(imgUrl)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        imageView.setImageDrawable(resource);
                        imageView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}

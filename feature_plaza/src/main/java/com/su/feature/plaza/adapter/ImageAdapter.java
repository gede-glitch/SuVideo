package com.su.feature.plaza.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.su.feature.plaza.R;
import com.su.feature.plaza.databinding.ItemImageDetailBinding;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private final List<String> mImgUrls;

    public ImageAdapter(List<String> mImgUrls) {
        this.mImgUrls = mImgUrls;
    }

    @NonNull
    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageDetailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_image_detail,
                null, false);

        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ImageViewHolder holder, int position) {
        String s = mImgUrls.get(position);
        holder.binding.setImgUrl(s);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mImgUrls == null ? 0 : mImgUrls.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        private final ItemImageDetailBinding binding;

        public ImageViewHolder(ItemImageDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

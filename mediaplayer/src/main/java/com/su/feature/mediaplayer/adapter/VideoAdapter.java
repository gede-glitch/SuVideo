package com.su.feature.mediaplayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.su.data.video.bean.ResVideo;
import com.su.feature.mediaplayer.databinding.ItemVideoBinding;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<ResVideo> mVideos;

    private ItemClickListener mItemClickListener;
    private boolean mStyle;

    public VideoAdapter(ItemClickListener clickListener) {
        mVideos = new ArrayList<>();
        this.mItemClickListener = clickListener;
    }

    public void setVideos(List<ResVideo> mVideos) {
        this.mVideos = mVideos;
        notifyDataSetChanged();
    }

    public void setItemWhite(boolean isStyle) {
        this.mStyle = isStyle;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemVideoBinding binding = ItemVideoBinding.inflate(inflater, parent, false);

        return new VideoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        ResVideo video = mVideos.get(position);
        holder.binding.setVideo(video);
        holder.binding.setWhite(mStyle);
        holder.binding.executePendingBindings();  // 实时更新数据
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        public final ItemVideoBinding binding;

        public VideoViewHolder(ItemVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    ResVideo video = mVideos.get(position);
                    mItemClickListener.onVideoItemClick(video.getId());
                }
            });
        }
    }

    public interface ItemClickListener {
        void onVideoItemClick(int id);
    }
}

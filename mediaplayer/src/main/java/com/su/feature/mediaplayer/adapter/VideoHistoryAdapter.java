package com.su.feature.mediaplayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.su.feature.mediaplayer.databinding.ItemVideoHistoryBinding;
import com.su.feature.mediaplayer.db.VideoHistory;
import com.su.library.config.ARouterPath;

import java.util.ArrayList;
import java.util.List;

public class VideoHistoryAdapter extends RecyclerView.Adapter<VideoHistoryAdapter.ViewHolder> {
    private List<VideoHistory> mDatas = new ArrayList<>();
    private Boolean mSelectStatus;
    private ItemClickListener mListener;

    @NonNull
    @Override
    public VideoHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater from = LayoutInflater.from(parent.getContext());
        ItemVideoHistoryBinding binding = ItemVideoHistoryBinding.inflate(from, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHistoryAdapter.ViewHolder holder, int position) {
        VideoHistory videoHistory = mDatas.get(position);
        holder.binding.setHistroy(videoHistory);
        holder.binding.setIsSelectStatus(mSelectStatus);
        String tag = "#" + videoHistory.getTag();
        holder.binding.setTag(tag);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemVideoHistoryBinding binding;

        public ViewHolder(ItemVideoHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        VideoHistory videoHistory = mDatas.get(getLayoutPosition());
                        mListener.onItemClick(videoHistory.getVideoId());
                    }
                }
            });

            binding.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                    if (mListener != null) {
                        VideoHistory videoHistory = mDatas.get(getLayoutPosition());
                        mListener.onDelSelect(videoHistory, isChecked);
                    }
                }
            });
        }
    }
    public void updateSelectStatus(Boolean selectStatus) {
        mSelectStatus = selectStatus;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClick(int videoId);

        void onDelSelect(VideoHistory videoHistory, boolean isSelect);
    }

    public void setDatas(List<VideoHistory> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public void setListener(ItemClickListener mListener) {
        this.mListener = mListener;
    }
}

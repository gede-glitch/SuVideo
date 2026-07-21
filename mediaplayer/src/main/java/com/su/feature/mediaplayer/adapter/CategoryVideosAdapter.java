package com.su.feature.mediaplayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.su.data.video.bean.ResCategoryVideoDetail;
import com.su.feature.mediaplayer.databinding.ItemCategoryVideoBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryVideosAdapter extends RecyclerView.Adapter<CategoryVideosAdapter.ViewHolder> {
    private List<ResCategoryVideoDetail> mDatas = new ArrayList<>();
    private ItemClickListener mListener;

    @NonNull
    @Override
    public CategoryVideosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCategoryVideoBinding binding = ItemCategoryVideoBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVideosAdapter.ViewHolder holder, int position) {
        ResCategoryVideoDetail info = mDatas.get(position);
        holder.binding.setData(info);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCategoryVideoBinding binding;

        public ViewHolder(ItemCategoryVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ResCategoryVideoDetail info = mDatas.get(getLayoutPosition());
                    mListener.onItemClick(info.getId());
                }
            });
            binding.ivLikes.setOnClickListener(v -> {
                mListener.onLikeClick(getLayoutPosition());
            });
            binding.ivCollection.setOnClickListener(v -> {
                mListener.onCollectionClick(getLayoutPosition());
            });
            binding.ivComments.setOnClickListener(v -> {
                mListener.onCommentClick(getLayoutPosition());
            });
        }
    }

    public interface ItemClickListener {
        /**
         * 点击后传递被点击的视频id
         *
         * @param id
         */
        void onItemClick(int id);

        /**
         * @param position 传递数据在列表中的位置
         */
        void onLikeClick(int position);

        void onCollectionClick(int position);

        void onCommentClick(int position);
    }

    public void setListener(ItemClickListener mListener) {
        this.mListener = mListener;
    }

    public void setDatas(List<ResCategoryVideoDetail> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
}

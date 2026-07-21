package com.su.feature.find.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.su.feature.find.bean.ResThemeData;
import com.su.feature.find.databinding.ItemAnchorInfoBinding;

import java.util.List;

public class ThemeListAdapter extends RecyclerView.Adapter<ThemeListAdapter.ViewHolder> {
    private List<ResThemeData> mData;
    private onItemClickListener mListener;
    @NonNull
    @Override
    public ThemeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemAnchorInfoBinding inflate = ItemAnchorInfoBinding.inflate(inflater, parent, false);
        return new ThemeListAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeListAdapter.ViewHolder holder, int position) {
        ResThemeData resThemeData = mData.get(position);
        holder.binding.setData(resThemeData);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemAnchorInfoBinding binding;

        public ViewHolder(ItemAnchorInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.group1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击第一个视频时，获取对应item内的数据，并从数据内拿到下标为0的视频id
                    if (mData != null && mListener != null) {
                        ResThemeData resThemeData = mData.get(getLayoutPosition());
                        ResThemeData.ListsDTO listsDTO = resThemeData.getLists().get(0);
                        Integer id = listsDTO.getId();
                        mListener.onVideoClick(id);
                    }
                }
            });

            binding.group2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mData != null && mListener != null) {
                        ResThemeData resThemeData = mData.get(getLayoutPosition());
                        ResThemeData.ListsDTO listsDTO = resThemeData.getLists().get(1);
                        Integer id = listsDTO.getId();
                        mListener.onVideoClick(id);
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onVideoClick(int id);
    }

    public void setData(List<ResThemeData> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void setListener(onItemClickListener mListener) {
        this.mListener = mListener;
    }
}

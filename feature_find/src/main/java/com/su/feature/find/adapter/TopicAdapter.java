package com.su.feature.find.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.su.feature.find.bean.ResTopic;
import com.su.feature.find.databinding.ItemTopicInfoBinding;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {
    private List<ResTopic> mData = new ArrayList<>();

    @NonNull
    @Override
    public TopicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTopicInfoBinding binding = ItemTopicInfoBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicAdapter.ViewHolder holder, int position) {
        ResTopic resTopic = mData.get(position);
        holder.binding.setTopicInfo(resTopic);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData.isEmpty() ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemTopicInfoBinding binding;

        public ViewHolder(ItemTopicInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setData(List<ResTopic> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }
}

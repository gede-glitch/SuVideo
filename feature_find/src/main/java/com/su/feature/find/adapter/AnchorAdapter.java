package com.su.feature.find.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.PluralsRes;
import androidx.recyclerview.widget.RecyclerView;

import com.su.feature.find.bean.ResFindAnchor;
import com.su.feature.find.databinding.ItemAnchorBinding;

import java.util.List;

public class AnchorAdapter extends RecyclerView.Adapter<AnchorAdapter.ViewHolder> {

    private List<ResFindAnchor> mDatas;
    private OnItemClickListener mListener;
    public void setDatas(List<ResFindAnchor> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AnchorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemAnchorBinding binding = ItemAnchorBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AnchorAdapter.ViewHolder holder, int position) {
        ResFindAnchor resFindAnchor = mDatas.get(position);
        holder.binding.setData(resFindAnchor);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final com.su.feature.find.databinding.ItemAnchorBinding binding;

        public ViewHolder(ItemAnchorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onThemeListClick();
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onThemeListClick();
    }

    public void setListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }
}

package com.su.feature.find.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.su.data.video.bean.ResFindCategory;
import com.su.feature.find.databinding.ItemCategoryBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<ResFindCategory> mDatas;
    private CategoryListener mListener;

    public void setDatas(List<ResFindCategory> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        ResFindCategory resFindCategory = mDatas.get(position);
        holder.binding.setData(resFindCategory);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final com.su.feature.find.databinding.ItemCategoryBinding binding;

        public ViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v->{
                if (mListener != null) {
                    int position = getLayoutPosition();
                    mListener.onCategroyItemClick(mDatas.get(position));
                }
            });
        }
    }

    public interface CategoryListener {
        void onCategroyItemClick(ResFindCategory category);
    }

    public void setListener(CategoryListener mListener) {
        this.mListener = mListener;
    }
}

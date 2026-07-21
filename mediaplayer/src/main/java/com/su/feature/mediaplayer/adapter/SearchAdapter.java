package com.su.feature.mediaplayer.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.su.data.video.bean.ResVideoDetail;
import com.su.feature.mediaplayer.databinding.ItemSearchBinding;
import com.su.library.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索页、收藏页样式相同，因此共用一个adapter
 * 但是要注意，收藏列表接口返回的数据，不是
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<ResVideoDetail.ArchivesInfoDTO> mData = new ArrayList<>();
    private ItemClickListener mListener;

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSearchBinding binding = ItemSearchBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        ResVideoDetail.ArchivesInfoDTO archivesInfoDTO = mData.get(position);
        holder.binding.setSearch(archivesInfoDTO);
        holder.binding.setTag("# " + archivesInfoDTO.getTags());
        String s = TimeUtils.convertTimestampToDate(archivesInfoDTO.getCreatetime());
        holder.binding.setTime(s);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemSearchBinding binding;

        public ViewHolder(ItemSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        ResVideoDetail.ArchivesInfoDTO archivesInfoDTO = mData.get(getLayoutPosition());
                        mListener.onItemClick(archivesInfoDTO.getId());
                    }
                }
            });
        }
    }

    public interface ItemClickListener {
        void onItemClick(int id);
    }

    public void setData(List<ResVideoDetail.ArchivesInfoDTO> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void setListener(ItemClickListener mListener) {
        this.mListener = mListener;
    }
}

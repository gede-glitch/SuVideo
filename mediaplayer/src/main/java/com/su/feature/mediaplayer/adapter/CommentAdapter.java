package com.su.feature.mediaplayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.su.data.video.bean.ResComment;
import com.su.feature.mediaplayer.databinding.ItemCommentBinding;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    private List<ResComment> mComments = new ArrayList<>();
    private onItemClickListener mOnItemClickListener;
    @NonNull
    @Override
    public CommentAdapter.CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater from = LayoutInflater.from(parent.getContext());
        ItemCommentBinding inflate = ItemCommentBinding.inflate(from, parent, false);
        return new CommentHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentHolder holder, int position) {
        ResComment comments = mComments.get(position);
        holder.binding.setContents(comments);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mComments.isEmpty() ? 0 : mComments.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        private ItemCommentBinding binding;

        public CommentHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // 长按弹出删除
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ResComment comment = mComments.get(getLayoutPosition());
                    mOnItemClickListener.onItemLongClick(comment);
                    return false;
                }
            });
        }
    }

    public void setComments(List<ResComment> mComments) {
        this.mComments = mComments;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(onItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface onItemClickListener {
        void onItemLongClick(ResComment comments);
    }
}

package com.su.feature.mediaplayer.ui.comment.delcomment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.su.data.video.bean.ResComment;
import com.su.feature.mediaplayer.R;
import com.su.feature.mediaplayer.databinding.LayoutDeleteCommentBinding;
import com.su.feature.mediaplayer.ui.videodetail.VideoDetailViewModel;

public class DeleteCommentDialog extends DialogFragment {

    private LayoutDeleteCommentBinding binding;

    private ResComment mComment;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        VideoDetailViewModel videoDetailViewModel = new ViewModelProvider(requireActivity()).get(VideoDetailViewModel.class);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.layout_delete_comment,
                null, false);
        binding.setLifecycleOwner(getActivity());

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoDetailViewModel.deleteComment(mComment);
                dismiss();
            }
        });
        // 关联自定义布局
        View root = binding.getRoot();
        builder.setView(root);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(attributes);
        }
    }

    public void setComment(ResComment mComment) {
        this.mComment = mComment;
    }
}

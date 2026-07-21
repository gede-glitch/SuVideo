package com.su.feature.mediaplayer.ui.comment.report;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.su.feature.mediaplayer.R;
import com.su.feature.mediaplayer.databinding.LayoutCommentReportBinding;
import com.su.feature.mediaplayer.ui.videodetail.VideoDetailViewModel;

public class CommentReportPopupWindow extends PopupWindow {
    private OnPopupInteractionListener listener;
    private LayoutCommentReportBinding binding;

    public CommentReportPopupWindow(AppCompatActivity activity) {
        super(activity);
        init(activity);
    }

    private void init(AppCompatActivity activity) {
        VideoDetailViewModel videoDetailViewModel = new ViewModelProvider(activity).get(VideoDetailViewModel.class);
        binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.layout_comment_report, null, false);
        binding.setViewModel(videoDetailViewModel);
        binding.setLifecycleOwner(activity);

        setContentView(binding.getRoot());
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setBackgroundDrawable(null);

        binding.etChat.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                String text = binding.etChat.getText().toString().trim();
                if (text != null && text.length() > 0) {
                    if (listener != null) {
                        listener.onSendMessage(binding.etChat.getText().toString());
                    }
                    binding.etChat.getText().clear();
                }
                return true;
            } else {
                return false;
            }
        });
    }

    /**
     * 显示PopupWindow
     */
    public void showPopup(View anchor) {
        showAtLocation(anchor, android.view.Gravity.BOTTOM, 0, 0);
    }

    /**
     * 设置监听器
     */
    public void setOnPopupInteractionListener(OnPopupInteractionListener listener) {
        this.listener = listener;
    }


    public interface OnPopupInteractionListener {
        void onSendMessage(String message);
    }
}

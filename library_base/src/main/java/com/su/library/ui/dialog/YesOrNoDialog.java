package com.su.library.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.su.library.base.databinding.LayoutDialogYesOrNoBinding;

public class YesOrNoDialog extends DialogFragment {
    private Callback mCallback;

    public YesOrNoDialog(Callback callback) {
        this.mCallback = callback;
    }

    public static void showDialog(FragmentActivity activity, String title, String content, Callback callback) {
        YesOrNoDialog yesOrNoDialog = YesOrNoDialog.newInstance(title, content, callback);
        yesOrNoDialog.show(activity.getSupportFragmentManager(), "yesorno");
    }

    private static YesOrNoDialog newInstance(String title, String content, Callback callback) {
        Bundle args = new Bundle();
        args.putString("KEY_TITLE", title);
        args.putString("KEY_CONTENT", content);

        YesOrNoDialog fragment = new YesOrNoDialog(callback);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String title = bundle.getString("KEY_TITLE");
        String content = bundle.getString("KEY_CONTENT");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 查找布局
        LayoutInflater inflater = LayoutInflater.from(getContext());
        LayoutDialogYesOrNoBinding binding = LayoutDialogYesOrNoBinding.inflate(inflater);
        binding.tvTitle.setText(title);
        binding.tvContent.setText(content);
        binding.tvConfirm.setOnClickListener(v -> {
            mCallback.onConfirm();
            dismiss();
        });
        binding.tvCancel.setOnClickListener(v -> {
            mCallback.onCancel();
            dismiss();
        });
        builder.setView(binding.getRoot());
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(dialog -> {
            WindowManager.LayoutParams attributes = alertDialog.getWindow().getAttributes();
            attributes.gravity = Gravity.CENTER;
            attributes.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
            alertDialog.getWindow().setAttributes(attributes);
        });
        return alertDialog;
    }

    public interface Callback {
        void onConfirm();
        void onCancel();
    }
}

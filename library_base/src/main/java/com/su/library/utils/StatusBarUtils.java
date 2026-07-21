package com.su.library.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.ViewDataBinding;

import com.su.library.base.BaseActivity;
import com.su.library.base.R;

public class StatusBarUtils {
    public static void addStatusBarHeight2RootView(View rootView) {
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public static void addStatusBarHeight2Views(View rootView, View... views) {
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            for (int i = 0; i < views.length; i++) {
                View view = views[i];
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                Integer tag = (Integer) view.getTag();
                int originalTopMargin = tag != null ? tag : layoutParams.topMargin;
                if (tag == null) {
                    view.setTag(layoutParams.topMargin);
                }
                layoutParams.setMargins(layoutParams.leftMargin, originalTopMargin + systemBars.top,
                        layoutParams.rightMargin, layoutParams.bottomMargin);
                view.setLayoutParams(layoutParams);
            }
            return insets;
        });
    }

    public static void setImmerseStatusBar(Activity activity) {
        // 设置状态栏颜色为透明
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);

        View decorView = activity.getWindow().getDecorView();
        int flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |      // 稳定布局
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |     // 拓展内容到状态栏
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;       // 状态栏图标变成深色
        decorView.setSystemUiVisibility(flag);
    }
}

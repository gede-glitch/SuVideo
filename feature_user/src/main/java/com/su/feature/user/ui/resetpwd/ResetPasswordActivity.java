package com.su.feature.user.ui.resetpwd;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.su.feature.user.BR;
import com.su.feature.user.R;
import com.su.feature.user.databinding.ActivityResetPasswordBinding;
import com.su.library.base.BaseActivity;
import com.su.library.config.ARouterPath;
import com.su.library.utils.StatusBarUtils;

@Route(path = ARouterPath.User.ACTIVITY_SETTINGS_RESET_PASSWORD)
public class ResetPasswordActivity extends BaseActivity<ResetPasswordViewModel, ActivityResetPasswordBinding> {
    private ProgressBar mProgressBar;

    @Override
    protected void initView() {
        initProgressBar();
        initShow();
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());

        mViewModel.getCode().observe(this, s -> {
            mViewModel.updateEnableResetBtnStatus();
        });
        mViewModel.getPassword1().observe(this, s -> {
            mViewModel.updateEnableResetBtnStatus();
        });
        mViewModel.getPassword2().observe(this, s -> {
            mViewModel.updateEnableResetBtnStatus();
        });
    }

    private void initProgressBar() {
        mProgressBar = new ProgressBar(this);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        mProgressBar.setLayoutParams(layoutParams);
        mProgressBar.setVisibility(View.GONE);
        ConstraintLayout constraintLayout = (ConstraintLayout) mViewDataBinding.getRoot();
        constraintLayout.addView(mProgressBar);
    }

    private void initShow() {
        if (mViewModel != null) {
            mViewModel.getShowLoading().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean show) {
                    mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected ResetPasswordViewModel getViewModel() {
        return new ViewModelProvider(this).get(ResetPasswordViewModel.class);
    }

    @Override
    protected int getBindingVariableId() {
        return BR.resetPwdViewModel;
    }
}
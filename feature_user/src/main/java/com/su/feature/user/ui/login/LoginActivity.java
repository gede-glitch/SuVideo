package com.su.feature.user.ui.login;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.su.feature.user.BR;
import com.su.feature.user.R;
import com.su.feature.user.databinding.ActivityLoginBinding;
import com.su.library.base.BaseActivity;
import com.su.library.config.ARouterPath;
import com.su.library.utils.StatusBarUtils;

@Route(path = ARouterPath.User.ACTIVITY_USER)
public class LoginActivity extends BaseActivity<LoginViewModel, ActivityLoginBinding> {
    private static final String TAG = "LoginActivity";
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initProgressBar();
        initShow();
        initAgreementText();
        mViewDataBinding.ivQualifications.setOnClickListener(v -> {
            ARouter.getInstance().build(ARouterPath.User.ACTIVITY_AGREEMENT).navigation();
        });
        mViewDataBinding.ivBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void initAgreementText() {
        String string = "   请阅读并同意《用户协议》和《隐私政策》";
        SpannableString spannableString = new SpannableString(string);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                ARouter.getInstance().build(ARouterPath.User.ACTIVITY_AGREEMENT).navigation();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLACK);
                ds.setTypeface(Typeface.DEFAULT_BOLD);
            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                ARouter.getInstance().build(ARouterPath.User.ACTIVITY_AGREEMENT).navigation();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLACK);
                ds.setTypeface(Typeface.DEFAULT_BOLD);
            }
        };
        spannableString.setSpan(clickableSpan1, 10, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan2, 17, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mViewDataBinding.cbAgreen.setText(spannableString);
        mViewDataBinding.cbAgreen.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void initShow() {
        if (mViewModel != null) {
            mViewModel.getShowLoading().observe(this, show -> {
                mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            });
        }
    }

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2Views(mViewDataBinding.getRoot(), mViewDataBinding.ivSettings, mViewDataBinding.ivBack);

        mViewModel.mUserPhone.observe(this, mobile -> {
            mViewModel.isEnableLogin();
        });
        mViewModel.mUserVerityCode.observe(this, code -> {
            mViewModel.isEnableLogin();
        });
        mViewModel.getLoginSuccess().observe(this, isSuccess -> {
            if (isSuccess){
                finish();
            }
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

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginViewModel getViewModel() {
        return new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    protected int getBindingVariableId() {
        return BR.loginViewModel;
    }
}
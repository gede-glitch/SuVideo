package com.su.feature.user.ui.agreement;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.su.feature.user.R;
import com.su.feature.user.config.UserConfig;
import com.su.feature.user.databinding.ActivityAgreementBinding;
import com.su.library.base.BaseActivity;
import com.su.library.base.BaseViewModel;
import com.su.library.config.ARouterPath;
import com.su.library.utils.StatusBarUtils;

@Route(path = ARouterPath.User.ACTIVITY_AGREEMENT)
public class AgreementActivity extends BaseActivity<BaseViewModel, ActivityAgreementBinding> {

    private final String BASE_URL = "https://titok.fzqq.fun/";
    private final String PRIVATE_URL = BASE_URL + "agreement.html";//隐私政策、隐私概要
    private final String AGREEMENT_URL = BASE_URL + "UserAgreement.html";//用户协议
    private final String USER_INFO_URL = BASE_URL + "userinfomenus.html";//隐私政策、隐私概要

    private ProgressBar mProgressBar;

    @Autowired(name = UserConfig.AgreementType.KEY_AGREEMENT_TYPE)
    public int mType;

    @Override
    protected void initView() {
        initProgressBar();
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());
    }

    @Override
    protected void initData() {
        mViewModel.showLoading(true);
        mViewModel.getShowLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (mProgressBar != null) {
                    mProgressBar.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
                }
            }
        });

        mViewDataBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mViewModel.showLoading(false);
            }
        });
        String url = AGREEMENT_URL;
        switch (mType) {
            case UserConfig.AgreementType.VALUE_AGREEMENT:
                url = AGREEMENT_URL;
                break;
            case UserConfig.AgreementType.VALUE_SIMPLE_PRIVATE:
            case UserConfig.AgreementType.VALUE_PRIVATE:
                url = PRIVATE_URL;
                break;
            case UserConfig.AgreementType.VALUE_USER_INFO:
                url = USER_INFO_URL;
                break;
            default:
                url = AGREEMENT_URL;
                break;
        }
        mViewDataBinding.webView.loadUrl(url);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_agreement;
    }

    @Override
    protected BaseViewModel getViewModel() {
        return new ViewModelProvider(this).get(BaseViewModel.class);
    }

    @Override
    protected int getBindingVariableId() {
        return BR.agreementViewModel;
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
}
package com.su.feature.user.ui.aboutme;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.su.feature.user.BR;
import com.su.feature.user.R;
import com.su.feature.user.databinding.ActivityAboutMeBinding;
import com.su.library.base.BaseActivity;
import com.su.library.config.ARouterPath;
import com.su.library.utils.StatusBarUtils;

@Route(path = ARouterPath.User.ACTIVITY_SETTINGS_ABOUT_ME)
public class AboutMeActivity extends BaseActivity<AboutMeViewModel, ActivityAboutMeBinding> {

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_about_me;
    }

    @Override
    protected AboutMeViewModel getViewModel() {
        return new ViewModelProvider(this).get(AboutMeViewModel.class);
    }

    @Override
    protected int getBindingVariableId() {
        return BR.aboutMeViewModel;
    }
}
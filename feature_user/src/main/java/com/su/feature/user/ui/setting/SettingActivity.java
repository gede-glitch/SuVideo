package com.su.feature.user.ui.setting;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.su.feature.user.BR;
import com.su.feature.user.R;
import com.su.feature.user.config.UserConfig;
import com.su.feature.user.databinding.ActivitySettingBinding;
import com.su.library.base.BaseActivity;
import com.su.library.config.ARouterPath;
import com.su.library.ui.dialog.YesOrNoDialog;
import com.su.library.utils.StatusBarUtils;

@Route(path = ARouterPath.User.ACTIVITY_SETTINGS_PAGE)
public class SettingActivity extends BaseActivity<SettingViewModel, ActivitySettingBinding> {
    private static final String TAG = "SettingActivity";
    private ProgressBar mProgressBar;
    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());
        initProgressBar();
        initShow();
        mViewModel.getAction().observe(this, settingsAction -> {
            switch (settingsAction) {
                case FINISH:                    //关闭当前页面
                    finish();
                    break;
                case NAVIGATION_TO_ACCOUNT:     //账号与绑定
                    ARouter.getInstance().build(ARouterPath.User.ACTIVITY_SETTINGS_ACCOUNT).navigation();
                    break;
                case NAVIGATION_TO_PASSWORD:
                    ARouter.getInstance().build(ARouterPath.User.ACTIVITY_SETTINGS_RESET_PASSWORD).navigation();
                    break;
                case NAVIGATE_TO_PUSH_SETTING://推送设置
                    ARouter.getInstance().build(ARouterPath.User.ACTIVITY_SETTINGS_PUSH).navigation();
                    break;
                case NAVIGATE_TO_PLAY_SETTING://播放设置
                    ARouter.getInstance().build(ARouterPath.User.ACTIVITY_SETTINGS_PLAY).navigation();
                    break;
                case SHOW_CLEAR_CACHE_DIALOG://清除缓存
                    //显示清除缓存的弹窗
                    Log.i(TAG, "显示清除缓存的弹窗");
                    showClearCacheDialog();
                    break;
                case NAVIGATE_TO_USER_AGREEMENT://用户协议
                    Log.i(TAG, "用户协议");
                    ARouter.getInstance().build(ARouterPath.User.ACTIVITY_AGREEMENT)
                            .withInt(UserConfig.AgreementType.KEY_AGREEMENT_TYPE, UserConfig.AgreementType.VALUE_AGREEMENT)
                            .navigation();
                    break;
                case NAVIGATE_TO_SIMPLE_PRIVACY_POLICY://隐私概要
                    Log.i(TAG, "隐私概要");
                    ARouter.getInstance().build(ARouterPath.User.ACTIVITY_AGREEMENT)
                            .withInt(UserConfig.AgreementType.KEY_AGREEMENT_TYPE, UserConfig.AgreementType.VALUE_SIMPLE_PRIVATE)
                            .navigation();
                    break;
                case NAVIGATE_TO_PRIVACY_POLICY://隐私政策
                    Log.i(TAG, "隐私政策");
                    ARouter.getInstance().build(ARouterPath.User.ACTIVITY_AGREEMENT)
                            .withInt(UserConfig.AgreementType.KEY_AGREEMENT_TYPE, UserConfig.AgreementType.VALUE_PRIVATE)
                            .navigation();
                    break;
                case NAVIGATE_TO_PERMISSION_SETTING://隐私权限设置页面
                    break;
                case NAVIGATE_TO_USER_INFO_MENU://用户信息收集清单
                    Log.i(TAG, "用户信息收集清单");
                    ARouter.getInstance().build(ARouterPath.User.ACTIVITY_AGREEMENT)
                            .withInt(UserConfig.AgreementType.KEY_AGREEMENT_TYPE, UserConfig.AgreementType.VALUE_USER_INFO)
                            .navigation();
                    break;
                case NAVIGATE_TO_ABOUT_US://关于我们
                    ARouter.getInstance().build(ARouterPath.User.ACTIVITY_SETTINGS_ABOUT_ME).navigation();
                    break;
                case SHOW_LOGOUT_DIALOG://退出登录
                    Log.i(TAG, "退出登录");
                    showLogoutDialog();
                    break;
                case NAVIGATE_TO_LOGIN://跳转到登录页
                    ARouter.getInstance().build(ARouterPath.User.ACTIVITY_USER).navigation();
                    break;
            }
        });
    }

    private void showLogoutDialog() {
        YesOrNoDialog.showDialog(this, "提示", "是否退出当前登录", new YesOrNoDialog.Callback() {
            @Override
            public void onConfirm() {
                mViewModel.logout();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    private void showClearCacheDialog() {
        YesOrNoDialog.showDialog(this, "清除缓存", "是否清除当前APP相关缓存", new YesOrNoDialog.Callback() {
            @Override
            public void onConfirm() {
                mViewModel.clearCache();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected SettingViewModel getViewModel() {
        return new ViewModelProvider(this).get(SettingViewModel.class);
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
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
            mViewModel.getShowLoading().observe(this, show -> {
                mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            });
        }
    }
}
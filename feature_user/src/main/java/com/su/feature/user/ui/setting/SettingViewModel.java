package com.su.feature.user.ui.setting;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.su.feature.user.bean.ResEmpty;
import com.su.library.base.BaseViewModel;
import com.su.library.base.IRequestCallback;
import com.su.library.event.MessageEvent;
import com.su.network.bean.ResBase;

public class SettingViewModel extends BaseViewModel {
    private SettingModel mModel;
    // 手机号
    private MutableLiveData<String> mMobile = new MutableLiveData<>();
    // 缓存大小
    private MutableLiveData<String> mCacheSize = new MutableLiveData<>();

    // 是否显示退出登录的按钮
    private MutableLiveData<Integer> mExitLoginBtnVisibility = new MutableLiveData<>();

    private MutableLiveData<SettingsAction> mAction = new MutableLiveData<>();


    public SettingViewModel() {
        this.mModel = new SettingModel();
        boolean login = mModel.isLogin();
        mExitLoginBtnVisibility.setValue(login ? View.VISIBLE : View.INVISIBLE);
        refreshLoginStatus();
        refreshCacheSize();
    }

    /**
     * 第一次进入页面、以及退出登录，需要调用这个方法，刷新当前的页面显示
     */
    private void refreshLoginStatus() {
        boolean login = mModel.isLogin();
        mExitLoginBtnVisibility.setValue(login ? View.VISIBLE : View.INVISIBLE);
        mMobile.setValue(mModel.getMobile());
    }

    /**
     * 退出登录点击事件
     */
    public void onLogoutClick() {
        mAction.setValue(SettingsAction.SHOW_LOGOUT_DIALOG);
    }

    /**
     * 账号与绑定点击事件
     */
    public void onAccountBindClick() {
        if (mModel.isLogin()) {
            mAction.setValue(SettingsAction.NAVIGATION_TO_ACCOUNT);
        } else {
            mAction.setValue(SettingsAction.NAVIGATE_TO_LOGIN);
        }
    }

    /**
     * 设置密码点击事件
     */
    public void onPasswordSettingClick() {
        if (mModel.isLogin()) {
            mAction.setValue(SettingsAction.NAVIGATION_TO_PASSWORD);
        } else {
            mAction.setValue(SettingsAction.NAVIGATE_TO_LOGIN);
        }
    }

    /**
     * 推送设置点击事件
     */
    public void onPushSettingClick() {
        mAction.setValue(SettingsAction.NAVIGATE_TO_PUSH_SETTING);
    }

    /**
     * 播放设置点击事件
     */
    public void onPlaySettingClick() {
        mAction.setValue(SettingsAction.NAVIGATE_TO_PLAY_SETTING);
    }

    /**
     * 清除缓存点击事件
     */
    public void onClearCacheClick() {
        mAction.setValue(SettingsAction.SHOW_CLEAR_CACHE_DIALOG);
    }

    /**
     * 用户协议点击事件
     */
    public void onUserAgreementClick() {
        mAction.setValue(SettingsAction.NAVIGATE_TO_USER_AGREEMENT);
    }

    /**
     * 隐私概要
     */
    public void onSimplePrivacyPolicyClick() {
        mAction.setValue(SettingsAction.NAVIGATE_TO_SIMPLE_PRIVACY_POLICY);
    }

    /**
     * 隐私政策、点击事件
     */
    public void onPrivacyPolicyClick() {
        mAction.setValue(SettingsAction.NAVIGATE_TO_PRIVACY_POLICY);
    }

    /**
     * 权限设置
     */
    public void onPermissionSettingsClick() {
        mAction.setValue(SettingsAction.NAVIGATE_TO_PRIVACY_POLICY);
    }

    /**
     * 个人信息手机清单
     */
    public void onUserInfoMenusClick() {
        mAction.setValue(SettingsAction.NAVIGATE_TO_USER_INFO_MENU);
    }

    /**
     * 关于我们点击事件
     */
    public void onAboutUsClick() {
        mAction.setValue(SettingsAction.NAVIGATE_TO_ABOUT_US);
    }

    /**
     * 刷新缓存大小
     */
    public void refreshCacheSize() {
        String cacheSize = mModel.getCacheSize();
        mCacheSize.setValue(cacheSize);
    }

    /**
     * 清除缓存
     */
    public void clearCache() {
        showLoading(true);
        boolean isSuccess = mModel.clearCache();
        if (isSuccess) {
            refreshCacheSize();
            showLoading(false);
            showToast("清楚缓存成功");
        } else {
            showLoading(false);
            showToast("清楚缓存失败，请手动前往设置页面处理！");
        }
    }

    public MutableLiveData<String> getMobile() {
        return mMobile;
    }

    public MutableLiveData<String> getCacheSize() {
        return mCacheSize;
    }

    public MutableLiveData<Integer> getExitLoginBtnVisibility() {
        return mExitLoginBtnVisibility;
    }

    public MutableLiveData<SettingsAction> getAction() {
        return mAction;
    }

    public void logout() {
        showLoading(true);
        mModel.logout(new IRequestCallback<ResBase<ResEmpty>>() {
            @Override
            public void onLoadFinish(ResBase<ResEmpty> datas) {
                MessageEvent.LoginStatusEvent.post(false);
                refreshLoginStatus();
                showToast(datas.getMsg());
                showLoading(false);
                mAction.setValue(SettingsAction.FINISH);
            }

            @Override
            public void onLoadFail(int statusCode, String message) {
                showToast(message);
                showLoading(false);
            }
        });
    }

    public enum SettingsAction {
        FINISH,                                 // 关闭页面
        SHOW_LOGOUT_DIALOG,                     // 显示退出登录的弹窗
        NAVIGATION_TO_ACCOUNT,                  // 跳转到账号与绑定
        NAVIGATION_TO_PASSWORD,                 // 跳转到设置密码页
        NAVIGATE_TO_PUSH_SETTING,               // 跳转到推送设置
        NAVIGATE_TO_PLAY_SETTING,               // 跳转到播放设置
        SHOW_CLEAR_CACHE_DIALOG,                // 显示清除缓存对话框
        NAVIGATE_TO_USER_AGREEMENT,             // 跳转到用户协议
        NAVIGATE_TO_SIMPLE_PRIVACY_POLICY,      // 跳转到概要隐私政策
        NAVIGATE_TO_PRIVACY_POLICY,             // 跳转到隐私政策
        NAVIGATE_TO_PERMISSION_SETTING,         // 跳转到权限设置
        NAVIGATE_TO_USER_INFO_MENU,             // 跳转到用户信息清单
        NAVIGATE_TO_ABOUT_US,                   // 跳转到关于我们
        NAVIGATE_TO_LOGIN                       // 跳转到登录页
    }

}

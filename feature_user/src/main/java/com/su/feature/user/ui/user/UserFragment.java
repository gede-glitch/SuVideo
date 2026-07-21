package com.su.feature.user.ui.user;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.su.feature.user.BR;
import com.su.feature.user.R;
import com.su.feature.user.config.UserConfig;
import com.su.feature.user.databinding.LayoutFragmentUserBinding;
import com.su.library.base.BaseFragment;
import com.su.library.config.ARouterPath;
import com.su.library.event.MessageEvent;
import com.su.library.ui.dialog.YesOrNoDialog;
import com.su.library.utils.StatusBarUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Route(path = ARouterPath.User.FRAGMENT_USER)
public class UserFragment extends BaseFragment<UserViewModel, LayoutFragmentUserBinding> {
    private static final String TAG = "UserFragment";

    @Override
    protected int getBindingVariableId() {
        return BR.userViewModel;
    }

    @Override
    protected UserViewModel getViewModel() {
        return new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_fragment_user;
    }

    @Override
    protected void initView() {
        Log.i(TAG, "initView: ");
        StatusBarUtils.addStatusBarHeight2Views(mDataBinding.getRoot(), mDataBinding.ivSet);

        mViewModel.getAction().observe(getViewLifecycleOwner(), userCenterAction -> {
            switch (userCenterAction) {
                case NAVIGATE_TO_LOGIN:
                    ARouter.getInstance().build(ARouterPath.User.ACTIVITY_USER).navigation();
                    break;
                case NAVIGATION_TO_EDIT_INFO:
                    ARouter.getInstance().build(ARouterPath.User.ACTIVITY_SETTINGS_EDIT_USER_INFO).navigation();
                    Log.i(TAG, "用户信息编辑页面");
                    break;
                case NAVIGATION_TO_RECORD:
                    Log.i(TAG, "播放记录");
                    ARouter.getInstance().build(ARouterPath.Video.ACTIVITY_PLAYRECORD).navigation();
                    break;
                case SHOW_LOGOUT_DIALOG:
                    showLogoutDialog();
                    break;
                case NAVIGATE_TO_SETTINGS:
                    ARouter.getInstance().build(ARouterPath.User.ACTIVITY_SETTINGS_PAGE).navigation();
                    break;
                case NAVIGATE_TO_USER_INFO_MENU:
                    ARouter.getInstance().build(ARouterPath.User.ACTIVITY_AGREEMENT)
                            .withInt(UserConfig.AgreementType.KEY_AGREEMENT_TYPE, UserConfig.AgreementType.VALUE_USER_INFO)
                            .navigation();
                    break;
                case NAVIGATION_TO_COLLECTION:
                    Log.i(TAG, "收藏页面");
                    break;
            }
        });
    }

    private void showLogoutDialog() {
        YesOrNoDialog.showDialog(getActivity(), "提示", "是否退出当前账号？",
                new YesOrNoDialog.Callback() {
                    @Override
                    public void onConfirm() {
                        mViewModel.logout();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    @Override
    protected void initData() {

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent.LoginStatusEvent event) {
        Log.i(TAG, "onMessageEvent: isLogin" + event.isLogin());
        boolean isLogin = event.isLogin();
        mViewModel.LoadUserInfo(isLogin);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}

package com.su.feature.user.ui.user;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.su.feature.user.bean.ResEmpty;
import com.su.library.base.BaseViewModel;
import com.su.library.base.IRequestCallback;
import com.su.library.bean.ResUser;
import com.su.library.bean.UserInfo;
import com.su.library.event.MessageEvent;
import com.su.network.bean.ResBase;

public class UserViewModel extends BaseViewModel {
    private final UserModel mModel;

    private MutableLiveData<String> mAvatar = new MutableLiveData<>();
    private MutableLiveData<String> mNickName = new MutableLiveData<>();
    private MutableLiveData<String> mBio = new MutableLiveData<>();
    private MutableLiveData<String> mFollow = new MutableLiveData<>();
    private MutableLiveData<String> mFans = new MutableLiveData<>();
    private MutableLiveData<String> mMedal = new MutableLiveData<>();
    //是否显示退出登录按钮，默认不显示
    private MutableLiveData<Integer> mShowLogoutBtn = new MutableLiveData<>(View.INVISIBLE);
    private MutableLiveData<UserCenterAction> mAction = new MutableLiveData<>();

    public UserViewModel() {
        mModel = new UserModel();
        boolean login = mModel.isLogin();
        LoadUserInfo(login);
    }

    public void LoadUserInfo(boolean login) {
        if (login) {
            showLoading(true);
            mModel.loadUserInfo(new IRequestCallback<ResUser>() {
                @Override
                public void onLoadFinish(ResUser datas) {
                    showLoading(false);
                    updateUserInfo(datas);
                }

                @Override
                public void onLoadFail(int statusCode, String message) {
                    showLoading(false);
                    notUpdateUserInfo();
                }
            });
            mShowLogoutBtn.setValue(View.VISIBLE);
        } else {
            showLoading(false);
            notUpdateUserInfo();
        }
    }

    private void notUpdateUserInfo() {
        ResUser user = new ResUser();
        user.setUser(new UserInfo());
        updateUserInfo(user);

        mShowLogoutBtn.setValue(View.INVISIBLE);
    }

    private void updateUserInfo(ResUser userInfo) {
        String avatar = userInfo.getUser().getAvatar();
        if (avatar != null && !avatar.isEmpty()) {
            mAvatar.setValue(avatar);
        } else {
            mAvatar.setValue(null);
        }

        String nickname = userInfo.getUser().getNickname();
        if (nickname != null && !nickname.isEmpty()) {
            mNickName.setValue(nickname);
        } else {
            mNickName.setValue("请先登录");
        }

        String bio = userInfo.getUser().getBio();
        if (bio != null && !bio.isEmpty()) {
            mBio.setValue(bio);
        } else {
            mBio.setValue("用户签名");
        }

        Integer fans = userInfo.getFans();
        if (fans != null && fans >= 0) {
            mFans.setValue(fans + " 粉丝");
        } else {
            mFans.setValue("0 粉丝");
        }

        Integer medal = userInfo.getMedal();
        if (medal != null && medal >= 0) {
            mMedal.setValue(medal + " 奖章");
        } else {
            mMedal.setValue("0 奖章");
        }

        Integer follow = userInfo.getFollow();
        if (follow != null && follow >= 0) {
            mFollow.setValue(follow + " 关注");
        } else {
            mFollow.setValue("0 关注");
        }
    }

    /**
     * 编辑资料
     */
    public void onEditUserInfoClick() {
        boolean login = mModel.isLogin();
        mAction.setValue(login ? UserCenterAction.NAVIGATION_TO_EDIT_INFO : UserCenterAction.NAVIGATE_TO_LOGIN);
    }

    public void onCollectionClick() {
        boolean login = mModel.isLogin();
        mAction.setValue(login ? UserCenterAction.NAVIGATION_TO_COLLECTION : UserCenterAction.NAVIGATE_TO_LOGIN);
    }

    /**
     * 播放记录页
     */
    public void onRecordClick() {
        mAction.setValue(UserCenterAction.NAVIGATION_TO_RECORD);
    }

    /**
     * 退出登录
     */
    public void onLogoutClick() {
        mAction.setValue(UserCenterAction.SHOW_LOGOUT_DIALOG);
    }

    public void onUserInfoMenuClick() {
        mAction.setValue(UserCenterAction.NAVIGATE_TO_USER_INFO_MENU);
    }

    /**
     * 设置页
     */
    public void onSettingsClick() {
        mAction.setValue(UserCenterAction.NAVIGATE_TO_SETTINGS);
    }

    public MutableLiveData<String> getAvatar() {
        return mAvatar;
    }

    public MutableLiveData<String> getNickName() {
        return mNickName;
    }

    public MutableLiveData<String> getBio() {
        return mBio;
    }

    public MutableLiveData<String> getFollow() {
        return mFollow;
    }

    public MutableLiveData<String> getFans() {
        return mFans;
    }

    public MutableLiveData<String> getMedal() {
        return mMedal;
    }

    public MutableLiveData<Integer> getShowLogoutBtn() {
        return mShowLogoutBtn;
    }

    public MutableLiveData<UserCenterAction> getAction() {
        return mAction;
    }

    public void logout() {
        showLoading(true);
        mModel.logout(new IRequestCallback<ResBase<ResEmpty>>() {
            @Override
            public void onLoadFinish(ResBase<ResEmpty> datas) {
                MessageEvent.LoginStatusEvent.post(false);
                showLoading(false);
                showToast(datas.getMsg());
            }

            @Override
            public void onLoadFail(int statusCode, String message) {
                showLoading(false);
                showToast(message);
            }
        });
    }

    public enum UserCenterAction {
        SHOW_LOGOUT_DIALOG,         // 显示退出登录的弹窗
        NAVIGATION_TO_EDIT_INFO,   // 跳转到账用户信息编辑
        NAVIGATION_TO_COLLECTION,// 跳转到收藏列表页
        NAVIGATION_TO_RECORD,// 跳转到播放记录页
        NAVIGATE_TO_LOGIN,   // 跳转到登录页
        NAVIGATE_TO_SETTINGS,   // 跳转到设置页
        NAVIGATE_TO_USER_INFO_MENU   // 跳转到用户信息收公示
    }
}

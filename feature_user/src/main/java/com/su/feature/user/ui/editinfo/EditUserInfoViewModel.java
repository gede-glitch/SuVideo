package com.su.feature.user.ui.editinfo;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.su.feature.user.bean.ResUpload;
import com.su.library.base.BaseViewModel;
import com.su.library.base.IRequestCallback;
import com.su.library.bean.UserInfo;
import com.su.library.event.MessageEvent;
import com.su.network.bean.ResBase;

public class EditUserInfoViewModel extends BaseViewModel {
    private final EditUserInfoModel mModel;
    private MutableLiveData<String> mNickName = new MutableLiveData<>();//当前昵称
    private MutableLiveData<String> mBio = new MutableLiveData<>();//当前简介
    private MutableLiveData<String> mAvatarUrl = new MutableLiveData<>();//当前头像url地址
    private MutableLiveData<EditUserAction> mAction = new MutableLiveData<>();

    public EditUserInfoViewModel() {
        this.mModel = new EditUserInfoModel();
        refresh();
    }

    /**
     * 刷新显示
     */
    private void refresh() {
        if (mModel.isLogin()) {
            UserInfo userInfo = mModel.getUserInfo();
            mNickName.setValue(userInfo.getNickname());
            mBio.setValue(userInfo.getBio());
            mAvatarUrl.setValue(userInfo.getAvatar());
        } else {
            mNickName.setValue(null);
            mBio.setValue(null);
            mAvatarUrl.setValue(null);
        }
    }

    /**
     * 保存最新的用户信息
     */
    public void onSaveUserInfo() {
        if (isChange()) {
            showLoading(true);
            String avatarUrlValue = mAvatarUrl.getValue();
            String nickNameValue = mNickName.getValue();
            String bioValue = mBio.getValue();
            mModel.updateUserInfo(avatarUrlValue, nickNameValue, bioValue, new IRequestCallback<ResBase>() {
                @Override
                public void onLoadFinish(ResBase datas) {
                    showLoading(false);
                    MessageEvent.LoginStatusEvent.post(true);
                    mAction.setValue(EditUserAction.FINISH);
                }

                @Override
                public void onLoadFail(int statusCode, String message) {
                    showLoading(false);
                    showToast(message);
                }
            });
        }
    }

    /**
     * 更换头像
     */
    public void onAvatarSelectClick() {
        mAction.setValue(EditUserAction.SHOW_AVATAR_SELECT_DIALOG);
    }

    public boolean isChange() {
        boolean change = false;
        UserInfo userInfo = mModel.getUserInfo();
        String value = mAvatarUrl.getValue();
        if (value != null && !value.equals(userInfo.getAvatar())) {
            change = true;
        }
        String nickName = mNickName.getValue();
        if (nickName != null && !nickName.equals(userInfo.getNickname())) {
            change = true;
        }
        String value1 = mBio.getValue();
        if (value1 != null && !value1.equals(userInfo.getBio())) {
            change = true;
        }
        return change;
    }

    /**
     * 枚举
     */
    public enum EditUserAction {
        FINISH,                      // 关闭页面
        SHOW_AVATAR_SELECT_DIALOG
    }

    public void setBio(MutableLiveData<String> mBio) {
        this.mBio = mBio;
    }

    public void setNickName(MutableLiveData<String> mNickName) {
        this.mNickName = mNickName;
    }

    public MutableLiveData<String> getNickName() {
        return mNickName;
    }

    public MutableLiveData<String> getBio() {
        return mBio;
    }

    public void setAvatarUrl(MutableLiveData<String> mAvatarUrl) {
        this.mAvatarUrl = mAvatarUrl;
    }

    public MutableLiveData<String> getAvatarUrl() {
        return mAvatarUrl;
    }

    public MutableLiveData<EditUserAction> getAction() {
        return mAction;
    }

    public void uploadAvatar(Uri uri) {
        showLoading(true);
        mModel.updateFile(uri, new IRequestCallback<ResUpload>() {
            @Override
            public void onLoadFinish(ResUpload datas) {
                showLoading(false);
                mAvatarUrl.setValue(datas.getFullurl());
            }

            @Override
            public void onLoadFail(int statusCode, String message) {
                showLoading(false);
                showToast(message);
            }
        });
    }
}

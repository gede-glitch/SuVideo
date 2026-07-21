package com.su.feature.user.ui.user;

import com.su.feature.user.api.UserApiServiceProvider;
import com.su.feature.user.bean.ResEmpty;
import com.su.library.base.IRequestCallback;
import com.su.library.bean.ResUser;
import com.su.library.config.ErrorStatusConfig;
import com.su.library.manager.UserManager;
import com.su.network.ApiCall;
import com.su.network.bean.ResBase;

import retrofit2.Call;

public class UserModel {
    public boolean isLogin() {
        return UserManager.getInstance().isLogin();
    }

    /**
     * 获取用户信息
     *
     * @param callback
     */
    public void loadUserInfo(IRequestCallback<ResUser> callback) {
        if (isLogin()) {
            ResUser userInfo = UserManager.getInstance().getUserInfo();
            if (userInfo != null) {
                callback.onLoadFinish(userInfo);
            } else {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "The user is not logged in");
            }
        } else {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "The user is not logged in");
        }
    }

    /**
     * 退出登录
     */
    public void logout(IRequestCallback<ResBase<ResEmpty>> callback) {
        String token = UserManager.getInstance().getToken();
        Call<ResBase<ResEmpty>> logoutCall = UserApiServiceProvider.getUserApiService().logout(token);
        ApiCall.enqueueCall(logoutCall, new ApiCall.SimpleCallback<ResBase<ResEmpty>>() {
            @Override
            public void onSuccess(ResBase<ResEmpty> data) {
                if (data.getCode() == 1) {
                    UserManager.getInstance().loginOut();
                    callback.onLoadFinish(data);
                }
            }

            @Override
            public void onEmpty() {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_EMPTY, "server is empty");
            }

            @Override
            public void onServerError(String message) {
                callback.onLoadFail(ErrorStatusConfig.ERROR_SERVER_REQUEST, message);
            }

            @Override
            public void onNetworkError() {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL, "network is error");
            }
        });
    }


}

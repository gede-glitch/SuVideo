package com.su.feature.user.ui.login;

import com.su.feature.user.api.UserApiServiceProvider;
import com.su.feature.user.bean.ReqMobileLogin;
import com.su.feature.user.bean.ReqSendSmsCode;
import com.su.feature.user.bean.ResEmpty;
import com.su.feature.user.bean.ResMobileLogin;
import com.su.library.bean.ResUser;
import com.su.library.base.IRequestCallback;
import com.su.library.config.ErrorStatusConfig;
import com.su.library.manager.UserManager;
import com.su.network.ApiCall;
import com.su.network.bean.ResBase;

import retrofit2.Call;

public class LoginModel {

    /**
     * 发送验证码
     *
     * @param mobile   手机号
     * @param callback 回调
     */
    public void sendSmsCode(String mobile, IRequestCallback<ResBase<ResEmpty>> callback) {
        ReqSendSmsCode reqSendSmsCode = new ReqSendSmsCode(mobile, "mobilelogin");
        Call<ResBase<ResEmpty>> resBaseCall = UserApiServiceProvider.getUserApiService().sendSmsCode(reqSendSmsCode);
        ApiCall.enqueueCall(resBaseCall, new ApiCall.SimpleCallback<ResBase<ResEmpty>>() {
            @Override
            public void onSuccess(ResBase<ResEmpty> data) {
                callback.onLoadFinish(data);
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

    /**
     * 手机号登录
     *
     * @param mobile   手机号
     * @param code     验证码
     * @param callback 回调
     */
    public void mobileLogin(String mobile, String code, IRequestCallback<ResBase<ResMobileLogin>> callback) {
        ReqMobileLogin reqMobileLogin = new ReqMobileLogin(mobile, code);
        Call<ResBase<ResMobileLogin>> resBaseCall = UserApiServiceProvider.getUserApiService().mobileLogin(reqMobileLogin);
        ApiCall.enqueueCall(resBaseCall, new ApiCall.SimpleCallback<ResBase<ResMobileLogin>>() {
            @Override
            public void onSuccess(ResBase<ResMobileLogin> data) {
                callback.onLoadFinish(data);

                String token = data.getData().getToken();
                UserManager instance = UserManager.getInstance();
                instance.saveToken(token);
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

    public void getUserInfo(String userId, IRequestCallback<ResBase<ResUser>> callback) {
        Call<ResBase<ResUser>> call = UserApiServiceProvider.getUserApiService().getUserInfo(userId, "archives");
        ApiCall.enqueueCall(call, new ApiCall.SimpleCallback<ResBase<ResUser>>() {
            @Override
            public void onSuccess(ResBase<ResUser> data) {
                ResUser resUser = data.getData();
                if (resUser != null) {
                    UserManager.getInstance().saveUserInfo(data.getData());
                    callback.onLoadFinish(data);
                }else {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_SERVER_REQUEST, "用户获取信息失败");
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

package com.su.feature.user.ui.resetpwd;

import com.su.feature.user.api.UserApiServiceProvider;
import com.su.feature.user.bean.ReqResetPwd;
import com.su.feature.user.bean.ReqSendSmsCode;
import com.su.feature.user.bean.ResEmpty;
import com.su.library.base.IRequestCallback;
import com.su.library.config.ErrorStatusConfig;
import com.su.library.manager.UserManager;
import com.su.network.ApiCall;
import com.su.network.bean.ResBase;

import retrofit2.Call;

public class ResetPasswordModel {
    public boolean isLogin() {
        return UserManager.getInstance().isLogin();
    }

    /**
     * 如果未登录 返回null
     *
     * @return 返回手机号
     */
    public String getMobile() {
        if (isLogin()) {
            String mobile = UserManager.getInstance().getUserInfo().getUser().getUsername();
            return mobile.substring(0, 3) +
                    "****" +
                    mobile.substring(7);
        }
        return null;
    }

    /**
     * 发送验证码
     *
     * @param callback
     */
    public void sendSmsCode(IRequestCallback<ResBase<ResEmpty>> callback) {
        String mobile = UserManager.getInstance().getUserInfo().getUser().getUsername();
        ReqSendSmsCode smsCode = new ReqSendSmsCode(mobile, "resetpwd");
        Call<ResBase<ResEmpty>> resBaseCall = UserApiServiceProvider.getUserApiService().sendSmsCode(smsCode);
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

    public void resetPassword(String password, String code, IRequestCallback<ResBase<ResEmpty>> callback) {
        String token = UserManager.getInstance().getToken();
        String mobile = UserManager.getInstance().getUserInfo().getUser().getUsername();
        ReqResetPwd reqResetPwd = new ReqResetPwd(password, mobile, code);
        Call<ResBase<ResEmpty>> resBaseCall = UserApiServiceProvider.getUserApiService().resetPassword(token, reqResetPwd);
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
}

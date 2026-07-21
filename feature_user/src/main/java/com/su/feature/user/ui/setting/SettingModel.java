package com.su.feature.user.ui.setting;

import com.su.feature.user.api.UserApiServiceProvider;
import com.su.feature.user.bean.ResEmpty;
import com.su.library.base.IRequestCallback;
import com.su.library.config.ErrorStatusConfig;
import com.su.library.manager.UserManager;
import com.su.library.utils.CacheUtils;
import com.su.network.ApiCall;
import com.su.network.bean.ResBase;

import retrofit2.Call;

public class SettingModel {
    public boolean isLogin() {
        return UserManager.getInstance().isLogin();
    }
    /**
     * 获取缓存大小
     *
     * @return
     */
    public String getCacheSize() {
        return CacheUtils.getTotalCacheSize();
    }

    /**
     * 清除缓存
     */
    public boolean clearCache() {
        boolean b = CacheUtils.clearAppCache();
        boolean b1 = CacheUtils.clearExternalCache();

        return b && b1;
    }

    /**
     * 如果未登录 返回null
     *
     * @return 返回手机号
     */
    public String getMobile() {
        if (isLogin()) {
            String mobile = UserManager.getInstance().getUserInfo().getUser().getUsername();
            //把username中间的4位替换成****
            return mobile.substring(0, 3) +
                    "****" +
                    mobile.substring(7);//返回手机号，userName就是手机号
        }
        return null;
    }

    /**
     * 退出登录
     */
    public void logout(IRequestCallback<ResBase<ResEmpty>> callback) {
        String token = UserManager.getInstance().getToken();
        Call<ResBase<ResEmpty>> call = UserApiServiceProvider.getUserApiService().logout(token);
        ApiCall.enqueueCall(call, new ApiCall.SimpleCallback<ResBase<ResEmpty>>() {
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
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL, "server network is error");
            }
        });
    }
}

package com.su.feature.find.ui.themelist;

import com.su.feature.find.api.FindApiServiceProvider;
import com.su.feature.find.bean.ResThemeData;
import com.su.library.base.IRequestCallback;
import com.su.library.config.ErrorStatusConfig;
import com.su.network.ApiCall;
import com.su.network.bean.ResBase;

import java.util.List;

import retrofit2.Call;

public class ThemeListModel {
    public void requestData(IRequestCallback<List<ResThemeData>> callback) {
        Call<ResBase<List<ResThemeData>>> anchor = FindApiServiceProvider.getApiService().getAnchor();
        ApiCall.enqueueCall(anchor, new ApiCall.SimpleCallback<ResBase<List<ResThemeData>>>() {
            @Override
            public void onSuccess(ResBase<List<ResThemeData>> data) {
                callback.onLoadFinish(data.getData());
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

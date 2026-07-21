package com.su.feature.find.ui.find;

import com.su.feature.find.api.FindApiServiceProvider;
import com.su.feature.find.bean.ResFind;
import com.su.library.base.IRequestCallback;
import com.su.library.config.ErrorStatusConfig;
import com.su.network.ApiCall;
import com.su.network.bean.ResBase;

import retrofit2.Call;

public class FindModel {
    public void loadFindData(IRequestCallback<ResFind> callback) {
        Call<ResBase<ResFind>> call = FindApiServiceProvider.getApiService().getFindData();
        ApiCall.enqueueCall(call, new ApiCall.SimpleCallback<ResBase<ResFind>>() {
            @Override
            public void onSuccess(ResBase<ResFind> result) {
                callback.onLoadFinish(result.getData());
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

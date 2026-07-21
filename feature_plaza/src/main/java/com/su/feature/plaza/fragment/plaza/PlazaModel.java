package com.su.feature.plaza.fragment.plaza;

import com.su.feature.plaza.api.PlazaApiService;
import com.su.feature.plaza.api.PlazaApiServiceProvider;
import com.su.feature.plaza.bean.ResPlaza;
import com.su.library.base.IRequestCallback;
import com.su.library.config.ErrorStatusConfig;
import com.su.library.list.IListListener;
import com.su.network.ApiCall;
import com.su.network.bean.ResBase;

import java.util.List;

import retrofit2.Call;

public class PlazaModel {

    public void requestDatas(IRequestCallback<List<ResPlaza>> callback) {
        Call<ResBase<List<ResPlaza>>> call = PlazaApiServiceProvider.getApiService().getPlaza();
        ApiCall.enqueueCall(call, new ApiCall.SimpleCallback<ResBase<List<ResPlaza>>>() {
            @Override
            public void onSuccess(ResBase<List<ResPlaza>> data) {
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

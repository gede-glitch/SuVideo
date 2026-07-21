package com.su.feature.find.ui.topic;

import com.su.feature.find.api.FindApiServiceProvider;
import com.su.feature.find.bean.ResTopic;
import com.su.library.base.IRequestCallback;
import com.su.library.config.ErrorStatusConfig;
import com.su.network.ApiCall;
import com.su.network.bean.ResBase;

import java.util.List;

import retrofit2.Call;

public class TopicModel {
    public void requestData(IRequestCallback<List<ResTopic>> callback) {
        Call<ResBase<List<ResTopic>>> topic = FindApiServiceProvider.getApiService().getTopic();
        ApiCall.enqueueCall(topic, new ApiCall.SimpleCallback<ResBase<List<ResTopic>>>() {
            @Override
            public void onSuccess(ResBase<List<ResTopic>> data) {
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

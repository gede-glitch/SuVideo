package com.su.feature.find.ui.categorydetail;

import com.su.data.video.bean.ResFindCategory;
import com.su.feature.find.api.FindApiServiceProvider;
import com.su.feature.find.bean.ResCategoryDetail;
import com.su.library.base.IRequestCallback;
import com.su.library.config.ErrorStatusConfig;
import com.su.network.ApiCall;
import com.su.network.bean.ResBase;

import retrofit2.Call;

public class CategoryDetailModel {
    /**
     * 请求分类详情数据
     *
     * @param id
     */
    public void requestDatas(int id, IRequestCallback<ResFindCategory> callback) {
        Call<ResBase<ResCategoryDetail>> categoryDetail = FindApiServiceProvider.getApiService().getCategoryDetail(id);
        ApiCall.enqueueCall(categoryDetail, new ApiCall.SimpleCallback<ResBase<ResCategoryDetail>>() {
            @Override
            public void onSuccess(ResBase<ResCategoryDetail> data) {
                callback.onLoadFinish(data.getData().getInfo());
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

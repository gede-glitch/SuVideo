package com.su.feature.mediaplayer.ui.search;

import com.su.data.video.bean.ResVideoDetail;
import com.su.feature.mediaplayer.api.VideoPlayerApiServiceProvider;
import com.su.library.base.IRequestCallback;
import com.su.library.config.ErrorStatusConfig;
import com.su.network.ApiCall;
import com.su.network.bean.ResBase;

import java.util.List;

import retrofit2.Call;

public class SearchModel {
    public void searchByKeyword(String keyword, IRequestCallback<List<ResVideoDetail.ArchivesInfoDTO>> callback) {
        Call<ResBase<List<ResVideoDetail.ArchivesInfoDTO>>> search = VideoPlayerApiServiceProvider.getVideoPlayerApiService().search(keyword);
        ApiCall.enqueueCall(search, new ApiCall.SimpleCallback<ResBase<List<ResVideoDetail.ArchivesInfoDTO>>>() {
            @Override
            public void onSuccess(ResBase<List<ResVideoDetail.ArchivesInfoDTO>> data) {
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

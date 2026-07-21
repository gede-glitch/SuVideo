package com.su.feature.mediaplayer.ui.videolist;

import android.util.Log;


import com.su.data.video.bean.ResVideo;
import com.su.feature.mediaplayer.api.VideoPlayerApiService;
import com.su.feature.mediaplayer.api.VideoPlayerApiServiceProvider;
import com.su.library.config.ARouterPath;
import com.su.library.list.BaseListModel;
import com.su.library.list.BaseListViewModel;
import com.su.network.bean.ResBase;
import com.su.network.bean.ResList;

import retrofit2.Call;

public class VideoListViewModel extends BaseListViewModel<ResVideo> {
    private static final String TAG = "VideoListViewModel";
    private int mPageType;

    public void setPageType(int mPageType) {
        this.mPageType = mPageType;
    }

    @Override
    protected BaseListModel<ResVideo> createModel() {
        return new BaseListModel<ResVideo>(this, 10) {
            @Override
            protected Call<ResBase<ResList<ResVideo>>> creatCall(int page, int limit) {
                VideoPlayerApiService apiService = VideoPlayerApiServiceProvider.getVideoPlayerApiService();
                if (mPageType == ARouterPath.Video.VIDEO_LIST_FRAGMENT_RECOMMEND) {
                    return apiService.getRecommend(page, limit);
                } else if (mPageType == ARouterPath.Video.VIDEO_LIST_FRAGMENT_DAILY) {
                    return apiService.getDaily(page, limit);
                } else {
                    Log.e(TAG, "createCall: mPageType is error:" + mPageType);
                    return apiService.getRecommend(page, limit);
                }
            }
        };
    }
}

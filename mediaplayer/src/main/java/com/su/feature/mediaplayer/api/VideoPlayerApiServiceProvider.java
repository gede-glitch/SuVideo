package com.su.feature.mediaplayer.api;

import com.su.network.RetrofitProvider;

import retrofit2.Retrofit;

public class VideoPlayerApiServiceProvider {
    private static VideoPlayerApiService mVideoPlayerApiService;

    public static VideoPlayerApiService getVideoPlayerApiService() {
        if (mVideoPlayerApiService == null) {
            Retrofit retrofit = RetrofitProvider.provider();
            mVideoPlayerApiService = retrofit.create(VideoPlayerApiService.class);
        }
        return mVideoPlayerApiService;
    }
}

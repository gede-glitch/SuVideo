package com.su.feature.home.api;

import com.su.network.RetrofitProvider;

import retrofit2.Retrofit;

public class HomeApiServiceProvider {
    private static HomeApiService mApiService;

    public static HomeApiService getApiService() {
        if (mApiService == null) {
            Retrofit retrofit = RetrofitProvider.provider();
            mApiService = retrofit.create(HomeApiService.class);
        }
        return mApiService;
    }
}

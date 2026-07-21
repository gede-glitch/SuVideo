package com.su.feature.plaza.api;

import com.su.network.RetrofitProvider;

import retrofit2.Retrofit;

public class PlazaApiServiceProvider {
    private static PlazaApiService mApiService;

    public static PlazaApiService getApiService() {
        if (mApiService == null) {
            Retrofit retrofit = RetrofitProvider.provider();
            mApiService = retrofit.create(PlazaApiService.class);
        }
        return mApiService;
    }
}

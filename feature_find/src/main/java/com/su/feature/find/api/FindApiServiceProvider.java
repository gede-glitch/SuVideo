package com.su.feature.find.api;

import com.su.network.RetrofitProvider;

import retrofit2.Retrofit;

public class FindApiServiceProvider {
    private static FindApiService mApiService;

    public static FindApiService getApiService() {
        if (mApiService == null) {
            Retrofit retrofit = RetrofitProvider.provider();
            mApiService = retrofit.create(FindApiService.class);
        }
        return mApiService;
    }
}

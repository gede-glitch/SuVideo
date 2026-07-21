package com.su.feature.user.api;

import com.su.network.RetrofitProvider;

import retrofit2.Retrofit;

public class UserApiServiceProvider {
    private static UserApiService mUserApiService;

    public static UserApiService getUserApiService() {
        if (mUserApiService == null) {
            Retrofit provider = RetrofitProvider.provider();
            mUserApiService = provider.create(UserApiService.class);
        }
        return mUserApiService;
    }
}

package com.su.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {
    private static final String BASE_URL = "https://titok.fzqq.fun/";
    private static Retrofit mRetrofit;
    public static Retrofit provider() {
        if(mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .client(OkhttpClientProvider.provider())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}

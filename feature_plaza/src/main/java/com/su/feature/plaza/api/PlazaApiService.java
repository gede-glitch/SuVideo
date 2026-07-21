package com.su.feature.plaza.api;

import com.su.feature.plaza.bean.ResPlaza;
import com.su.network.bean.ResBase;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlazaApiService {
    @GET("addons/cms/api.eye/square")
    Call<ResBase<List<ResPlaza>>> getPlaza();
}

package com.su.feature.find.api;

import com.su.feature.find.bean.ResCategoryDetail;
import com.su.feature.find.bean.ResFind;
import com.su.feature.find.bean.ResThemeData;
import com.su.feature.find.bean.ResTopic;
import com.su.network.bean.ResBase;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FindApiService {
    @GET("addons/cms/api.eye/find")
    Call<ResBase<ResFind>> getFindData();

    /**
     * 获取分类详情数据
     *
     * @return 服务端返回的数据类型
     */
    @GET("addons/cms/api.eye/category_detail")
    Call<ResBase<ResCategoryDetail>> getCategoryDetail(@Query("channel_id") int id);


    /**
     * 获取主题播单
     *
     * 不需要传任何参数
     *
     * @return 服务端返回的数据类型
     */
    @GET("addons/cms/api.eye/anchor")
    Call<ResBase<List<ResThemeData>>> getAnchor();
    /**
     * 获取主题播单
     *
     * 不需要传任何参数
     *
     * @return 服务端返回的数据类型
     */
    @GET("addons/cms/api.eye/topic")
    Call<ResBase<List<ResTopic>>> getTopic();
}

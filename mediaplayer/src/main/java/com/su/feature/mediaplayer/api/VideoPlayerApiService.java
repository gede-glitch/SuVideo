package com.su.feature.mediaplayer.api;

import com.su.data.video.bean.ReqComment;
import com.su.data.video.bean.ReqDeleteComment;
import com.su.data.video.bean.ReqVideoOperation;
import com.su.data.video.bean.ResCategoryVideoDetail;
import com.su.data.video.bean.ResCollection;
import com.su.data.video.bean.ResComment;
import com.su.data.video.bean.ResLike;
import com.su.data.video.bean.ResSendComment;
import com.su.data.video.bean.ResVideo;
import com.su.data.video.bean.ResVideoDetail;
import com.su.network.bean.ResBase;
import com.su.network.bean.ResList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VideoPlayerApiService {
    /**
     * 获取分类详情列表
     *
     * @return 服务端返回的数据类型
     */
    @GET("addons/cms/api.eye/category_list")
    Call<ResBase<ResList<ResCategoryVideoDetail>>> getCategoryLists(
            @Header("token") String token,
            @Query("type") int type,
            @Query("channel_id") int id,
            @Query("page") int page,
            @Query("limit") int limit
    );

    /**
     * 删除评论
     *
     * @param token
     * @param comment comment内部的id 指的是评论id
     * @return7
     */
    @POST("addons/cms/api.comment/delete")
    Call<ResBase<ReqComment>> delComment(@Header("token") String token, @Body ReqDeleteComment comment);

    /**
     * 评论列表
     *
     * @param token
     * @param id    视频id
     * @param page  分页参数
     * @return7
     */
    @POST("addons/cms/api.comment/index")
    Call<ResBase<ResList<ResComment>>> requestComments(@Header("token") String token, @Query("aid") int id, @Query("page") int page);

    /**
     * 发送评论
     *
     * @param token
     * @param operation 需要传递aid、content
     * @return7
     */
    @POST("addons/cms/api.comment/post")
    Call<ResBase<ResSendComment>> sendComment(@Header("token") String token, @Body ReqComment operation);

    /**
     * 收藏
     *
     * @param token
     * @param like  收藏传递id和type， type固定为archives
     * @return7
     */
    @POST("addons/cms/api.collection/create")
    Call<ResLike> requestCollection(@Header("token") String token, @Body ReqVideoOperation like);

    /**
     * 取消收藏
     *
     * @param token
     * @param like  收藏传递id和type
     * @return7
     */
    @POST("addons/cms/api.collection/delete")
    Call<ResLike> cancelCollection(@Header("token") String token, @Body ReqVideoOperation like);

    /**
     * 点赞
     *
     * @param token
     * @param like  点赞传递id和type，type默认传like
     * @return7
     */
    @POST("addons/cms/api.archives/vote")
    Call<ResLike> requestLike(@Header("token") String token, @Body ReqVideoOperation like);

    /**
     * 取消点赞
     *
     * @param token
     * @param like  需要取消点赞的视频id
     * @return7
     */
    @POST("addons/cms/api.archives/vote_del")
    Call<ResLike> cancelLike(@Header("token") String token, @Body ReqVideoOperation like);

    /**
     * 获取视频详情
     *
     * @param id    视频id
     * @param token 用户token
     */
    @POST("addons/cms/api.archives/detail")
    Call<ResBase<ResVideoDetail>> getVideoDetail(@Header("token") String token, @Query("id") int id);

    /**
     * 分页加载日报列表数据
     *
     * @param page  当前页数
     * @param limit 每页请求数量
     * @return 服务端返回的数据类型
     */
    @GET("addons/cms/api.eye/daily")
    Call<ResBase<ResList<ResVideo>>> getDaily(@Query("page") int page, @Query("limit") int limit);

    /**
     * 分页加载推荐列表数据
     *
     * @param page  当前页数
     * @param limit 每页请求数量
     * @return 服务端返回的数据类型
     */
    @GET("addons/cms/api.eye/recommend")
    Call<ResBase<ResList<ResVideo>>> getRecommend(@Query("page") int page, @Query("limit") int limit);

    /**
     * 搜索
     *
     * @param keyword 搜索关键词
     * @return
     */
    @POST("addons/cms/api.eye/search")
    Call<ResBase<List<ResVideoDetail.ArchivesInfoDTO>>> search(@Query("keyword") String keyword);

    /**
     * 收藏列表
     *
     * @param token 已登录的用户token
     * @param type  默认传 “archives”
     * @return
     */
    @POST("addons/cms/api.collection/index")
    Call<ResBase<ResCollection>> getCollectionList(@Header("token") String token, @Query("type") String type);
}

package com.su.feature.user.api;

import com.su.feature.user.bean.ReqMobileLogin;
import com.su.feature.user.bean.ReqResetPwd;
import com.su.feature.user.bean.ReqSendSmsCode;
import com.su.feature.user.bean.ReqUpdateUserInfo;
import com.su.feature.user.bean.ResEmpty;
import com.su.feature.user.bean.ResMobileLogin;
import com.su.feature.user.bean.ResUpload;
import com.su.library.bean.ResUser;
import com.su.network.bean.ResBase;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserApiService {
    @POST("addons/cms/api.sms/send")
    Call<ResBase<ResEmpty>> sendSmsCode(@Body ReqSendSmsCode code);

    @POST("addons/cms/api.login/mobilelogin")
    Call<ResBase<ResMobileLogin>> mobileLogin(@Body ReqMobileLogin code);

    @GET("addons/cms/api.user/userInfo")
    Call<ResBase<ResUser>> getUserInfo(@Query("user_id") String userId, @Query("type") String type);

    /**
     * 重置密码
     *
     * @param code 请求体
     * @return
     */
    @POST("addons/cms/api.login/resetpwd")
    Call<ResBase<ResEmpty>> resetPassword(@Header("token") String token, @Body ReqResetPwd code);

    /**
     * 退出登录
     *
     * @return
     */
    @POST("addons/cms/api.user/logout")
    Call<ResBase<ResEmpty>> logout(@Header("token") String token);

    /**
     * 修改用户信息
     *
     * @return
     */
    @POST("addons/cms/api.user/profile")
    Call<ResBase<Void>> updateUserProfile(@Header("token") String token, @Body ReqUpdateUserInfo profile);

    @Multipart //标识这个请求是一个multipart/form-data 表单提交请求
    @POST("api/common/upload")
    Call<ResBase<ResUpload>> uploadFile(@Header("token") String token, @Part MultipartBody.Part file);
}

package com.su.feature.mediaplayer.ui.categorylist;

import com.su.data.video.bean.ReqComment;
import com.su.data.video.bean.ReqVideoOperation;
import com.su.data.video.bean.ResCategoryVideoDetail;
import com.su.data.video.bean.ResLike;
import com.su.data.video.bean.ResSendComment;
import com.su.feature.mediaplayer.api.VideoPlayerApiService;
import com.su.feature.mediaplayer.api.VideoPlayerApiServiceProvider;
import com.su.library.base.IRequestCallback;
import com.su.library.config.ErrorStatusConfig;
import com.su.library.list.BaseListModel;
import com.su.library.list.IListListener;
import com.su.library.manager.UserManager;
import com.su.network.ApiCall;
import com.su.network.bean.ResBase;
import com.su.network.bean.ResList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListModel extends BaseListModel<ResCategoryVideoDetail> {
    public int mType;
    public int mChannelId;

    public CategoryListModel(IListListener<ResCategoryVideoDetail> mListener, int mLimit) {
        super(mListener, mLimit);
    }

    public void setArgments(int type, int id) {
        mType = type;
        mChannelId = id;
    }

    public boolean isLogin() {
        return UserManager.getInstance().isLogin();
    }

    @Override
    protected Call<ResBase<ResList<ResCategoryVideoDetail>>> creatCall(int page, int limit) {
        String token = UserManager.getInstance().getToken();
        return VideoPlayerApiServiceProvider.getVideoPlayerApiService()
                .getCategoryLists(token, mType, mChannelId, page, limit);
    }

    public void sendComment(int id, String content, IRequestCallback<ResSendComment> callback) {
        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }
        String token = UserManager.getInstance().getToken();
        Call<ResBase<ResSendComment>> call = VideoPlayerApiServiceProvider.getVideoPlayerApiService()
                .sendComment(token, new ReqComment(id, content));
        ApiCall.enqueueCall(call, new ApiCall.SimpleCallback<ResBase<ResSendComment>>() {
            @Override
            public void onSuccess(ResBase<ResSendComment> data) {
                callback.onLoadFinish(data.getData());
            }
            @Override
            public void onEmpty() {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_EMPTY, "暂无数据");
            }
            @Override
            public void onServerError(String message) {
                callback.onLoadFail(ErrorStatusConfig.ERROR_SERVER_REQUEST, message);
            }
            @Override
            public void onNetworkError() {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL, "网络错误");
            }
        });
    }

    public void requestLike(int id, IRequestCallback<ResLike> callback) {
        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }
        Call<ResLike> call = VideoPlayerApiServiceProvider.getVideoPlayerApiService()
                .requestLike(UserManager.getInstance().getToken(), new ReqVideoOperation(id, "like"));
        call.enqueue(new retrofit2.Callback<ResLike>() {
            @Override
            public void onResponse(retrofit2.Call<ResLike> call, retrofit2.Response<ResLike> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onLoadFinish(response.body());
                } else {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_SERVER_REQUEST, "请求失败");
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResLike> call, Throwable t) {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL, "网络错误");
            }
        });
    }

    public void requestCancelLike(int id, IRequestCallback<ResLike> callback) {
        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }
        Call<ResLike> call = VideoPlayerApiServiceProvider.getVideoPlayerApiService()
                .cancelLike(UserManager.getInstance().getToken(), new ReqVideoOperation(id));
        call.enqueue(new retrofit2.Callback<ResLike>() {
            @Override
            public void onResponse(retrofit2.Call<ResLike> call, retrofit2.Response<ResLike> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onLoadFinish(response.body());
                } else {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_SERVER_REQUEST, "请求失败");
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResLike> call, Throwable t) {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL, "网络错误");
            }
        });
    }

    public void requestCollection(int id, IRequestCallback<ResLike> callback) {
        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }
        Call<ResLike> call = VideoPlayerApiServiceProvider.getVideoPlayerApiService()
                .requestCollection(UserManager.getInstance().getToken(), new ReqVideoOperation("archives", id));
        call.enqueue(new retrofit2.Callback<ResLike>() {
            @Override
            public void onResponse(retrofit2.Call<ResLike> call, retrofit2.Response<ResLike> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onLoadFinish(response.body());
                } else {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_SERVER_REQUEST, "请求失败");
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResLike> call, Throwable t) {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL, "网络错误");
            }
        });
    }

    public void requestCancelCollection(int id, IRequestCallback<ResLike> callback) {
        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }
        Call<ResLike> call = VideoPlayerApiServiceProvider.getVideoPlayerApiService()
                .cancelCollection(UserManager.getInstance().getToken(), new ReqVideoOperation(id));
        call.enqueue(new retrofit2.Callback<ResLike>() {
            @Override
            public void onResponse(retrofit2.Call<ResLike> call, retrofit2.Response<ResLike> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onLoadFinish(response.body());
                } else {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_SERVER_REQUEST, "请求失败");
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResLike> call, Throwable t) {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL, "网络错误");
            }
        });
    }
}

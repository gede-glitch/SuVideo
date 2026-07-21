package com.su.feature.mediaplayer.ui.videodetail;

import com.su.data.video.bean.ReqComment;
import com.su.data.video.bean.ReqDeleteComment;
import com.su.data.video.bean.ReqVideoOperation;
import com.su.data.video.bean.ResComment;
import com.su.data.video.bean.ResLike;
import com.su.data.video.bean.ResSendComment;
import com.su.data.video.bean.ResVideoDetail;
import com.su.feature.mediaplayer.api.VideoPlayerApiService;
import com.su.feature.mediaplayer.api.VideoPlayerApiServiceProvider;
import com.su.feature.mediaplayer.db.VideoHistory;
import com.su.feature.mediaplayer.db.VideoHistoryRepository;
import com.su.library.base.BaseApplication;
import com.su.library.base.IRequestCallback;
import com.su.library.config.ErrorStatusConfig;
import com.su.library.list.IListListener;
import com.su.library.manager.UserManager;
import com.su.network.ApiCall;
import com.su.network.bean.ResBase;
import com.su.network.bean.ResList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoDetailModel {
    private int mCommentPage = 1;

    /**
     * 检查用户是否已登录
     */
    public boolean isLogin() {
        return UserManager.getInstance().isLogin();
    }

    /**
     * 获取视频详情
     */
    public void requestDetail(int id, IRequestCallback<ResVideoDetail> callback) {
        VideoPlayerApiService videoPlayerApiService = VideoPlayerApiServiceProvider.getVideoPlayerApiService();
        String token = UserManager.getInstance().getToken();
        Call<ResBase<ResVideoDetail>> videoDetailCall = videoPlayerApiService.getVideoDetail(token, id);
        ApiCall.enqueueCall(videoDetailCall, new ApiCall.SimpleCallback<ResBase<ResVideoDetail>>() {
            @Override
            public void onSuccess(ResBase<ResVideoDetail> data) {
                callback.onLoadFinish(data.getData());
            }

            @Override
            public void onEmpty() {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_EMPTY, "server is empty");
            }

            @Override
            public void onServerError(String message) {
                callback.onLoadFail(ErrorStatusConfig.ERROR_SERVER_REQUEST, message);
            }

            @Override
            public void onNetworkError() {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL, "network is error");
            }
        });
    }

    /**
     * 点赞
     */
    public void requestLike(int id, IRequestCallback<ResLike> callback) {
        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }
        VideoPlayerApiService videoPlayerApiService = VideoPlayerApiServiceProvider.getVideoPlayerApiService();
        String token = UserManager.getInstance().getToken();
        Call<ResLike> likeCall = videoPlayerApiService.requestLike(token, new ReqVideoOperation(id, "like"));
        likeCall.enqueue(new Callback<ResLike>() {
            @Override
            public void onResponse(Call<ResLike> call, Response<ResLike> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onLoadFinish(response.body());
                } else {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_SERVER_REQUEST, "请求失败");
                }
            }

            @Override
            public void onFailure(Call<ResLike> call, Throwable throwable) {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL, "网络错误");
            }
        });
    }

    /**
     * 取消点赞
     */
    public void requestCancelLike(int id, IRequestCallback<ResLike> callback) {
        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }
        VideoPlayerApiService videoPlayerApiService = VideoPlayerApiServiceProvider.getVideoPlayerApiService();
        String token = UserManager.getInstance().getToken();
        Call<ResLike> unlikeCall = videoPlayerApiService.cancelLike(token, new ReqVideoOperation(id));
        unlikeCall.enqueue(new Callback<ResLike>() {
            @Override
            public void onResponse(Call<ResLike> call, Response<ResLike> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onLoadFinish(response.body());
                } else {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_SERVER_REQUEST, "请求失败");
                }
            }

            @Override
            public void onFailure(Call<ResLike> call, Throwable throwable) {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL, "网络错误");
            }
        });
    }

    /**
     * 收藏
     */
    public void requestCollection(int id, IRequestCallback<ResLike> callback) {
        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }
        VideoPlayerApiService videoPlayerApiService = VideoPlayerApiServiceProvider.getVideoPlayerApiService();
        String token = UserManager.getInstance().getToken();
        Call<ResLike> collectionCall = videoPlayerApiService.requestCollection(token, new ReqVideoOperation("archives", id));
        collectionCall.enqueue(new Callback<ResLike>() {
            @Override
            public void onResponse(Call<ResLike> call, Response<ResLike> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onLoadFinish(response.body());
                } else {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_SERVER_REQUEST, "请求失败");
                }
            }

            @Override
            public void onFailure(Call<ResLike> call, Throwable throwable) {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL, "网络错误");
            }
        });
    }

    /**
     * 取消收藏
     */
    public void requestCancelCollection(int id, IRequestCallback<ResLike> callback) {
        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }
        VideoPlayerApiService videoPlayerApiService = VideoPlayerApiServiceProvider.getVideoPlayerApiService();
        String token = UserManager.getInstance().getToken();
        Call<ResLike> unCollectionCall = videoPlayerApiService.cancelCollection(token, new ReqVideoOperation(id));
        unCollectionCall.enqueue(new Callback<ResLike>() {
            @Override
            public void onResponse(Call<ResLike> call, Response<ResLike> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onLoadFinish(response.body());
                } else {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_SERVER_REQUEST, "请求失败");
                }
            }

            @Override
            public void onFailure(Call<ResLike> call, Throwable throwable) {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL, "网络错误");
            }
        });
    }

    public void sendComment(int id, String content, IRequestCallback<ResSendComment> callback) {
        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "用户未登录");
            return;
        }
        String token = UserManager.getInstance().getToken();
        ReqComment operation = new ReqComment(id, content);
        Call<ResBase<ResSendComment>> resBaseCall = VideoPlayerApiServiceProvider.getVideoPlayerApiService().sendComment(token, operation);
        ApiCall.enqueueCall(resBaseCall, new ApiCall.SimpleCallback<ResBase<ResSendComment>>() {
            @Override
            public void onSuccess(ResBase<ResSendComment> data) {
                callback.onLoadFinish(data.getData());
            }

            @Override
            public void onEmpty() {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_EMPTY, "server is empty");
            }

            @Override
            public void onServerError(String message) {
                callback.onLoadFail(ErrorStatusConfig.ERROR_SERVER_REQUEST, "server is error");
            }

            @Override
            public void onNetworkError() {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL, "network is error");
            }
        });
    }

    public void requestComments(int id, boolean isFirst, IListListener<ResComment> callback) {
        if (isFirst) {
            mCommentPage = 1;
        } else {
            mCommentPage++;
        }
        VideoPlayerApiService videoPlayerApiService = VideoPlayerApiServiceProvider.getVideoPlayerApiService();
        String token = UserManager.getInstance().getToken();
        Call<ResBase<ResList<ResComment>>> resBaseCall = videoPlayerApiService.requestComments(token, id, mCommentPage);
        ApiCall.enqueueListCall(resBaseCall, isFirst, new ApiCall.ListCallback<ResComment>() {
            @Override
            public void onSuccess(boolean isFirst, ResList<ResComment> data) {
                callback.onLoadFinish(isFirst, data);
            }

            @Override
            public void onEmpty() {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_EMPTY, "server is empty");
            }

            @Override
            public void onServerError(String message) {
                callback.onLoadFail(ErrorStatusConfig.ERROR_SERVER_REQUEST, "server is error");
            }

            @Override
            public void onNetworkError() {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL, "network is error");
            }
        });
    }

    public boolean isAuthor(int userId) {
        boolean isAuthor = false;
        if (!isLogin()) {
            return isAuthor;
        }
        String id = UserManager.getInstance().getUserInfo().getUser().getId();
        isAuthor = userId == Integer.parseInt(id);
        return isAuthor;
    }

    public void deleteComment(int id, IRequestCallback<ResBase> callback) {
        if (!isLogin()) {
            callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN, "user is not login");
            return;
        }

        String token = UserManager.getInstance().getToken();
        Call<ResBase<ReqComment>> resBaseCall = VideoPlayerApiServiceProvider.getVideoPlayerApiService().delComment(token, new ReqDeleteComment(id));

        ApiCall.enqueueCall(resBaseCall, new ApiCall.SimpleCallback<ResBase<ReqComment>>() {
            @Override
            public void onSuccess(ResBase<ReqComment> data) {
                callback.onLoadFinish(data);
            }

            @Override
            public void onEmpty() {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_EMPTY, "server is empty");
            }

            @Override
            public void onServerError(String message) {
                callback.onLoadFail(ErrorStatusConfig.ERROR_SERVER_REQUEST, message);
            }

            @Override
            public void onNetworkError() {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL, "network is error");
            }
        });
    }

    public void insertHistory(ResVideoDetail.ArchivesInfoDTO archivesInfo) {
        VideoHistoryRepository repository = new VideoHistoryRepository(BaseApplication.getContext());
        //如果未登录 userid = 0
        String userId = "0";
        if (UserManager.getInstance().isLogin()) {
            userId = UserManager.getInstance().getUserInfo().getUser().getId();
        }
        //生成浏览记录
        String name = archivesInfo.getChannel().getName();
        VideoHistory history = repository.generateVideoHistory(userId, archivesInfo.getId(),
                archivesInfo.getTitle(), name, archivesInfo.getDuration(), archivesInfo.getImage());

        //插入数据
        repository.insert(history);
    }
}

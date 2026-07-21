package com.su.feature.mediaplayer.ui.videodetail;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.su.data.video.bean.ResComment;
import com.su.data.video.bean.ResLike;
import com.su.data.video.bean.ResSendComment;
import com.su.data.video.bean.ResVideoDetail;
import com.su.library.base.BaseViewModel;
import com.su.library.base.IRequestCallback;
import com.su.library.config.ErrorStatusConfig;
import com.su.library.list.IListListener;
import com.su.network.bean.ResBase;
import com.su.network.bean.ResList;

import java.util.List;

public class VideoDetailViewModel extends BaseViewModel {
    private static final String TAG = "VideoDetailViewModel";
    private VideoDetailModel mModel;

    private MutableLiveData<ResVideoDetail.ArchivesInfoDTO> mVideoInfo = new MutableLiveData<>();
    private MutableLiveData<List<ResComment>> mCommentList = new MutableLiveData<>();
    private MutableLiveData<String> mChannel = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsLikes = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> mIsCollection = new MutableLiveData<>(false);
    private MutableLiveData<String> mCommentCount = new MutableLiveData<>("0");
    private MutableLiveData<Boolean> mIsLoadMore = new MutableLiveData<>(true);
    private MutableLiveData<String> mVideoUrl = new MutableLiveData<>();

    public VideoDetailViewModel() {
        this.mModel = new VideoDetailModel();
    }


    public MutableLiveData<Boolean> getIsLikes() {
        return mIsLikes;
    }

    public MutableLiveData<Boolean> getIsCollection() {
        return mIsCollection;
    }

    public void requestDetail(int videoId) {
        showLoading(true);
        mModel.requestDetail(videoId, new IRequestCallback<ResVideoDetail>() {
            @Override
            public void onLoadFinish(ResVideoDetail datas) {
                showLoading(false);

                ResVideoDetail.ArchivesInfoDTO archivesInfo = datas.getArchivesInfo();
                List<ResComment> commentList = datas.getCommentList();

                Log.d(TAG, "archivesInfo.comments = " + archivesInfo.getComments());
                Log.d(TAG, "commentList.size() = " + (commentList != null ? commentList.size() : 0));

                mVideoInfo.setValue(archivesInfo);
                mVideoUrl.setValue(archivesInfo.getVideoFile());
                mCommentList.setValue(commentList);
                mCommentCount.setValue(String.valueOf(commentList != null ? commentList.size() : 0));
                mIsLikes.setValue(archivesInfo.getIslike() == 1);
                mIsCollection.setValue(archivesInfo.getIscollection() == 1);

                ResVideoDetail.ArchivesInfoDTO.ChannelDTO channel = archivesInfo.getChannel();
                if (channel != null && channel.getName() != null && !channel.getName().isEmpty()) {
                    mChannel.setValue("#" + channel.getName());
                } else {
                    mChannel.setValue("");
                }

                mModel.insertHistory(datas.getArchivesInfo());
            }

            @Override
            public void onLoadFail(int statusCode, String message) {
                showLoading(false);
                showToast(message);
            }
        });
    }

    public MutableLiveData<ResVideoDetail.ArchivesInfoDTO> getVideoInfo() {
        return mVideoInfo;
    }

    public MutableLiveData<List<ResComment>> getCommentList() {
        return mCommentList;
    }

    public MutableLiveData<String> getChannel() {
        return mChannel;
    }

    /**
     * 点赞/取消点赞 — 乐观更新
     */
    public void onLikeClick() {
        if (!mModel.isLogin()) {
            showToast("未登录");
            mErrorCode.setValue(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN);
            return;
        }

        Boolean isLike = mIsLikes.getValue();
        ResVideoDetail.ArchivesInfoDTO value = mVideoInfo.getValue();

        if (value == null) {
            return;
        }

        int id = value.getId();

        // --- 保存旧状态用于回滚 ---
        boolean oldLikeState = Boolean.TRUE.equals(isLike);
        int oldLikes = value.getLikes() != null ? value.getLikes() : 0;

        // --- 乐观更新：立即改变本地状态 ---
        boolean newLikeState = !oldLikeState;
        int newLikes = oldLikes + (newLikeState ? 1 : -1);
        if (newLikes < 0) newLikes = 0;

        value.setIslike(newLikeState ? 1 : 0);
        value.setLikes(newLikes);
        mVideoInfo.setValue(value);
        mIsLikes.setValue(newLikeState);

        // --- 发起网络请求，失败时回滚 ---
        if (newLikeState) {
            mModel.requestLike(id, new IRequestCallback<ResLike>() {
                @Override
                public void onLoadFinish(ResLike datas) {
                    // 成功：乐观值已经是对的，不需要覆盖
                    // 用服务器返回的准确值覆盖（可选，如果服务器可靠）
                    if (datas != null && datas.getLikes() > 0) {
                        value.setLikes(datas.getLikes());
                        mVideoInfo.setValue(value);
                    }
                    if (datas != null && datas.getMsg() != null) {
//                        showToast(datas.getMsg());
                    }
                }

                @Override
                public void onLoadFail(int statusCode, String message) {
                    showToast(message);
                    // 回滚
                    value.setIslike(oldLikeState ? 1 : 0);
                    value.setLikes(oldLikes);
                    mVideoInfo.setValue(value);
                    mIsLikes.setValue(oldLikeState);
                }
            });
        } else {
            mModel.requestCancelLike(id, new IRequestCallback<ResLike>() {
                @Override
                public void onLoadFinish(ResLike datas) {
                    if (datas != null && datas.getLikes() > 0) {
                        value.setLikes(datas.getLikes());
                        mVideoInfo.setValue(value);
                    }
                    if (datas != null && datas.getMsg() != null) {
//                        showToast(datas.getMsg());
                    }
                }

                @Override
                public void onLoadFail(int statusCode, String message) {
                    showToast(message);
                    // 回滚
                    value.setIslike(oldLikeState ? 1 : 0);
                    value.setLikes(oldLikes);
                    mVideoInfo.setValue(value);
                    mIsLikes.setValue(oldLikeState);
                }
            });
        }
    }

    /**
     * 收藏/取消收藏 — 乐观更新
     */
    public void onCollectionClick() {
        if (!mModel.isLogin()) {
            showToast("未登录");
            mErrorCode.setValue(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN);
            return;
        }

        Boolean isCollection = mIsCollection.getValue();
        ResVideoDetail.ArchivesInfoDTO info = mVideoInfo.getValue();
        if (info == null) return;

        int id = info.getId();

        // --- 保存旧状态用于回滚 ---
        boolean oldCollectionState = Boolean.TRUE.equals(isCollection);
        int oldCollection = info.getCollection() != null ? info.getCollection() : 0;

        // --- 乐观更新：立即改变本地状态 ---
        boolean newCollectionState = !oldCollectionState;
        int newCollection = oldCollection + (newCollectionState ? 1 : -1);
        if (newCollection < 0) newCollection = 0;

        info.setIscollection(newCollectionState ? 1 : 0);
        info.setCollection(newCollection);
        mVideoInfo.setValue(info);
        mIsCollection.setValue(newCollectionState);

        // --- 发起网络请求，失败时回滚 ---
        if (newCollectionState) {
            mModel.requestCollection(id, new IRequestCallback<ResLike>() {
                @Override
                public void onLoadFinish(ResLike datas) {
                    if (datas != null && datas.getMsg() != null) {
//                        showToast(datas.getMsg());
                    }
                }

                @Override
                public void onLoadFail(int statusCode, String message) {
                    showToast(message);
                    // 回滚
                    info.setIscollection(oldCollectionState ? 1 : 0);
                    info.setCollection(oldCollection);
                    mVideoInfo.setValue(info);
                    mIsCollection.setValue(oldCollectionState);
                }
            });
        } else {
            mModel.requestCancelCollection(id, new IRequestCallback<ResLike>() {
                @Override
                public void onLoadFinish(ResLike datas) {
                    if (datas != null && datas.getMsg() != null) {
//                        showToast(datas.getMsg());
                    }
                }

                @Override
                public void onLoadFail(int statusCode, String message) {
                    showToast(message);
                    // 回滚
                    info.setIscollection(oldCollectionState ? 1 : 0);
                    info.setCollection(oldCollection);
                    mVideoInfo.setValue(info);
                    mIsCollection.setValue(oldCollectionState);
                }
            });
        }
    }

    /**
     * 发送评论
     */
    public void sendComment(String message) {
        if (!mModel.isLogin()) {
            showToast("未登录");
            mErrorCode.setValue(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN);
            return;
        }

        ResVideoDetail.ArchivesInfoDTO info = mVideoInfo.getValue();
        if (info == null) {
            return;
        }
        int id = info.getId();
        showLoading(true);
        mModel.sendComment(id, message, new IRequestCallback<ResSendComment>() {
            @Override
            public void onLoadFinish(ResSendComment datas) {
                showLoading(false);
                List<ResComment> comments = mCommentList.getValue();
                if (comments != null) {
                    comments.add(0, datas.getComment());
                    mCommentList.setValue(comments);
//                    info.setComments(info.getComments() + 1);
                    mVideoInfo.setValue(info);
                    mCommentCount.setValue(String.valueOf(comments.size()));
                }
            }

            @Override
            public void onLoadFail(int statusCode, String message) {
                showLoading(false);
                showToast(message);
            }
        });
    }

    public void requestComments(boolean isFirst) {
        Integer id = mVideoInfo.getValue().getId();
        mModel.requestComments(id, isFirst, new IListListener<ResComment>() {
            @Override
            public void onLoadFinish(boolean isFirst, ResList<ResComment> dataList) {
                List<ResComment> list = dataList.getList();
                if (list != null && list.size() >= 10) {
                    mIsLoadMore.setValue(true);
                } else {
                    mIsLoadMore.setValue(false);
                }
                if (isFirst) {
                    mCommentList.setValue(list);
                } else {
                    List<ResComment> value = mCommentList.getValue();
                    value.addAll(list);
                    mCommentList.setValue(value);
                }
            }

            @Override
            public void onLoadFail(int statusCode, String message) {
                Log.i(TAG, "onLoadFail: statusCode : " + statusCode);
                if (statusCode == ErrorStatusConfig.ERROR_STATUS_EMPTY) {
                    mIsLoadMore.setValue(false);
                    showToast("没有更多数据了");
                }
            }
        });
    }

    public void deleteComment(ResComment comment) {
        if (!mModel.isLogin()) {
            showToast("未登录");
            mErrorCode.setValue(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN);
            return;
        }

        Integer userId = comment.getUserId();
        if (!mModel.isAuthor(userId)) {
            showToast("不能删除别人的评论");
            return;
        }
        showLoading(true);
        int id = comment.getId();
        mModel.deleteComment(id, new IRequestCallback<ResBase>() {
            @Override
            public void onLoadFinish(ResBase datas) {
                showLoading(false);
                List<ResComment> value = mCommentList.getValue();
                value.remove(comment);
                mCommentList.setValue(value);
                mCommentCount.setValue(String.valueOf(value.size()));
            }

            @Override
            public void onLoadFail(int statusCode, String message) {
                showLoading(false);
                showToast(message);
            }
        });
    }

    public MutableLiveData<String> getCommentCount() {
        return mCommentCount;
    }

    public MutableLiveData<Boolean> getIsLoadMore() {
        return mIsLoadMore;
    }

    public MutableLiveData<String> getVideoUrl() {
        return mVideoUrl;
    }
}

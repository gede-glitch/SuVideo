package com.su.feature.mediaplayer.ui.categorylist;

import androidx.lifecycle.MutableLiveData;

import com.su.data.video.bean.ResCategoryVideoDetail;
import com.su.data.video.bean.ResLike;
import com.su.data.video.bean.ResSendComment;
import com.su.library.base.BaseViewModel;
import com.su.library.base.IRequestCallback;
import com.su.library.config.ErrorStatusConfig;
import com.su.library.list.BaseListModel;
import com.su.library.list.BaseListViewModel;
import com.su.network.bean.ResList;

public class CategoryListViewModel extends BaseListViewModel<ResCategoryVideoDetail> {
    private CategoryListModel mCategoryModel;
    @Override
    protected BaseListModel<ResCategoryVideoDetail> createModel() {
        mCategoryModel = new CategoryListModel(this, 10);
        setPageLimit(10);
        return mCategoryModel;
    }

    public void setArgments(int type, int id) {
        mCategoryModel.setArgments(type, id);
    }

    public void onLikeClick(int position) {
        if (!mCategoryModel.isLogin()) {
            showToast("未登录");
            mErrorCode.setValue(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN);
            return;
        }
        ResList<ResCategoryVideoDetail> value = mDataList.getValue();
        if (value == null || position >= value.getList().size()) return;

        ResCategoryVideoDetail video = value.getList().get(position);
        boolean isLike = video.getIslike();
        showLoading(true);

        if (isLike) {
            mCategoryModel.requestCancelLike(video.getId(), new IRequestCallback<ResLike>() {
                @Override
                public void onLoadFinish(ResLike data) {
                    showLoading(false);
                    showToast(data.getMsg());
                    video.setIslike(0);
                    video.setLikes(video.getLikes() - 1);
                    mDataList.setValue(value);
                }
                @Override
                public void onLoadFail(int statusCode, String message) {
                    showLoading(false);
                    showToast(message);
                }
            });
        } else {
            mCategoryModel.requestLike(video.getId(), new IRequestCallback<ResLike>() {
                @Override
                public void onLoadFinish(ResLike data) {
                    showLoading(false);
                    showToast(data.getMsg());
                    video.setIslike(1);
                    video.setLikes(video.getLikes() + 1);
                    mDataList.setValue(value);
                }
                @Override
                public void onLoadFail(int statusCode, String message) {
                    showLoading(false);
                    showToast(message);
                }
            });
        }
    }

    public void onCollectionClick(int position) {
        if (!mCategoryModel.isLogin()) {
            showToast("未登录");
            mErrorCode.setValue(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN);
            return;
        }
        ResList<ResCategoryVideoDetail> value = mDataList.getValue();
        if (value == null || position >= value.getList().size()) return;

        ResCategoryVideoDetail video = value.getList().get(position);
        boolean isCollected = video.getIscollection();
        showLoading(true);

        if (isCollected) {
            mCategoryModel.requestCancelCollection(video.getId(), new IRequestCallback<ResLike>() {
                @Override
                public void onLoadFinish(ResLike data) {
                    showLoading(false);
                    showToast(data.getMsg());
                    video.setIscollection(0);
                    video.setCollection(video.getCollection() - 1);
                    mDataList.setValue(value);
                }
                @Override
                public void onLoadFail(int statusCode, String message) {
                    showLoading(false);
                    showToast(message);
                }
            });
        } else {
            mCategoryModel.requestCollection(video.getId(), new IRequestCallback<ResLike>() {
                @Override
                public void onLoadFinish(ResLike data) {
                    showLoading(false);
                    showToast(data.getMsg());
                    video.setIscollection(1);
                    video.setCollection(video.getCollection() + 1);
                    mDataList.setValue(value);
                }
                @Override
                public void onLoadFail(int statusCode, String message) {
                    showLoading(false);
                    showToast(message);
                }
            });
        }
    }

    public void sendComment(String message, int position) {
        if (!mCategoryModel.isLogin()) {
            showToast("未登录");
            mErrorCode.setValue(ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN);
            return;
        }
        ResList<ResCategoryVideoDetail> value = mDataList.getValue();
        if (value == null || position >= value.getList().size()) return;

        ResCategoryVideoDetail video = value.getList().get(position);
        showLoading(true);

        mCategoryModel.sendComment(video.getId(), message, new IRequestCallback<ResSendComment>() {
            @Override
            public void onLoadFinish(ResSendComment data) {
                showLoading(false);
                showToast("评论成功");
            }
            @Override
            public void onLoadFail(int statusCode, String message) {
                showLoading(false);
                showToast(message);
            }
        });
    }

}

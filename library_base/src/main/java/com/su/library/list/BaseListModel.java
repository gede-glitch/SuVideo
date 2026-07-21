package com.su.library.list;


import androidx.annotation.NonNull;

import com.su.library.config.ErrorStatusConfig;
import com.su.network.ApiCall;
import com.su.network.bean.ResBase;
import com.su.network.bean.ResList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseListModel<T> {
    private static final String TAG = "BaseListModel";
    private final IListListener<T> mListener;
    private int mPage = 1;
    private final int mLimit;

    public BaseListModel(IListListener<T> mListener, int mLimit) {
        this.mListener = mListener;
        this.mLimit = mLimit;
    }

    /**
     * 子类实现：根据 page、limit 构建 Retrofit Call
     */
    protected abstract Call<ResBase<ResList<T>>> creatCall(int page, int limit);

    public void requestData(boolean isFirst) {
        if (isFirst) {
            resetPage();
        } else {
            mPage++;
        }

        Call<ResBase<ResList<T>>> call = creatCall(mPage, mLimit);
        ApiCall.enqueueListCall(call, isFirst, new ApiCall.ListCallback<T>() {
            @Override
            public void onSuccess(boolean isFirst, ResList<T> data) {
                mListener.onLoadFinish(isFirst, data);
            }

            @Override
            public void onEmpty() {
                mListener.onLoadFail(ErrorStatusConfig.ERROR_STATUS_EMPTY, "server data is empty");
            }

            @Override
            public void onServerError(String message) {
                mListener.onLoadFail(ErrorStatusConfig.ERROR_SERVER_REQUEST, message);
            }

            @Override
            public void onNetworkError() {
                mListener.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL, "network request error");
            }
        });
    }

    public void resetPage() {
        mPage = 1;
    }

    // 加在 public void resetPage() 方法的后面
    public int getLimit() {
        return mLimit;
    }
}

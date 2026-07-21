package com.su.library.list;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.su.library.base.BaseViewModel;
import com.su.library.config.ErrorStatusConfig;
import com.su.network.bean.ResList;

public abstract class BaseListViewModel<T> extends BaseViewModel implements IListListener<T> {
    private static final String TAG = "BaseListViewModel";
    private final BaseListModel<T> mModel;
    public MutableLiveData<ResList<T>> mDataList = new MutableLiveData<>();
    public MutableLiveData<Boolean> mIsLoadMore = new MutableLiveData<>(true);
    private int mPageLimit = 10; // 默认10，子类可以覆写

    public BaseListViewModel() {
        this.mModel = createModel();
    }

    /**
     * 子类实现：构建具体的 BaseListModel
     *
     * @return 返回子类的Model
     */
    protected abstract BaseListModel<T> createModel();

    public void requestData(boolean isFirst) {
        if (isFirst) {
            mIsLoadMore.setValue(true);
        }
        mModel.requestData(isFirst);
    }

    @Override
    public void onLoadFinish(boolean isFirst, ResList<T> dataList) {
        if (isFirst) {
            mDataList.setValue(dataList);
        } else {
            ResList<T> current = mDataList.getValue();
            if (current != null) {
                current.getList().addAll(dataList.getList());
                mDataList.setValue(current);
            }
        }

        if (dataList == null || dataList.getList() == null || dataList.getList().size() < mPageLimit) {
            mIsLoadMore.setValue(false);
        }
    }

    @Override
    public void onLoadFail(int statusCode, String message) {
        mErrorCode.setValue(statusCode);
        if (statusCode == ErrorStatusConfig.ERROR_STATUS_EMPTY) {
            mIsLoadMore.setValue(false);
        }
    }

    public MutableLiveData<Boolean> getIsLoadMore() {
        return mIsLoadMore;
    }

    public MutableLiveData<ResList<T>> getDataList() {
        return mDataList;
    }
    public void setPageLimit(int limit) {
        this.mPageLimit = limit;
    }
}

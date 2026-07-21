package com.su.feature.find.ui.topic;

import androidx.lifecycle.MutableLiveData;

import com.su.feature.find.bean.ResTopic;
import com.su.library.base.BaseViewModel;
import com.su.library.base.IRequestCallback;

import java.util.List;

public class TopicViewModel extends BaseViewModel {
    private final TopicModel mModel;
    private MutableLiveData<List<ResTopic>> mData = new MutableLiveData<>();

    public TopicViewModel() {
        this.mModel = new TopicModel();
    }

    public void requestData() {
        showLoading(true);
        mModel.requestData(new IRequestCallback<List<ResTopic>>() {
            @Override
            public void onLoadFinish(List<ResTopic> datas) {
                showLoading(false);
                mData.setValue(datas);
            }

            @Override
            public void onLoadFail(int statusCode, String message) {
                showLoading(false);
                showToast(message);
            }
        });
    }

    public MutableLiveData<List<ResTopic>> getData() {
        return mData;
    }
}

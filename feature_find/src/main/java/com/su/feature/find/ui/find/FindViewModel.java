package com.su.feature.find.ui.find;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.su.data.video.bean.ResFindCategory;
import com.su.feature.find.bean.ResFind;
import com.su.feature.find.bean.ResFindAnchor;
import com.su.feature.find.bean.ResFindTopic;
import com.su.library.base.BaseViewModel;
import com.su.library.base.IRequestCallback;

import java.util.List;

public class FindViewModel extends BaseViewModel implements IRequestCallback<ResFind> {
    private static final String TAG = "FindViewModel";
    private final FindModel mModel;

    private MutableLiveData<List<ResFindCategory>> mCategory = new MutableLiveData<>();
    private MutableLiveData<List<ResFindAnchor>> mAnchor = new MutableLiveData<>();
    private MutableLiveData<List<ResFindTopic>> mTopic = new MutableLiveData<>();

    private MutableLiveData<FindAction> mAction = new MutableLiveData<>();

    public FindViewModel() {
        mModel = new FindModel();
    }

    public void loadFindData() {
        mModel.loadFindData(this);
        Log.i(TAG, "loadFindData: 请求发现页数据");
    }

    @Override
    public void onLoadFinish(ResFind datas) {
        Log.i(TAG, "onLoadFinish: " + datas.getCategory().size());
        mCategory.setValue(datas.getCategory());
        mAnchor.setValue(datas.getAnchor());
        mTopic.setValue(datas.getTopic());
    }

    @Override
    public void onLoadFail(int statusCode, String message) {
        Log.i(TAG, "onLoadFail: " + statusCode + message);
        mErrorCode.setValue(statusCode);
    }

    public MutableLiveData<List<ResFindCategory>> getCategory() {
        return mCategory;
    }

    public MutableLiveData<List<ResFindAnchor>> getAnchor() {
        return mAnchor;
    }

    public MutableLiveData<List<ResFindTopic>> getTopic() {
        return mTopic;
    }

    public MutableLiveData<FindAction> getAction() {
        return mAction;
    }

    public void startThemeListActivity() {
        mAction.setValue(FindAction.NAVIGATION_TO_THEME_LIST);
    }

    public void startTopicActivity() {
        mAction.setValue(FindAction.NAVIGATION_TO_TOPIC);
    }

    public enum FindAction {
        NAVIGATION_TO_THEME_LIST,   // 跳转到主题播单
        NAVIGATION_TO_TOPIC,// 跳转到话题广场
    }
}

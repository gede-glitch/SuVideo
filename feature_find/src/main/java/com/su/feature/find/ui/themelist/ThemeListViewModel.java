package com.su.feature.find.ui.themelist;

import androidx.lifecycle.MutableLiveData;

import com.su.feature.find.bean.ResThemeData;
import com.su.library.base.BaseViewModel;
import com.su.library.base.IRequestCallback;

import java.util.List;

public class ThemeListViewModel extends BaseViewModel {
    private final ThemeListModel mModel;
    private MutableLiveData<List<ResThemeData>> mData = new MutableLiveData<>();

    public ThemeListViewModel() {
        this.mModel = new ThemeListModel();
    }

    public void requestData() {
        showLoading(true);
        mModel.requestData(new IRequestCallback<List<ResThemeData>>() {
            @Override
            public void onLoadFinish(List<ResThemeData> datas) {
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

    public MutableLiveData<List<ResThemeData>> getData() {
        return mData;
    }
}

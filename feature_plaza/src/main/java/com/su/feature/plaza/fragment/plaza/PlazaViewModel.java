package com.su.feature.plaza.fragment.plaza;

import androidx.lifecycle.MutableLiveData;

import com.su.feature.plaza.bean.ResPlaza;
import com.su.library.base.BaseViewModel;
import com.su.library.base.IRequestCallback;
import com.su.library.config.ErrorStatusConfig;

import java.util.List;

public class PlazaViewModel extends BaseViewModel implements IRequestCallback<List<ResPlaza>> {
    private final PlazaModel mModel;
    private MutableLiveData<List<ResPlaza>> mDatas = new MutableLiveData<>();

    public PlazaViewModel(){
        mModel = new PlazaModel();
    }

    public void requestDatas() {
        mModel.requestDatas(this);
    }

    @Override
    public void onLoadFinish(List<ResPlaza> datas) {
        mDatas.setValue(datas);
    }

    public MutableLiveData<List<ResPlaza>> getDatas() {
        return mDatas;
    }

    @Override
    public void onLoadFail(int statusCode, String message) {
        mErrorCode.setValue(statusCode);
    }
}

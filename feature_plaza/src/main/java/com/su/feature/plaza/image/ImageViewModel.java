package com.su.feature.plaza.image;

import androidx.lifecycle.MutableLiveData;

import com.su.feature.plaza.bean.ResPlaza;
import com.su.library.base.BaseViewModel;

public class ImageViewModel extends BaseViewModel {
    private MutableLiveData<ResPlaza.PlazaDetail> mData = new MutableLiveData<>();

    public void updateData(ResPlaza.PlazaDetail detail) {
        mData.setValue(detail);
    }

    public MutableLiveData<ResPlaza.PlazaDetail> getData() {
        return mData;
    }
}

package com.su.feature.find.ui.categorydetail;

import androidx.lifecycle.MutableLiveData;

import com.su.data.video.bean.ResFindCategory;
import com.su.library.base.BaseViewModel;
import com.su.library.base.IRequestCallback;

public class CategoryDetailViewModel extends BaseViewModel {
    private final CategoryDetailModel mModel;
    private MutableLiveData<ResFindCategory> mCategoryDetail = new MutableLiveData<>();
    private MutableLiveData<String> mPepoleCount = new MutableLiveData<>();
    public CategoryDetailViewModel() {

        mModel = new CategoryDetailModel();
    }

    /**
     * 请求分类详情数据
     *
     * @param id
     */
    public void requestDatas(int id) {
        mModel.requestDatas(id, new IRequestCallback<ResFindCategory>() {
            @Override
            public void onLoadFinish(ResFindCategory datas) {
                mCategoryDetail.setValue(datas);
                mPepoleCount.setValue(String.format("%s万人参与·%s万人浏览", datas.getPeople(), datas.getBrowse()));
            }

            @Override
            public void onLoadFail(int statusCode, String message) {
                showToast(message);
            }
        });
    }

    public MutableLiveData<ResFindCategory> getCategoryDetail() {
        return mCategoryDetail;
    }

    public MutableLiveData<String> getPeopleCount() {
        return mPepoleCount;
    }
}

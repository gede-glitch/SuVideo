package com.su.feature.mediaplayer.ui.search;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.su.data.video.bean.ResVideoDetail;
import com.su.library.base.BaseViewModel;
import com.su.library.base.IRequestCallback;

import java.util.List;

public class SearchViewModel extends BaseViewModel {
    private static final String TAG = "SearchViewModel";
    //搜索关键词
    private MutableLiveData<String> mSearchKeyword = new MutableLiveData<>();

    //是否显示清除搜索内容的按钮 默认不显示
    private MutableLiveData<Integer> mShowCleanButton = new MutableLiveData<>(View.INVISIBLE);

    //搜索过的数据
    private MutableLiveData<List<ResVideoDetail.ArchivesInfoDTO>> mDatas = new MutableLiveData<>();

    private final SearchModel mModel;

    public SearchViewModel() {
        this.mModel = new SearchModel();
    }

    /**
     * 清除关键词
     */
    public void cleanSearchKeyword() {
        mSearchKeyword.setValue("");
    }

    public void searchByKeyword() {
        String keyword = mSearchKeyword.getValue();
        if (keyword != null && !keyword.isEmpty()) {
            showLoading(true);
            mModel.searchByKeyword(keyword, new IRequestCallback<List<ResVideoDetail.ArchivesInfoDTO>>() {
                @Override
                public void onLoadFinish(List<ResVideoDetail.ArchivesInfoDTO> datas) {
                    showLoading(false);
                    mDatas.setValue(datas);
                }

                @Override
                public void onLoadFail(int statusCode, String message) {
                    showLoading(false);
                    showToast(message);
                }
            });
        } else {
            showToast("请输入关键词后重试！");
        }
    }

    public void update(boolean isShow) {
        mShowCleanButton.setValue(isShow ? View.VISIBLE : View.INVISIBLE);
    }

    public MutableLiveData<String> getSearchKeyword() {
        return mSearchKeyword;
    }

    public MutableLiveData<Integer> getShowCleanButton() {
        return mShowCleanButton;
    }

    public MutableLiveData<List<ResVideoDetail.ArchivesInfoDTO>> getDatas() {
        return mDatas;
    }
}

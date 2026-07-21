package com.su.library.base;

import android.os.CountDownTimer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    public MutableLiveData<Integer> mErrorCode = new MutableLiveData<>();
    private MutableLiveData<String> mToastText = new MutableLiveData<>();

    private MutableLiveData<Boolean> mShowLoading = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> mFinish = new MutableLiveData<>();

    public void showToast(String text) {
        if (text == null || text.isEmpty()) {

            return;
        }
        mToastText.setValue(text);
        mToastText.setValue(null);
    }

    public void onFinishPage() {
        mFinish.setValue(true);
    }

    public void showLoading(boolean isLoading) {
        mShowLoading.setValue(isLoading);
    }

    public MutableLiveData<Integer> getErrorCode() {
        return mErrorCode;
    }

    public MutableLiveData<String> getToastText() {
        return mToastText;
    }

    public MutableLiveData<Boolean> getShowLoading() {
        return mShowLoading;
    }

    public MutableLiveData<Boolean> getFinish() {
        return mFinish;
    }
}

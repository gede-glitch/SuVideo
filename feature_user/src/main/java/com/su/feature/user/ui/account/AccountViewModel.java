package com.su.feature.user.ui.account;

import androidx.lifecycle.MutableLiveData;

import com.su.library.base.BaseViewModel;

import java.lang.invoke.MutableCallSite;

public class AccountViewModel extends BaseViewModel {
    private AccountModel mModel;
    private MutableLiveData<String> mMobile = new MutableLiveData<>();

    public AccountViewModel() {
        mModel = new AccountModel();

        mMobile.setValue(mModel.getMobile());
    }

    public void onAccountBindClick() {
        showToast("无法换绑！");
    }

    public MutableLiveData<String> getMobile() {
        return mMobile;
    }
}

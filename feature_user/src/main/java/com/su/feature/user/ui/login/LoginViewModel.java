package com.su.feature.user.ui.login;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.su.feature.user.bean.ResEmpty;
import com.su.feature.user.bean.ResMobileLogin;
import com.su.library.bean.ResUser;
import com.su.library.base.BaseViewModel;
import com.su.library.base.IRequestCallback;
import com.su.library.event.MessageEvent;
import com.su.network.bean.ResBase;

import org.greenrobot.eventbus.EventBus;

public class LoginViewModel extends BaseViewModel {
    private static final String TAG = "LoginViewModel";
    private final LoginModel mModel;
    MutableLiveData<String> mUserPhone = new MutableLiveData<>();
    MutableLiveData<String> mUserVerityCode = new MutableLiveData<>();
    MutableLiveData<Boolean> mEnableLogin = new MutableLiveData<>(false);
    MutableLiveData<Boolean> mEnableChoose = new MutableLiveData<>(false);
    MutableLiveData<String> mGetVerticalCodeText = new MutableLiveData<>("请输入验证码");
    MutableLiveData<Boolean> mIsEnableSendCode = new MutableLiveData<>(true);
    MutableLiveData<Boolean> mLoginSuccess = new MutableLiveData<>(false);
    private static final String mGetSmsString = "获取验证码";
    private CountDownTimer mDownTimer;

    public LoginViewModel() {
        mModel = getLoginModel();
    }

    @NonNull
    private static LoginModel getLoginModel() {
        return new LoginModel();
    }

    /**
     * 更新登录按钮的可用状态
     */
    public void isEnableLogin() {
        String mobile = mUserPhone.getValue();
        String code = mUserVerityCode.getValue();

        if (mobile == null || code == null) {
            return;
        }

        boolean isEnable = mobile.length() == 11 && code.length() == 4;
        mEnableLogin.setValue(isEnable);
    }

    public void sendCode() {
        String mobile = mUserPhone.getValue();
        if (mobile == null || mobile.length() != 11) {
            Log.i(TAG, "sendCode: 手机号不符合规则");
            showToast("手机号不符合规则!");
            return;
        }

        if (mDownTimer != null) {
            mDownTimer.cancel();
        }

        mIsEnableSendCode.setValue(false);
        mDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onFinish() {
                mGetVerticalCodeText.setValue(mGetSmsString);
                mIsEnableSendCode.setValue(true);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                int second = (int) (millisUntilFinished / 1000);
                mGetVerticalCodeText.setValue(second + "s");
            }
        }.start();

        Log.i(TAG, "sendCode: ");
        showLoading(true);
        mModel.sendSmsCode(mobile, new IRequestCallback<ResBase<ResEmpty>>() {
            @Override
            public void onLoadFinish(ResBase<ResEmpty> datas) {
                String msg = datas.getMsg();
                showToast(msg);
                showLoading(false);
            }

            @Override
            public void onLoadFail(int statusCode, String message) {
                showToast(message);
                showLoading(false);

                if (mDownTimer != null) {
                    mDownTimer.cancel();
                }
                mGetVerticalCodeText.setValue(mGetSmsString);
                mIsEnableSendCode.setValue(true);
            }
        });
    }

    public void login() {
        String mobile = mUserPhone.getValue();
        boolean checkAgreement = Boolean.TRUE.equals(mEnableChoose.getValue());
        if (!checkAgreement) {
            Log.i(TAG, "请先同意用户协议与隐私政策");
            showToast("请先同意用户协议与隐私政策");
            return;
        }
        Log.i(TAG, "login: ");
        showLoading(true);

        String code = mUserVerityCode.getValue();
        if (mobile == null || code == null) {
            Log.i(TAG, "手机号:" + mobile + " " + "验证码:" + code + " " + "有误！");
            showToast("手机号验证码有误，请重新输入");
            return;
        }

        mModel.mobileLogin(mobile, code, new IRequestCallback<ResBase<ResMobileLogin>>() {
            @Override
            public void onLoadFinish(ResBase<ResMobileLogin> datas) {
                showLoading(false);
                Log.i(TAG, "onLoadFinish: mobileLogin success, Data:" + datas.getData());
                Integer id = datas.getData().getId();
                getUserInfo(id);
            }

            @Override
            public void onLoadFail(int statusCode, String message) {
                showToast(message);
                showLoading(false);
            }
        });
    }

    private void getUserInfo(Integer id) {
        showLoading(true);
        mModel.getUserInfo(String.valueOf(id), new IRequestCallback<ResBase<ResUser>>() {
            @Override
            public void onLoadFinish(ResBase<ResUser> datas) {
                showLoading(false);
                // 发送一个已经登录的状态
                MessageEvent.LoginStatusEvent.post(true);
                mLoginSuccess.setValue(true);
            }

            @Override
            public void onLoadFail(int statusCode, String message) {
                showLoading(false);
                showToast(message);
            }
        });
    }

    public MutableLiveData<String> getUserPhone() {
        return mUserPhone;
    }

    public MutableLiveData<String> getUserVerityCode() {
        return mUserVerityCode;
    }

    public MutableLiveData<Boolean> getEnableLogin() {
        return mEnableLogin;
    }

    public MutableLiveData<Boolean> getEnableChoose() {
        return mEnableChoose;
    }

    public MutableLiveData<String> getGetVerticalCodeText() {
        return mGetVerticalCodeText;
    }

    public MutableLiveData<Boolean> getIsEnableSendCode() {
        return mIsEnableSendCode;
    }

    public MutableLiveData<Boolean> getLoginSuccess() {
        return mLoginSuccess;
    }
}

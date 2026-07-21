package com.su.feature.user.ui.resetpwd;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.su.feature.user.bean.ResEmpty;
import com.su.library.base.BaseViewModel;
import com.su.library.base.IRequestCallback;
import com.su.network.bean.ResBase;

public class ResetPasswordViewModel extends BaseViewModel {
    private static final String TAG = "ResetPasswordViewModel";
    private MutableLiveData<String> mMobile = new MutableLiveData<>();
    private MutableLiveData<String> mCode = new MutableLiveData<>();//用户输入的验证码
    private MutableLiveData<Boolean> mIsEnableLogin = new MutableLiveData<>(false);//登录按钮是否可用。默认不可用
    private MutableLiveData<String> mGetVerticalCodeText = new MutableLiveData<>("获取验证码");//获取验证码控件的显示文本
    private MutableLiveData<Boolean> mIsEnableSendCode = new MutableLiveData<>(true);//获取验证码控件是否可用
    private MutableLiveData<Boolean> mLoginSuccess = new MutableLiveData<>(false);//登录状态
    private MutableLiveData<String> mPassword1 = new MutableLiveData<>();
    private MutableLiveData<String> mPassword2 = new MutableLiveData<>();

    private ResetPasswordModel mModel;
    private CountDownTimer mDownTimer;

    public ResetPasswordViewModel() {
        mModel = new ResetPasswordModel();

        mMobile.setValue(mModel.getMobile());
    }

    /**
     * 发送验证码
     */
    public void sendCode() {
        String mobile = mMobile.getValue();

        if (mobile == null || mobile.length() != 11) {
            Log.i(TAG, "sendCode: 手机号不符合规则！");
            showToast("请输入正确的手机号码！");
            return;
        }

        if (mDownTimer != null) {
            mDownTimer.cancel();
        }

        mIsEnableSendCode.setValue(false);

        mDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onFinish() {
                mGetVerticalCodeText.setValue("获取验证码");
                mIsEnableSendCode.setValue(true);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                int second = (int) (millisUntilFinished / 1000);
                mGetVerticalCodeText.setValue(second + "s");
            }
        }.start();

        showLoading(true);
        mModel.sendSmsCode(new IRequestCallback<ResBase<ResEmpty>>() {
            @Override
            public void onLoadFinish(ResBase<ResEmpty> datas) {
                showLoading(false);
                String msg = datas.getMsg();
                showToast(msg);
            }

            @Override
            public void onLoadFail(int statusCode, String message) {
                showLoading(false);
                showToast(message);

                if (mDownTimer != null) {
                    mDownTimer.cancel();
                }
                mGetVerticalCodeText.setValue("获取验证码");
                mIsEnableSendCode.setValue(true);
            }
        });
    }

    /**
     * 重置密码
     */
    public void resetPassword() {
        String password1 = mPassword1.getValue();
        String password2 = mPassword2.getValue();
        String code = mCode.getValue();

        if (code == null || code.isEmpty()) {
            showToast("验证码不能为空");
            return;
        }

        if (password1 == null || password1.isEmpty()) {
            showToast("密码不能为空");
            return;
        }

        if (password2 == null || password2.isEmpty()) {
            showToast("请确认密码");
            return;
        }

        if (!password1.equals(password2)) {
            showToast("两次输入的密码不一致！");
            return;
        }

        showLoading(true);
        mModel.resetPassword(password1, code, new IRequestCallback<ResBase<ResEmpty>>() {
            @Override
            public void onLoadFinish(ResBase<ResEmpty> datas) {
                showLoading(false);
                showToast(datas.getMsg());
                onFinishPage();
            }

            @Override
            public void onLoadFail(int statusCode, String message) {
                showLoading(false);
                showToast(message);
            }
        });
    }

    /**
     * 是否允许点击重置密码的按钮
     */
    public void updateEnableResetBtnStatus() {
        String password1 = mPassword1.getValue();
        String password2 = mPassword2.getValue();
        String code = mCode.getValue();

        if (code == null || password1 == null || password2 == null) {
            return;
        }

        boolean isEnable = code.length() == 4 && password1.equals(password2);
        mIsEnableLogin.setValue(isEnable);
    }

    public MutableLiveData<String> getMobile() {
        return mMobile;
    }

    public MutableLiveData<String> getCode() {
        return mCode;
    }

    public MutableLiveData<Boolean> getIsEnableLogin() {
        return mIsEnableLogin;
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

    public MutableLiveData<String> getPassword1() {
        return mPassword1;
    }

    public MutableLiveData<String> getPassword2() {
        return mPassword2;
    }

}

package com.su.feature.user.bean;

public class ReqMobileLogin {
    private String mobile;
    private String captcha;

    public ReqMobileLogin(String mobile, String captcha) {
        this.mobile = mobile;
        this.captcha = captcha;
    }
}

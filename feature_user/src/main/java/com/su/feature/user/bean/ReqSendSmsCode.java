package com.su.feature.user.bean;

public class ReqSendSmsCode {
    private String mobile;      // 手机号
    private String event;       // 事件名称

    public ReqSendSmsCode(String mobile, String event) {
        this.mobile = mobile;
        this.event = event;
    }
}

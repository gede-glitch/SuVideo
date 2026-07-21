package com.su.library.event;

import org.greenrobot.eventbus.EventBus;

public class MessageEvent {
    public static class LoginStatusEvent {
        private boolean isLogin;

        public LoginStatusEvent(boolean isLogin) {
            this.isLogin = isLogin;
        }

        public boolean isLogin() {
            return isLogin;
        }

        public static void post(boolean isLogin) {
            EventBus.getDefault().postSticky(new LoginStatusEvent(isLogin));
        }
    }
}

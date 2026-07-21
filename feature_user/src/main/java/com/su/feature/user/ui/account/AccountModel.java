package com.su.feature.user.ui.account;

import com.su.library.manager.UserManager;

public class AccountModel {
    public boolean isLogin() {
        return UserManager.getInstance().isLogin();
    }

    public String getMobile() {
        if (isLogin()) {
            String mobile = UserManager.getInstance().getUserInfo().getUser().getUsername();
            return mobile.substring(0, 3) +
                    "****" +
                    mobile.substring(7);
        }
        return null;
    }
}

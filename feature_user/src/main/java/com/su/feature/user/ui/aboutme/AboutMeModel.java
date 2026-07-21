package com.su.feature.user.ui.aboutme;

import android.content.Context;

import com.su.library.utils.VersionUtils;

public class AboutMeModel {
    /**
     * 获取应用的版本名称
     *
     * @return 版本名称，如果获取失败则返回空字符串
     */
    public String getVersionName() {
        return VersionUtils.getVersionName();
    }

    /**
     * 获取应用的版本代码
     *
     * @return 版本代码，如果获取失败则返回 -1
     */
    public int getVersionCode() {
        return VersionUtils.getVersionCode();
    }
}

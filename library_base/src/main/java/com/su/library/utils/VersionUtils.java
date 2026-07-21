package com.su.library.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.su.library.base.BaseApplication;

public class VersionUtils {
    /**
     * 获取应用的版本名称
     *
     * @return 版本名称，如果获取失败则返回空字符串
     */
    public static String getVersionName() {
        try {
            Context context = BaseApplication.getContext();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取应用的版本代码
     *
     * @return 版本代码，如果获取失败则返回 -1
     */
    public static int getVersionCode() {
        try {
            Context context = BaseApplication.getContext();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                return (int) packageInfo.getLongVersionCode();
            } else {
                return packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }
}

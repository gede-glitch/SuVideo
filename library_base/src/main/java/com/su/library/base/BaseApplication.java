package com.su.library.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kingja.loadsir.core.LoadSir;
import com.su.library.callback.EmptyCallback;
import com.su.library.callback.ErrorCallback;
import com.su.library.callback.LoadingCallback;

import me.jessyan.autosize.AutoSizeConfig;

public class BaseApplication extends Application {

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 在调试模式下
        if (BuildConfig.DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        // 初始化 ARouter
        ARouter.init(this);
        // AndroidAutoSize 的参数初始化
        AutoSizeConfig.getInstance().setCustomFragment(true);

        LoadSir.beginBuilder()
                .addCallback(new LoadingCallback())
                .addCallback(new ErrorCallback())
                .addCallback(new EmptyCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}

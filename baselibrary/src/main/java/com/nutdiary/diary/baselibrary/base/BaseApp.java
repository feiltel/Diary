package com.nutdiary.diary.baselibrary.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nutdiary.diary.baselibrary.utils.AppUtils;


public class BaseApp extends Application {
    public static Context getContext() {
        return BaseApp.context;
    }

    private static Application context;

    @Override
    public void onCreate() {
        super.onCreate();
        BaseApp.context = this;
        //突破65535的限制
        MultiDex.install(this);
        //ARouter配置
        if (AppUtils.isDebug(this)) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }


}

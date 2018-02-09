package com.dazhi.renzhengtong;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/**
 * Created by mac on 2018/1/25.
 */

public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        Fresco.initialize(getApplicationContext());
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}


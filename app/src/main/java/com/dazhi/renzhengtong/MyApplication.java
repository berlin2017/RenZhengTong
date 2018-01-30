package com.dazhi.renzhengtong;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.concurrent.TimeUnit;

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
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}


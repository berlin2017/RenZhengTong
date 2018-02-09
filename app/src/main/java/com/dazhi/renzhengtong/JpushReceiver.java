package com.dazhi.renzhengtong;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.dazhi.renzhengtong.news.NewsDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by mac on 2018/2/9.
 */

public class JpushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        switch (intent.getAction()) {
            case JPushInterface.EXTRA_REGISTRATION_ID:
                String title = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.e("jpus的注册id为：", title);
                break;

            case JPushInterface.ACTION_MESSAGE_RECEIVED:
                String msgTitle = bundle.getString(JPushInterface.EXTRA_TITLE);
                String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                break;

            case JPushInterface.ACTION_NOTIFICATION_RECEIVED:
                String notTitle = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
                String notExtras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                break;

            case JPushInterface.ACTION_NOTIFICATION_OPENED:
                String title1 = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                String content1 = bundle.getString(JPushInterface.EXTRA_ALERT);
                String type1 = bundle.getString(JPushInterface.EXTRA_EXTRA);
                try {
                    JSONObject jsonObject = new JSONObject(type1);
                    if (!TextUtils.isEmpty(jsonObject.optString("id"))) {
                        Intent intent1 = new Intent(context,NewsDetailActivity.class);
                        intent1.putExtra("id",Integer.parseInt(jsonObject.optString("id")));
                        context.startActivity(intent1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}

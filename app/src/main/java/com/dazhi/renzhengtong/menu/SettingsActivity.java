package com.dazhi.renzhengtong.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.user.UserManager;
import com.dazhi.renzhengtong.utils.ToastHelper;

/**
 * Created by mac on 2018/1/27.
 */

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout pass_layout;
    private RelativeLayout about_layout;
    private Button loginout_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings_home);
        pass_layout = findViewById(R.id.settings_home_pass);
        about_layout = findViewById(R.id.settings_home_about);
        pass_layout.setOnClickListener(this);
        about_layout.setOnClickListener(this);
        loginout_btn = findViewById(R.id.settings_home_loginout);
        loginout_btn.setOnClickListener(this);
        initTitle();
    }

    public void initTitle(){
        ImageView back = findViewById(R.id.common_back);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        textView.setText("设置");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.common_back){
            finish();
        }else if (v.getId() == R.id.settings_home_about){
            Intent intent2 = new Intent(this,AboutActivtity.class);
            startActivity(intent2);
        }else if(v.getId() == R.id.settings_home_pass){
            if(UserManager.getUser(this)==null){
                ToastHelper.showToast("请登录后再试");
                return;
            }
            Intent intent = new Intent(this,ChangePassActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.settings_home_loginout){
            if(UserManager.getUser(this)==null){
                ToastHelper.showToast("请登录后再试");
                return;
            }
            UserManager.clearUser(this);
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
            Intent intent = new Intent("login_success");
            localBroadcastManager.sendBroadcastSync(intent);
            finish();
        }
    }
}

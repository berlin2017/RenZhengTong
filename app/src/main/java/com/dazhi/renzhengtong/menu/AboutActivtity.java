package com.dazhi.renzhengtong.menu;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.loading.SystemInfo;
import com.dazhi.renzhengtong.loading.SystemInfoManager;

import static com.dazhi.renzhengtong.MyApplication.context;

/**
 * Created by mac on 2018/1/27.
 */

public class AboutActivtity extends AppCompatActivity implements View.OnClickListener {


    private TextView email;
    private TextView version;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);

        initTitle();
        SystemInfo systemInfo = SystemInfoManager.getInfo(this);
        email = findViewById(R.id.about_email);
        if (!TextUtils.isEmpty(systemInfo.getSite_admin_email())){
            email.setText(String.format("商业合作: %s",systemInfo.getSite_admin_email()));
        }
        version = findViewById(R.id.about_version);

        PackageManager pm = context.getPackageManager();//context为当前Activity上下文
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            version.setText( pi.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void initTitle() {
        ImageView back = findViewById(R.id.common_back);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        textView.setText("关于");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.common_back) {
            finish();
        }
    }


}

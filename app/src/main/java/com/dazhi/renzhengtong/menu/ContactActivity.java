package com.dazhi.renzhengtong.menu;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.loading.SystemInfo;
import com.dazhi.renzhengtong.loading.SystemInfoManager;
import com.dazhi.renzhengtong.utils.Constant;
import com.dazhi.renzhengtong.utils.NetRequest;
import com.dazhi.renzhengtong.utils.ToastHelper;
import com.dazhi.renzhengtong.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by mac on 2018/2/6.
 */

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private SimpleDraweeView simpleDraweeView;
    private TextView info;
    private TextView qq;
    private TextView email;
    private TextView phone;
    private TextView location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu_contact);
        initTitle();
        progressBar = findViewById(R.id.menu_contact_progress);
        simpleDraweeView = findViewById(R.id.menu_contact_logo);
        info = findViewById(R.id.menu_contact_info);
        qq = findViewById(R.id.menu_contact_qq_value);
        email = findViewById(R.id.menu_contact_email_value);
        phone = findViewById(R.id.menu_contact_phone_value);
        location = findViewById(R.id.menu_contact_location_value);
        requestInfo();
    }

    private void requestInfo() {
        progressBar.setVisibility(View.VISIBLE);
        NetRequest.getFormRequest(Constant.SYSTEM_INFO_URL, null, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.optInt("code")==1){
                    SystemInfo systemInfo = Utils.decodeJSON(jsonObject.optString("data"),SystemInfo.class);
                    SystemInfoManager.saveInfo(getApplicationContext(),systemInfo);
                    update(systemInfo);
                }else{
                    ToastHelper.showToast(jsonObject.optString("msg"));
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastHelper.showToast(R.string.request_failed);
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    public void update(SystemInfo systemInfo){
        if (systemInfo!=null&& !TextUtils.isEmpty(systemInfo.getSite_logo())){
            simpleDraweeView.setImageURI(Uri.parse(systemInfo.getSite_logo()));
        }
        qq.setText(systemInfo.getSite_qq());
        phone.setText(systemInfo.getSite_tel());
        email.setText(systemInfo.getSite_admin_email());
        location.setText(systemInfo.getSite_address());
        info.setText(systemInfo.getSite_seo_description());
    }

    public void initTitle() {
        ImageView back = findViewById(R.id.common_back);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        textView.setText("联系我们");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.common_back:
                finish();
                break;
        }
    }

}

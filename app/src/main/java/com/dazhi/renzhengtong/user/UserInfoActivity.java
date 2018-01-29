package com.dazhi.renzhengtong.user;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dazhi.renzhengtong.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Admin on 2018/1/29 0029.
 */

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView name;
    private SimpleDraweeView simpleDraweeView;
    private TextView company;
    private TextView type;
    private TextView time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_userinfo);
        initTitle();
        name = findViewById(R.id.userinfo_name_value);
        simpleDraweeView = findViewById(R.id.userinfo_photo_image);
        company = findViewById(R.id.userinfo_company_value);
        time = findViewById(R.id.userinfo_time_value);
        type = findViewById(R.id.userinfo_type_value);
        UserInfo info = UserManager.getUser(this);
        if (info!=null){
            name.setText(info.getNickname());
            simpleDraweeView.setImageURI(Uri.parse(info.getPhoto()));
            company.setText(info.getCompany());
            time.setText(info.getTime());
            type.setText(info.getType());
        }
    }

    public void initTitle() {
        ImageView back = findViewById(R.id.common_back);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        textView.setText("我的资料");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.common_back:
                finish();
                break;

        }
    }
}

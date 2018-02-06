package com.dazhi.renzhengtong.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dazhi.renzhengtong.R;
import com.facebook.drawee.view.SimpleDraweeView;

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


        progressBar.setVisibility(View.GONE);
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

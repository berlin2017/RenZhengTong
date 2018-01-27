package com.dazhi.renzhengtong.news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dazhi.renzhengtong.R;

/**
 * Created by mac on 2018/1/27.
 */

public class LocationActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_location);
        initTitle();
    }

    public void initTitle() {
        RelativeLayout layout = findViewById(R.id.title_layout);
        layout.setBackgroundColor(Color.WHITE);
        ImageView back = findViewById(R.id.common_back);
        back.setImageResource(R.drawable.ic_wrong);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        textView.setTextColor(Color.BLACK);
        textView.setText("区域选择");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                finish();
                break;
        }
    }
}

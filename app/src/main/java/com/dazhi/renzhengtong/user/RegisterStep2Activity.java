package com.dazhi.renzhengtong.user;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.utils.ToastHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 2018/1/31 0031.
 */

public class RegisterStep2Activity extends AppCompatActivity implements View.OnClickListener {

    private EditText type;
    private EditText time;
    private EditText name;
    private EditText company;
    private Button commitBtn;
    private List<String> list = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register_step2);
        initTitle();
        time = findViewById(R.id.register_time);
        type = findViewById(R.id.register_type);
        name = findViewById(R.id.register_name);
        company = findViewById(R.id.register_company);
        commitBtn = findViewById(R.id.register_confirm_btn);
        progressBar = findViewById(R.id.register_loading);
        progressBar.setVisibility(View.GONE);
        time.setOnClickListener(this);
        commitBtn.setOnClickListener(this);
        type.setOnClickListener(this);
        list.add("1-10人");
        list.add("11-20人");
        list.add("21-50人");
        list.add("51-100人");
        list.add("101-200人");
        list.add("200人以上");
    }

    public void initTitle() {
        ImageView back = findViewById(R.id.common_back);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        textView.setText("注册");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_confirm_btn:
                finish();
                break;
            case R.id.common_back:
                finish();
                break;
            case R.id.register_time:
                boolean[]types = {true,true,true,false,false,false};
                TimePickerView pickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD");
                        String timestring = simpleDateFormat.format(date);
                        time.setText(timestring);
                    }
                }).setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示
                        .setDividerColor(Color.DKGRAY)
                        .setContentSize(20)
                        .setType(types)
                        .setDate(Calendar.getInstance()).build();
                pickerView.show();
                break;
            case R.id.register_type:
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        type.setText(list.get(options1));
                    }
                }).build();
                pvOptions.setPicker(list);
                pvOptions.show();
                break;
            default:
                break;
        }
    }

    public void commit() {

    }

}

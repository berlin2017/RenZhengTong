package com.dazhi.renzhengtong.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.dazhi.renzhengtong.utils.Constant;
import com.dazhi.renzhengtong.utils.NetRequest;
import com.dazhi.renzhengtong.utils.ToastHelper;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

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
    private int uid = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uid = getIntent().getIntExtra("uid",0);
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
        TextView skip = findViewById(R.id.common_title_right);
        skip.setVisibility(View.VISIBLE);
        skip.setText("跳过");
        skip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_confirm_btn:
                commit();
                break;
            case R.id.common_back:
                finish();
                break;
            case R.id.register_time:
                boolean[] types = {true, true, true, false, false, false};
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

            case R.id.common_title_right:
                finish();
                break;
            default:
                break;
        }
    }

    public void commit() {
        String nameSting = name.getText().toString();
        String companyString = company.getText().toString();
        String typeString = type.getText().toString();
        String timeString = time.getText().toString();

        //networking
        progressBar.setVisibility(View.VISIBLE);
        HashMap<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(nameSting)){
            map.put("user_nickname", nameSting);
        }else{
            ToastHelper.showToast("请填写姓名和公司名称");
            return;
        }
        if (!TextUtils.isEmpty(companyString)){
            map.put("company", companyString);
        }else{
            ToastHelper.showToast("请填写姓名和公司名称");
            return;
        }
        if (!TextUtils.isEmpty(typeString)){
            map.put("rztype", typeString);
        }
        if (!TextUtils.isEmpty(timeString)){
            map.put("rztime", timeString);
        }
        map.put("uid",uid+"");

        NetRequest.postFormRequest(Constant.USRE_REGISTER2_URL, map, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.optInt("code") == 1) {
                    progressBar.setVisibility(View.GONE);
                    finish();
                } else {
                    ToastHelper.showToast(jsonObject.optString("msg"));
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastHelper.showToast("请求失败,请重试");
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}

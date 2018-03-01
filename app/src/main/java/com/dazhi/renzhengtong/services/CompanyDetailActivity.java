package com.dazhi.renzhengtong.services;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dazhi.renzhengtong.MyProgressDialog;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.loading.SystemInfoManager;
import com.dazhi.renzhengtong.menu.model.MenuJGModel;
import com.dazhi.renzhengtong.services.adapter.DetailImageAdapter;
import com.dazhi.renzhengtong.services.model.JGDetail;
import com.dazhi.renzhengtong.user.LoginActivity;
import com.dazhi.renzhengtong.user.UserManager;
import com.dazhi.renzhengtong.utils.Constant;
import com.dazhi.renzhengtong.utils.NetRequest;
import com.dazhi.renzhengtong.utils.ToastHelper;
import com.dazhi.renzhengtong.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * Created by mac on 2018/2/27.
 */

public class CompanyDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private TextView location_tv;
    private TextView info_tv;
    private TextView product_tv;
    private Button call_btn;
    private Button follow_btn;
    private TextView name_tv;
    private TextView remake_tv;
    private TextView number_tv;
    private MenuJGModel model;
    private SimpleDraweeView simpleDraweeView;
    private int id = 0;
    private DetailImageAdapter adapter;
    private List<String> images = new ArrayList<>();
    private MyProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_company_detail);
        initTitle();
        id = getIntent().getIntExtra("id", 0);
        if (id == 0) {
            ToastHelper.showToast("机构不存在");
            return;
        }
        initView();
        progressDialog = new MyProgressDialog(this,R.style.Dialog);
        requestDetail();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (viewPager.getCurrentItem()==images.size()-1){
                viewPager.setCurrentItem(0);
            }else{
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
            sendEmptyMessageDelayed(0,3000);
        }
    };

    public void requestDetail() {
        progressDialog.show();
        HashMap<String,String>map = new HashMap<>();
        map.put("id", id+"");
        NetRequest.postFormRequest(Constant.COMPANY_DETAIL_URL, map, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.optInt("code")==1){
                    model = Utils.decodeJSON(jsonObject.optString("data"),MenuJGModel.class);
                    updateInfo();
                }else{
                    ToastHelper.showToast(jsonObject.optString("msg"));
                }
                progressDialog.dismiss();
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastHelper.showToast(R.string.request_failed);
                progressDialog.dismiss();
            }
        });

    }

    public void updateInfo() {

        if (model != null) {
            location_tv.setText(model.getAddress());
            info_tv.setText(model.getIntro());
            product_tv.setText(model.getServices());
            name_tv.setText(model.getJgname());
            remake_tv.setText(model.getRemark());
            number_tv.setText(model.getGzrs() + "");
            if (!TextUtils.isEmpty(model.getJglogo())) {
                simpleDraweeView.setImageURI(Uri.parse(Constant.BASE_URL + model.getJglogo()));
            }
            images.clear();
            if (model.getImages() != null && model.getImages().size() > 0) {
                images.addAll(model.getImages());
                adapter.notifyDataSetChanged();
                handler.sendEmptyMessageDelayed(0,3000);
            }
        }
    }


    public void initView() {
        simpleDraweeView = findViewById(R.id.company_detail_icon);
        viewPager = findViewById(R.id.company_detail_viewpager);
        location_tv = findViewById(R.id.company_detail_location);
        info_tv = findViewById(R.id.company_detail_info);
        product_tv = findViewById(R.id.company_detail_product);
        call_btn = findViewById(R.id.company_detail_call);
        follow_btn = findViewById(R.id.company_detail_follow);
        name_tv = findViewById(R.id.company_detail_name);
        remake_tv = findViewById(R.id.company_detail_remake);
        number_tv = findViewById(R.id.company_detail_number);
        adapter = new DetailImageAdapter(images, this);
        viewPager.setAdapter(adapter);
        call_btn.setOnClickListener(this);
        follow_btn.setOnClickListener(this);
    }

    public void initTitle() {
        ImageView back = findViewById(R.id.common_back);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        textView.setText("机构详情");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.company_detail_call:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("拨打电话").setMessage(SystemInfoManager.getInfo(this).getSite_tel()).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ContextCompat.checkSelfPermission(CompanyDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ||
                                ContextCompat.checkSelfPermission(CompanyDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(CompanyDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + SystemInfoManager.getInfo(CompanyDetailActivity.this).getSite_tel()));
                            startActivity(intent);
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
                break;

            case R.id.company_detail_follow:
                follow();
                break;
        }
    }

    public void follow(){
        progressDialog.show();
        if(UserManager.getUser(this)==null){
            ToastHelper.showToast("请先登录");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return;
        }
        HashMap<String,String>map = new HashMap<>();
        map.put("jgid", id+"");
        map.put("uid",UserManager.getUser(this).getId()+"");
        map.put("status","1");
        NetRequest.postFormRequest(Constant.COMPANY_FOLLOW_URL, map, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.optInt("code")==1){
                   ToastHelper.showToast("关注成功");
                }else{
                    ToastHelper.showToast(jsonObject.optString("msg"));
                }
                progressDialog.dismiss();
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastHelper.showToast(R.string.request_failed);
                progressDialog.dismiss();
            }
        });
    }
}

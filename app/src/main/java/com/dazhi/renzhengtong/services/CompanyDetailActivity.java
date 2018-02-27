package com.dazhi.renzhengtong.services;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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

import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.loading.SystemInfoManager;
import com.dazhi.renzhengtong.services.adapter.DetailImageAdapter;
import com.dazhi.renzhengtong.services.model.JGDetail;
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
    private JGDetail detail;
    private SimpleDraweeView simpleDraweeView;
    private String id;
    private DetailImageAdapter adapter;
    private List<String> images = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");
        if (TextUtils.isEmpty(id)) {
            ToastHelper.showToast("机构不存在");
        }
        setContentView(R.layout.layout_company_detail);
        initTitle();
        initView();
        requestPage();
        updateInfo();
    }

    private void requestPage() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        NetRequest.postFormRequest(Constant.COMPANY_DETAIL_URL, map, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject jsonObject = new JSONObject(result);
                detail = Utils.decodeJSON(jsonObject.optString("detail"), JGDetail.class);
                updateInfo();
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastHelper.showToast(R.string.request_failed);
            }
        });
    }

    private void updateInfo() {
        if (detail!=null){
            location_tv.setText(detail.getLocation());
            info_tv.setText(detail.getInfo());
            product_tv.setText(detail.getProduct_info());
            name_tv.setText(detail.getName());
            remake_tv.setText(detail.getRemake());
            number_tv.setText(detail.getNumber() + "");
            simpleDraweeView.setImageURI(Uri.parse(detail.getPhoto()));
            images = detail.getImages();
        }
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1519708753095&di=00021a07fca9d7a60f277831b6e173cc&imgtype=0&src=http%3A%2F%2Fwap.365jia.cn%2Fuploads%2Fnews%2Ffolder_1436127%2Fimages%2Fded72671afd94a45ecdda8132ce6288b.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1520303492&di=900fffe3dbec0e7c354ee61fe188ed1c&imgtype=jpg&er=1&src=http%3A%2F%2Fimages2015.cnblogs.com%2Fblog%2F1014286%2F201608%2F1014286-20160827225341429-1650107896.png");
        adapter.notifyDataSetChanged();
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
                        if (ContextCompat.checkSelfPermission(CompanyDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED||
                                ContextCompat.checkSelfPermission(CompanyDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED   ){
                            ActivityCompat.requestPermissions(CompanyDetailActivity.this,new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                        }else {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+SystemInfoManager.getInfo(CompanyDetailActivity.this).getSite_tel()));
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

                break;
        }
    }
}
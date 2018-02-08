package com.dazhi.renzhengtong.menu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.menu.model.MenuJGModel;
import com.dazhi.renzhengtong.user.UserManager;
import com.dazhi.renzhengtong.utils.Constant;
import com.dazhi.renzhengtong.utils.NetRequest;
import com.dazhi.renzhengtong.utils.ToastHelper;
import com.dazhi.renzhengtong.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * Created by mac on 2018/2/6.
 */

public class MenuJGDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private int id = 0;
    private ProgressBar progressBar;
    private SimpleDraweeView simpleDraweeView;
    private EditText company;
    private EditText name;
    private EditText phone;
    private Button save;
    private PopupWindow pop;
    private View rootview;
    private MenuJGModel model;
    private Bitmap select_image;
    private final String PATH = "mnt/sdcard/jglogo.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("id", 0);
        rootview = getLayoutInflater().inflate(R.layout.layout_menu_jigou_detail, null);
        setContentView(rootview);
        initTitle();
        progressBar = findViewById(R.id.jigou_detail_progressbar);
        simpleDraweeView = findViewById(R.id.jigou_detail_logo);
        simpleDraweeView.setOnClickListener(this);
        company = findViewById(R.id.jigou_detail_company_edit);
        name = findViewById(R.id.jigou_detail_name_edit);
        phone = findViewById(R.id.jigou_detail_phone_edit);
        save = findViewById(R.id.jigou_detail_save);
        save.setOnClickListener(this);
        if (id != 0) {
            requestDetail();
        }
    }

    public void initTitle() {
        ImageView back = findViewById(R.id.common_back);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        if (id != 0) {
            textView.setText("机构详情");
            TextView del = findViewById(R.id.common_title_right);
            del.setVisibility(View.VISIBLE);
            del.setOnClickListener(this);
        }else{
            textView.setText("机构认证");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.common_title_right:
                delete();
                break;

            case R.id.jigou_detail_save:
                commit();
                break;

            case R.id.jigou_detail_logo:
                showPopupWindow();
                break;
        }

    }

    public void requestDetail() {
        progressBar.setVisibility(View.VISIBLE);
        HashMap<String,String>map = new HashMap<>();
        map.put("id", id+"");
        map.put("uid",UserManager.getUser(this).getId()+"");
        NetRequest.postFormRequest(Constant.MENU_JIGOU_INFO_URL, map, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.optInt("code")==1){
                    model = Utils.decodeJSON(jsonObject.optString("data"),MenuJGModel.class);
                    progressBar.setVisibility(View.GONE);
                    updateInfo();
                }else{
                    ToastHelper.showToast(jsonObject.optString("msg"));
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastHelper.showToast(R.string.request_failed);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void updateInfo(){
        if (model!=null){
            company.setText(model.getJgname());
            name.setText(model.getJgxm());
            phone.setText(model.getJgtel());
            if (!TextUtils.isEmpty(model.getJglogo())){
                simpleDraweeView.setImageURI(Uri.parse(Constant.BASE_URL+model.getJglogo()));
            }
        }
    }


    public void commit() {
        progressBar.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(company.getText().toString())) {
            progressBar.setVisibility(View.GONE);
            ToastHelper.showToast("公司名称不能为空");
            return;
        }
        if (TextUtils.isEmpty(name.getText().toString())) {
            progressBar.setVisibility(View.GONE);
            ToastHelper.showToast("姓名不能为空");
            return;
        }
        if (TextUtils.isEmpty(phone.getText().toString())) {
            progressBar.setVisibility(View.GONE);
            ToastHelper.showToast("电话不能为空");
            return;
        }

        if (id!=0){
            HashMap<String,String>map = new HashMap<>();
            map.put("jgname",company.getText().toString());
            map.put("jgxm",name.getText().toString());
            map.put("jgtel",phone.getText().toString());
            map.put("uid", com.dazhi.renzhengtong.user.UserManager.getUser(this).getId()+"");
            map.put("id", id+"");
//            NetRequest.postFormRequest(Constant.MENU_JIGOU_UPDATE_URL, map, new NetRequest.DataCallBack() {
//                @Override
//                public void requestSuccess(String result) throws Exception {
//                    JSONObject jsonObject = new JSONObject(result);
//                    if (jsonObject.optInt("code")==1){
//                        ToastHelper.showToast("修改成功");
//                        progressBar.setVisibility(View.GONE);
//                        finish();
//                    }else{
//                        ToastHelper.showToast(jsonObject.optString("msg"));
//                        progressBar.setVisibility(View.GONE);
//                    }
//                }
//
//                @Override
//                public void requestFailure(Request request, IOException e) {
//                    ToastHelper.showToast(R.string.request_failed);
//                    progressBar.setVisibility(View.GONE);
//                }
//            });
            File file = null;
            if (select_image != null) {
                file = new File(PATH);
            }

            NetRequest.postFile(Constant.MENU_JIGOU_UPDATE_URL, map, "jglogo", file, new NetRequest.DataCallBack() {

                @Override
                public void requestSuccess(String result) throws Exception {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.optInt("code")==1){
                        ToastHelper.showToast("修改成功");
                        progressBar.setVisibility(View.GONE);
                        finish();
                    }else{
                        ToastHelper.showToast(jsonObject.optString("msg"));
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void requestFailure(Request request, IOException e) {
                    ToastHelper.showToast(R.string.request_failed);
                    progressBar.setVisibility(View.GONE);
                }
            });
        }else{
            HashMap<String,String>map = new HashMap<>();
            map.put("jgname",company.getText().toString());
            map.put("jgxm",name.getText().toString());
            map.put("jgtel",phone.getText().toString());
            map.put("uid", com.dazhi.renzhengtong.user.UserManager.getUser(this).getId()+"");
//            NetRequest.postFormRequest(Constant.USRE_JGRZ_URL, map, new NetRequest.DataCallBack() {
//                @Override
//                public void requestSuccess(String result) throws Exception {
//                    JSONObject jsonObject = new JSONObject(result);
//                    if (jsonObject.optInt("code")==1){
//                        ToastHelper.showToast("认证成功");
//                        progressBar.setVisibility(View.GONE);
//                        setResult(2000);
//                        finish();
//                    }else{
//                        ToastHelper.showToast(jsonObject.optString("msg"));
//                        progressBar.setVisibility(View.GONE);
//                    }
//                }
//
//                @Override
//                public void requestFailure(Request request, IOException e) {
//                    ToastHelper.showToast(R.string.request_failed);
//                    progressBar.setVisibility(View.GONE);
//                }
//            });

            File file = null;
            if (select_image != null) {
                file = new File(PATH);
            }

            NetRequest.postFile(Constant.USRE_JGRZ_URL, map, "logo", file, new NetRequest.DataCallBack() {

                @Override
                public void requestSuccess(String result) throws Exception {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.optInt("code")==1){
                        ToastHelper.showToast("认证成功");
                        progressBar.setVisibility(View.GONE);
                        setResult(2000);
                        finish();
                    }else{
                        ToastHelper.showToast(jsonObject.optString("msg"));
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void requestFailure(Request request, IOException e) {
                    ToastHelper.showToast(R.string.request_failed);
                    progressBar.setVisibility(View.GONE);
                }
            });
        }

    }



    public void delete() {
        progressBar.setVisibility(View.VISIBLE);
        HashMap<String,String>map = new HashMap<>();
        map.put("uid", com.dazhi.renzhengtong.user.UserManager.getUser(this).getId()+"");
        map.put("id", id+"");
        NetRequest.postFormRequest(Constant.MENU_JIGOU_DELETE_URL, map, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.optInt("code")==1){
                    ToastHelper.showToast("删除成功");
                    progressBar.setVisibility(View.GONE);
                    setResult(2000);
                    finish();
                }else{
                    ToastHelper.showToast(jsonObject.optString("msg"));
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastHelper.showToast(R.string.request_failed);
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            cutImage(data.getData());
        } else if (requestCode == 2000 && resultCode == RESULT_OK) {
            cutImage(data.getData());
        } else if (requestCode == 3000 && resultCode == RESULT_OK) {
            Bitmap bitmap = data.getExtras().getParcelable("data");
            select_image = bitmap;
            setFile(select_image);
            simpleDraweeView.setImageBitmap(bitmap);
        }
    }

    public void cutImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", "1");
        intent.putExtra("aspectY", "1");
        intent.putExtra("outputX", "200");
        intent.putExtra("outputY", "200");
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3000);
    }

    private void setFile(Bitmap photo) {
        File file = new File(PATH);
        try {
            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(file));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bout);
            bout.flush();
            bout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showPopupWindow() {
        pop = new PopupWindow(this);
        View view = getLayoutInflater().inflate(R.layout.layout_imagepick_pop,
                null);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setAnimationStyle(R.style.MyPopStyle);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        Button bt1 = (Button) view.findViewById(R.id.imagepick_pop_local);
        Button bt2 = (Button) view.findViewById(R.id.imagepick_pop_camer);
        Button bt3 = (Button) view.findViewById(R.id.imagepick_pop_cancel);
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1000);
                pop.dismiss();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent2, 2000);
                pop.dismiss();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
            }
        });
        pop.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

}

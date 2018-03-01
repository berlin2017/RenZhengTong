package com.dazhi.renzhengtong.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dazhi.renzhengtong.MyProgressDialog;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.loading.SystemInfo;
import com.dazhi.renzhengtong.loading.SystemInfoManager;
import com.dazhi.renzhengtong.user.UserManager;
import com.dazhi.renzhengtong.utils.Constant;
import com.dazhi.renzhengtong.utils.NetRequest;
import com.dazhi.renzhengtong.utils.ToastHelper;
import com.dazhi.renzhengtong.utils.Utils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

/**
 * Created by mac on 2018/1/27.
 */

public class ChangePassActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText old_edit;
    private EditText new_edit;
    private EditText confirm_edit;
    private Button confirm_btn;
    private ProgressBar progressBar;
    private MyProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_frogetpass);
        progressDialog = new MyProgressDialog(this,R.style.Dialog);
        initTitle();

        old_edit = findViewById(R.id.froget_old);
        new_edit = findViewById(R.id.froget_new);
        confirm_edit = findViewById(R.id.froget_new_confirm);
        old_edit.addTextChangedListener(this);
        new_edit.addTextChangedListener(this);
        confirm_edit.addTextChangedListener(this);

        confirm_btn = findViewById(R.id.froget_confirm_btn);
        confirm_btn.setOnClickListener(this);
        confirm_btn.setEnabled(false);
        progressBar = findViewById(R.id.froget_progress);
    }

    public void initTitle() {
        ImageView back = findViewById(R.id.common_back);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        textView.setText("修改密码");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.common_back) {
            finish();
        }else if (v.getId() == R.id.froget_confirm_btn){
            commit();
        }
    }

    private void commit() {
        if (TextUtils.isEmpty(old_edit.getText().toString())){
            ToastHelper.showToast("旧密码不能为空");
            old_edit.setError("旧密码不能为空");
            return;
        }

        if (TextUtils.isEmpty(new_edit.getText().toString())){
            ToastHelper.showToast("新密码不能为空");
            new_edit.setError("新密码不能为空");
            return;
        }

        if (!TextUtils.isEmpty(new_edit.getText().toString())&&new_edit.getText().toString().length()<=4){
            ToastHelper.showToast(R.string.error_invalid_password);
            new_edit.setError(getString(R.string.error_invalid_password));
            return;
        }

        if (TextUtils.isEmpty(confirm_edit.getText().toString())){
            ToastHelper.showToast("请确认密码");
            confirm_edit.setError("请确认密码");
            return;
        }

        if (!TextUtils.isEmpty(confirm_edit.getText().toString())&&confirm_edit.getText().toString().length()<=4){
            ToastHelper.showToast(R.string.error_invalid_password);
            confirm_edit.setError(getString(R.string.error_invalid_password));
            return;
        }


        if (!new_edit.getText().toString().equals(confirm_edit.getText().toString())){
            ToastHelper.showToast("密码不一致");
            confirm_edit.setError("密码不一致");
            return;
        }

//        progressBar.setVisibility(View.VISIBLE);
        progressDialog.show();
        HashMap<String,String>map = new HashMap<>();
        map.put("password",old_edit.getText().toString());
        map.put("newpassword",confirm_edit.getText().toString());
        map.put("mobile", UserManager.getUser(this).getMobile());
        NetRequest.getFormRequest(Constant.USER_CHNAGEPASS_URL, null, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.optInt("code")==1){
                    ToastHelper.showToast("修改密码成功");
                }else{
                    ToastHelper.showToast(jsonObject.optString("msg"));
                }
                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastHelper.showToast(R.string.request_failed);
                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
            }
        });
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        confirm_btn.setBackgroundResource(R.drawable.drawable_evaluation_btn_gray);
        confirm_btn.setEnabled(false);
        if (!old_edit.getText().toString().isEmpty()) {
            if (!new_edit.getText().toString().isEmpty()) {
                if (!confirm_edit.getText().toString().isEmpty()) {
                    confirm_btn.setBackgroundResource(R.drawable.drawable_evaluation_btn);
                    confirm_btn.setEnabled(true);
                }
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

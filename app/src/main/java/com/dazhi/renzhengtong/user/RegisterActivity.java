package com.dazhi.renzhengtong.user;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dazhi.renzhengtong.MyProgressDialog;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.utils.Constant;
import com.dazhi.renzhengtong.utils.NetRequest;
import com.dazhi.renzhengtong.utils.ToastHelper;

import org.json.JSONObject;

import java.io.IOException;
import java.sql.Time;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Request;

/**
 * Created by mac on 2018/1/27.
 */

public class RegisterActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    private EditText phone_edit;
    private EditText code_edit;
    private EditText pass_edit;
    private Button send_btn;
    private Button confirm_btn;
    private TextView rule_textview;
    private TextView login_textview;
    private TimeCount timeCount;
    private ProgressBar progressBar;
    private CheckBox checkBox;
    private MyProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        progressDialog = new MyProgressDialog(this,R.style.Dialog);
        checkBox = findViewById(R.id.register_checkbox);
        phone_edit = findViewById(R.id.register_phone_edit);
        code_edit = findViewById(R.id.register_code_edit);
        pass_edit = findViewById(R.id.register_pass_edit);
        send_btn = findViewById(R.id.register_send_btn);
        confirm_btn = findViewById(R.id.register_confirm_btn);
        rule_textview = findViewById(R.id.register_rule_textview);
        login_textview = findViewById(R.id.register_login_textview);

        phone_edit.addTextChangedListener(this);
        code_edit.addTextChangedListener(this);
        pass_edit.addTextChangedListener(this);
        confirm_btn.setEnabled(false);

        send_btn.setOnClickListener(this);
        confirm_btn.setOnClickListener(this);
        login_textview.setOnClickListener(this);

        timeCount = new TimeCount(60000, 1000);
        progressBar = findViewById(R.id.register_loading);
        progressBar.setVisibility(View.GONE);
        initTitle();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        confirm_btn.setBackgroundResource(R.drawable.drawable_evaluation_btn_gray);
//        confirm_btn.setEnabled(false);
        if (!phone_edit.getText().toString().isEmpty()) {
//            if (!code_edit.getText().toString().isEmpty()) {
                if (!pass_edit.getText().toString().isEmpty()) {
                    confirm_btn.setEnabled(true);
                    confirm_btn.setBackgroundResource(R.drawable.drawable_evaluation_btn);
//                }
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void initTitle() {
        ImageView back = findViewById(R.id.common_back);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        textView.setText("注册");
    }


    public void commit() {

        String mobile = phone_edit.getText().toString();
        String code = code_edit.getText().toString();
        String pass = pass_edit.getText().toString();

        // Check for a valid email address.
        if (TextUtils.isEmpty(mobile)) {
            phone_edit.setError(getString(R.string.error_field_required));
            return;
        } else if (!isPhoneNumber(mobile)) {
            phone_edit.setError(getString(R.string.error_invalid_email));
            return;
        }
//
//        if (TextUtils.isEmpty(code)) {
//            code_edit.setError("请输入验证码");
//            return;
//        }

        if (TextUtils.isEmpty(pass)) {
            pass_edit.setError("请输入密码");
        } else if (!isPasswordValid(pass)) {
            pass_edit.setError(getString(R.string.error_invalid_password));
            return;
        }

        if (!checkBox.isChecked()){
            ToastHelper.showToast("请同意认证服务条款");
            return;
        }

        //networking
//        progressBar.setVisibility(View.VISIBLE);
        progressDialog.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("username", phone_edit.getText().toString());
        map.put("password", pass_edit.getText().toString());
        map.put("device_type", "android");
        NetRequest.postFormRequest(Constant.USRE_REGISTER_URL, map, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.optInt("code") == 1) {
//                    Intent intent = new Intent(RegisterActivity.this, RegisterStep2Activity.class);
//                    intent.putExtra("uid",jsonObject.optJSONObject("data").optJSONObject("info").optInt("id"));
//                    startActivity(intent);
                    ToastHelper.showToast("注册成功");
                    finish();
                } else {
                    ToastHelper.showToast(jsonObject.optString("msg"));
                }
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

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    /**
     * 判断手机号是否符合规范
     *
     * @param phoneNo 输入的手机号
     * @return
     */
    public static boolean isPhoneNumber(String phoneNo) {
        if (TextUtils.isEmpty(phoneNo)) {
            return false;
        }
        if (phoneNo.length() == 11) {
            for (int i = 0; i < 11; i++) {
                if (!PhoneNumberUtils.isISODigit(phoneNo.charAt(i))) {
                    return false;
                }
            }
            Pattern p = Pattern.compile("^((13[^4,\\D])" + "|(134[^9,\\D])" +
                    "|(14[5,7])" +
                    "|(15[^4,\\D])" +
                    "|(17[3,6-8])" +
                    "|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(phoneNo);
            return m.matches();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register_confirm_btn) {
            commit();
        } else if (v.getId() == R.id.register_send_btn) {
            timeCount.start();
        } else if (v.getId() == R.id.common_back) {
            finish();
        } else if (v.getId() == R.id.register_login_textview) {
            finish();
        }
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            send_btn.setBackgroundResource(R.drawable.drawable_sendcode_unuse);
            send_btn.setEnabled(false);
            send_btn.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            send_btn.setText("获取验证码");
            send_btn.setEnabled(true);
            send_btn.setBackgroundResource(R.drawable.drawable_sendcode);

        }
    }
}

package com.dazhi.renzhengtong.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dazhi.renzhengtong.R;

/**
 * Created by mac on 2018/1/27.
 */

public class FrogetChangePassActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText first_edit;
    private EditText secend_edit;
    private Button confirm_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_froget_change);
        initTitle();

        first_edit = findViewById(R.id.froget_change_new_edit);
        secend_edit = findViewById(R.id.froget_change_new_edit2);
        first_edit.addTextChangedListener(this);
        secend_edit.addTextChangedListener(this);

        confirm_btn = findViewById(R.id.froget_change_confirm_btn);
        confirm_btn.setOnClickListener(this);
        confirm_btn.setEnabled(false);
    }

    public void initTitle() {
        ImageView back = findViewById(R.id.common_back);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        textView.setText("密码重置");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.common_back) {
            finish();
        } else if (v.getId() == R.id.froget_change_confirm_btn) {

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!first_edit.getText().toString().isEmpty()) {
            if (!secend_edit.getText().toString().isEmpty()) {
                confirm_btn.setBackgroundResource(R.drawable.drawable_evaluation_btn);
                confirm_btn.setEnabled(true);
            }else{
                confirm_btn.setBackgroundResource(R.drawable.drawable_evaluation_btn_gray);
                confirm_btn.setEnabled(false);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

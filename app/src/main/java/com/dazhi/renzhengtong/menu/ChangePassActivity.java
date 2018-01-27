package com.dazhi.renzhengtong.menu;

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

public class ChangePassActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText old_edit;
    private EditText new_edit;
    private EditText confirm_edit;
    private Button confirm_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_frogetpass);
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
        }
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

package com.dazhi.renzhengtong.loading;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dazhi.renzhengtong.MainActivity;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.TableBarActivity;

/**
 * Created by mac on 2018/1/22.
 */

public class LoadingActivity extends AppCompatActivity implements MyProgressView.OnProgressChangedListener {

    private MyProgressView progressBar;
    private TextView textView;
    private int duration = 000;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loading);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView);
        textView.setText(duration+"s");
        progressBar.setListener(this);
        progressBar.setDuration(duration);
        progressBar.start();

    }


    @Override
    public void onProgressChanged(int progress) {
        if (progress == 0){
            textView.setText("0s");
            Intent intent = new Intent(this,TableBarActivity.class);
            startActivity(intent);
            finish();
        }else{
            textView.setText((progress)*duration/1000/100+1+"s");
        }
    }
}

package com.dazhi.renzhengtong.utils;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ProgressBar;

/**
 * Created by Admin on 2018/1/29 0029.
 */

public class MyLoadingDialog extends Dialog {

    public MyLoadingDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public void init(Context context){
        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        addContentView(progressBar,new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
    }
}

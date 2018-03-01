package com.dazhi.renzhengtong;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by mac on 2018/3/1.
 */

public class MyProgressDialog extends Dialog{

    public MyProgressDialog(@NonNull Context context) {
        super(context);
    }

    public MyProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my_progress);
        setCancelable(false);
    }
}

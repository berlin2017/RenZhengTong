package com.dazhi.renzhengtong.news;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dazhi.renzhengtong.R;

/**
 * Created by mac on 2018/1/26.
 */

public class NewsDetailActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView back;
    private ImageView share;
    private ImageView collect;
    private TextView title_tv;
    private WebView webView;
    private RelativeLayout bottom_layout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail);

        webView = findViewById(R.id.detail_webview);
        webView.loadUrl("https://www.baidu.com/");
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new MyChromeClient());
        webView.setWebViewClient(new MyWebClient());

        bottom_layout = findViewById(R.id.detail_bottom_layout);
        bottom_layout.setOnClickListener(this);

        progressBar = findViewById(R.id.detail_loading);

        initTitle();
    }


    public void initTitle(){
        back = findViewById(R.id.detail_title_back);
        share = findViewById(R.id.detail_title_share);
        collect = findViewById(R.id.detail_title_collect);
        title_tv = findViewById(R.id.detail_title_title);
        back.setOnClickListener(this);
        share.setOnClickListener(this);
        collect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_bottom_layout:

                break;
            case R.id.detail_title_back:
                finish();
                break;

            case R.id.detail_title_share:

                break;
            case R.id.detail_title_collect:

                break;
        }
    }

    public void collect(){

    }

    class MyChromeClient extends WebChromeClient{
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            title_tv.setText(title);
        }
    }

    class MyWebClient extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }


}

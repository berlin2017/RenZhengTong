package com.dazhi.renzhengtong.news;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.news.model.NewsModel;
import com.dazhi.renzhengtong.utils.Constant;
import com.dazhi.renzhengtong.utils.NetRequest;
import com.dazhi.renzhengtong.utils.ToastHelper;
import com.dazhi.renzhengtong.utils.Utils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

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
    private NewsModel model;
    private int id = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail);
        id = getIntent().getIntExtra("id", 0);
        webView = findViewById(R.id.detail_webview);
//        webView.loadUrl("https://www.baidu.com/");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
//		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webView.setWebChromeClient(new MyChromeClient());
        webView.setWebViewClient(new MyWebClient());

        bottom_layout = findViewById(R.id.detail_bottom_layout);
        bottom_layout.setOnClickListener(this);

        progressBar = findViewById(R.id.detail_loading);

        initTitle();
        requestDetail();
    }


    public void initTitle() {
        back = findViewById(R.id.detail_title_back);
        share = findViewById(R.id.detail_title_share);
        collect = findViewById(R.id.detail_title_collect);
        title_tv = findViewById(R.id.detail_title_title);
        back.setOnClickListener(this);
        share.setOnClickListener(this);
        collect.setOnClickListener(this);

    }

    public void requestDetail() {

        NetRequest.getFormRequest(Constant.NEW_DETAIL_URL+"/articles/"+id, null, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject jsonObject = new JSONObject(result);
                model = Utils.decodeJSON(jsonObject.optString("data"), NewsModel.class);
                webView.loadDataWithBaseURL(null, model.getPost_content(), "text/html", "utf-8", null);
                title_tv.setText(model.getPost_title());
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastHelper.showToast("请求失败,请重试");
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    public void collect() {

    }

    class MyChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

        }
    }

    class MyWebClient extends WebViewClient {

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

package com.dazhi.renzhengtong.news;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.loading.SystemInfoManager;
import com.dazhi.renzhengtong.news.adapter.NewsFileAdapter;
import com.dazhi.renzhengtong.news.model.NewsFileModel;
import com.dazhi.renzhengtong.news.model.NewsModel;
import com.dazhi.renzhengtong.search.SearchListActivity;
import com.dazhi.renzhengtong.user.LoginActivity;
import com.dazhi.renzhengtong.user.UserManager;
import com.dazhi.renzhengtong.utils.Constant;
import com.dazhi.renzhengtong.utils.NetRequest;
import com.dazhi.renzhengtong.utils.ToastHelper;
import com.dazhi.renzhengtong.utils.Utils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
    private String url;
    private RecyclerView recyclerView;
    private NewsFileAdapter adapter;
    private List<NewsFileModel> list = new ArrayList<>();
    private TextView fileTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail);
        id = getIntent().getIntExtra("id", 0);
        url = getIntent().getStringExtra("url");
        recyclerView = findViewById(R.id.detail_files_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsFileAdapter(R.layout.item_detail_file, list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                if (UserManager.getUser(NewsDetailActivity.this) != null) {
                    AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(NewsDetailActivity.this);
                    builder.setTitle("确认下载").setMessage(list.get(position).getName()).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(list.get(position).getUrl()));
                            //指定下载路径和下载文件名
                            request.setDestinationInExternalPublicDir("/download/", list.get(position).getName());
                            //获取下载管理器
                            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                            //将下载任务加入下载队列，否则不会进行下载
                            downloadManager.enqueue(request);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                } else {
                    AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(NewsDetailActivity.this);
                    builder.setTitle("提示").setMessage("请登录后下载").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(NewsDetailActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                }
            }
        });
        View header = LayoutInflater.from(this).inflate(R.layout.layout_detail_header, null);
        adapter.addHeaderView(header);
        fileTitle = header.findViewById(R.id.detail_files_name);
        webView = header.findViewById(R.id.detail_webview);
//        webView.loadUrl("https://www.baidu.com/");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
//		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webView.setWebChromeClient(new MyChromeClient());
        webView.setWebViewClient(new MyWebClient());

        bottom_layout = findViewById(R.id.detail_bottom_layout);
        bottom_layout.setOnClickListener(this);

        progressBar = findViewById(R.id.detail_loading);

        initTitle();
        if (TextUtils.isEmpty(url)) {
            requestDetail();
        } else {
            webView.loadUrl(url);
        }


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

        NetRequest.getFormRequest(Constant.NEW_DETAIL_URL + "/articles/" + id, null, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject jsonObject = new JSONObject(result);
                model = Utils.decodeJSON(jsonObject.optString("data"), NewsModel.class);
                webView.loadDataWithBaseURL(null, model.getPost_content(), "text/html", "utf-8", null);
                title_tv.setText(model.getPost_title());
                list.clear();
                list.addAll(model.getMore().getFiles());
                if (list.size()<=0){
                    fileTitle.setVisibility(View.GONE);
                }else{
                    fileTitle.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("拨打电话").setMessage(SystemInfoManager.getInfo(getApplicationContext()).getSite_tel()).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ContextCompat.checkSelfPermission(NewsDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ||
                                ContextCompat.checkSelfPermission(NewsDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(NewsDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + SystemInfoManager.getInfo(getApplicationContext()).getSite_tel()));
                            startActivity(intent);
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
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

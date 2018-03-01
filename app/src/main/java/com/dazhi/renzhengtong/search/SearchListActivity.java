package com.dazhi.renzhengtong.search;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dazhi.renzhengtong.MyProgressDialog;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.news.DashlineItemDivider;
import com.dazhi.renzhengtong.news.NewsDetailActivity;
import com.dazhi.renzhengtong.news.model.NewsModel;
import com.dazhi.renzhengtong.news.model.SlideModel;
import com.dazhi.renzhengtong.search.adapter.SearchListAdapter;
import com.dazhi.renzhengtong.user.LoginActivity;
import com.dazhi.renzhengtong.user.UserManager;
import com.dazhi.renzhengtong.utils.Constant;
import com.dazhi.renzhengtong.utils.NetRequest;
import com.dazhi.renzhengtong.utils.ToastHelper;
import com.dazhi.renzhengtong.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by mac on 2018/2/2.
 */

public class SearchListActivity extends AppCompatActivity implements View.OnClickListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SearchListAdapter adapter;
    private List<NewsModel> list = new ArrayList<>();
    private int id = 0;
    private MyProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new MyProgressDialog(this,R.style.Dialog);
        id = getIntent().getIntExtra("id", 0);
        setContentView(R.layout.layout_search_list);
        swipeRefreshLayout = findViewById(R.id.search_list_swip);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                requestList();
            }
        });
        recyclerView = findViewById(R.id.search_list_recyclerview);
        progressBar = findViewById(R.id.search_list_progress);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchListAdapter(R.layout.item_search_layout, list);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestList();
            }
        }, recyclerView);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DashlineItemDivider());
        adapter.disableLoadMoreIfNotFullPage(recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                if (id == 17 && list.get(position).getMore().getFiles().size() > 0) {
                    if (UserManager.getUser(SearchListActivity.this) != null) {
                        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(SearchListActivity.this);
                        builder.setTitle("确认下载").setMessage(list.get(position).getMore().getFiles().get(0).getName()).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(list.get(position).getMore().getFiles().get(0).getUrl()));
                                //指定下载路径和下载文件名
                                request.setDestinationInExternalPublicDir("/download/", list.get(position).getMore().getFiles().get(0).getName());
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
                        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(SearchListActivity.this);
                        builder.setTitle("提示").setMessage("请登录后下载").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(SearchListActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
                    }

                } else {
                    Intent intent = new Intent(SearchListActivity.this, NewsDetailActivity.class);
                    intent.putExtra("id", list.get(position).getNews_id());
                    intent.putExtra("url", list.get(position).getPost_source());
                    startActivity(intent);
                }
            }
        });
        autoRefresh();
        requestList();
        initTitle();
    }


    public void initTitle() {
        ImageView back = findViewById(R.id.common_back);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        textView.setText(getIntent().getStringExtra("name"));
    }

    public void requestList() {
//        progressBar.setVisibility(View.VISIBLE);
        progressDialog.show();
        NetRequest.getFormRequest(Constant.NEW_LIST_URL + "/category_id/" + id, null, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {

                JSONObject jsonObject = new JSONObject(result);
                List<NewsModel> array = Utils.decodeJSONARRAY(jsonObject.optJSONObject("data").optString("list"), NewsModel.class);
                list.addAll(array);
                adapter.notifyDataSetChanged();
                adapter.loadMoreComplete();
                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
                stopRefresh();
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastHelper.showToast("请求失败,请重试");
                adapter.loadMoreComplete();
                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
                stopRefresh();
            }
        });
    }

    public void autoRefresh() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
//                requestList();
            }
        });
    }

    private void stopRefresh() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                finish();
                break;
        }
    }
}

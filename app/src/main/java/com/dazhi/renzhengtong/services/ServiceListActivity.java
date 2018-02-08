package com.dazhi.renzhengtong.services;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.news.NewsDetailActivity;
import com.dazhi.renzhengtong.news.model.NewsModel;
import com.dazhi.renzhengtong.search.SearchListActivity;
import com.dazhi.renzhengtong.services.adapter.ServiceListAdapter;
import com.dazhi.renzhengtong.services.model.TiXiModel;
import com.dazhi.renzhengtong.utils.Constant;
import com.dazhi.renzhengtong.utils.NetRequest;
import com.dazhi.renzhengtong.utils.ToastHelper;
import com.dazhi.renzhengtong.utils.Utils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by mac on 2018/2/1.
 */

public class ServiceListActivity extends AppCompatActivity implements View.OnClickListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ServiceListAdapter adapter;
    private List<NewsModel> list = new ArrayList<>();
    private int id = 0;
    private int page = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("id", 0);
        setContentView(R.layout.layout_services_list);
        initTitle();
        recyclerView = findViewById(R.id.services_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ServiceListAdapter(R.layout.item_service_list_layout, list);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestList();
            }
        },recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.disableLoadMoreIfNotFullPage(recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ServiceListActivity.this, NewsDetailActivity.class);
                intent.putExtra("id", list.get(position).getId());
                intent.putExtra("url", list.get(position).getPost_source());
                startActivity(intent);
            }
        });
        swipeRefreshLayout = findViewById(R.id.services_list_swip);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                requestList();
            }
        });
        autoRefresh();
        requestList();
    }

    public void requestList() {
        NetRequest.getFormRequest(Constant.NEW_LIST_URL + "/category_id/" + id, null, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {

                JSONObject jsonObject = new JSONObject(result);
                List<NewsModel> array = Utils.decodeJSONARRAY(jsonObject.optJSONObject("data").optString("list"), NewsModel.class);
                list.addAll(array);
                adapter.notifyDataSetChanged();
                adapter.loadMoreComplete();
                stopRefresh();
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastHelper.showToast("请求失败,请重试");
                adapter.loadMoreComplete();
                stopRefresh();
            }
        });
    }

    public void initTitle() {
        ImageView back = findViewById(R.id.common_back);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        textView.setText(getIntent().getStringExtra("name"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                finish();
                break;
        }
    }

    public void autoRefresh() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
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
}
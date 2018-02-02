package com.dazhi.renzhengtong.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.news.NewsDetailActivity;
import com.dazhi.renzhengtong.news.model.NewsModel;
import com.dazhi.renzhengtong.search.adapter.SearchListAdapter;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("id", 0);
        setContentView(R.layout.layout_search_list);
        swipeRefreshLayout = findViewById(R.id.search_list_swip);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestList();
            }
        });
        recyclerView = findViewById(R.id.search_list_recyclerview);
        progressBar = findViewById(R.id.search_list_progress);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchListAdapter(R.layout.item_service_list_layout, list);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestList();
            }
        }, recyclerView);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SearchListActivity.this, NewsDetailActivity.class);
                intent.putExtra("id", list.get(position).getId());
                startActivity(intent);
            }
        });
        autoRefresh();
        initTitle();
    }


    public void initTitle() {
        ImageView back = findViewById(R.id.common_back);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        textView.setText(getIntent().getStringExtra("name"));
    }

    public void requestList() {
        progressBar.setVisibility(View.VISIBLE);
        for (int i = 0; i < 10; i++) {
            NewsModel model = new NewsModel();
            model.setPost_title("外部链接" + i);
            model.setId(10);
            list.add(model);
            adapter.notifyDataSetChanged();
            adapter.loadMoreComplete();
            progressBar.setVisibility(View.GONE);
            stopRefresh();
        }
    }

    public void autoRefresh() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                requestList();
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

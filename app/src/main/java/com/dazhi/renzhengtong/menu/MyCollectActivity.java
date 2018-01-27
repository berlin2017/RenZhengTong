package com.dazhi.renzhengtong.menu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.news.DashlineItemDivider;
import com.dazhi.renzhengtong.news.NewsDetailActivity;
import com.dazhi.renzhengtong.news.adapter.BaseViewHolder;
import com.dazhi.renzhengtong.news.adapter.HeaderFooterAdapter;
import com.dazhi.renzhengtong.news.model.NewsModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/1/27.
 */

public class MyCollectActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private HeaderFooterAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page = 0;
    private List<NewsModel> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_collect_home);
        initTitle();
        swipeRefreshLayout = findViewById(R.id.collect_home_swip);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        recyclerView = findViewById(R.id.collect_home_recycelerview);
        adapter = new HeaderFooterAdapter(this,list) {
            @Override
            public BaseViewHolder creatViewHolder(ViewGroup parent, int viewType) {
                return new BaseViewHolder(LayoutInflater.from(MyCollectActivity.this).inflate(R.layout.item_news_list,parent,false));
            }

            @Override
            public void convert(BaseViewHolder holder, int position) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MyCollectActivity.this,NewsDetailActivity.class);
                        startActivity(intent);
                    }
                });
                holder.setText(R.id.item_news_name,list.get(position).getName());
                holder.setText(R.id.item_news_time,list.get(position).getTime());
                holder.setText(R.id.item_news_content,list.get(position).getContent());
                SimpleDraweeView simpleDraweeView = holder.getView(R.id.item_news_imageview);
                simpleDraweeView.setImageURI(Uri.parse(list.get(position).getImage()));
            }


        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DashlineItemDivider());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    if (lastItemPosition>=list.size()-1){
                        page++;
                        requestList();
                    }
                }
            }
        });
        autoRefresh();
//        requestList();
    }

    public void requestList(){
        NewsModel model = new NewsModel();
        model.setName("合肥蜀山区科技园");
        model.setContent("关于组织申报2017年促进服务业发展政策（电子商务及服务外包）时候讲不项目的通知");
        model.setTime("2018-01-25");
        model.setImage("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2054592640,401359956&fm=58");
        for (int i=0;i<20;i++){
            list.add(model);
        }
        adapter.notifyDataSetChanged();
        stopRefresh();
    }

    public void initTitle(){
        ImageView back = findViewById(R.id.common_back);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        textView.setText("我的关注");
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.common_back){
            finish();
        }
    }

    @Override
    public void onRefresh() {
        page = 0;
        list.clear();
        requestList();
    }

    public void autoRefresh() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
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

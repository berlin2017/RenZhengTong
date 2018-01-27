package com.dazhi.renzhengtong.news;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.news.adapter.BaseViewHolder;
import com.dazhi.renzhengtong.news.adapter.HeaderFooterAdapter;
import com.dazhi.renzhengtong.news.adapter.NewsImageAdapter;
import com.dazhi.renzhengtong.news.adapter.NewsListAdapter;
import com.dazhi.renzhengtong.news.model.ImageModel;
import com.dazhi.renzhengtong.news.model.NewsModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/1/25.
 */

public class NewsChannelFragment extends Fragment implements ViewPager.OnPageChangeListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private HeaderFooterAdapter adapter;
    private List<NewsModel>list = new ArrayList<>();
    private ViewPager viewPager;
    private List<ImageModel>images = new ArrayList<>();
    private LinearLayout indicator_layout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_channel_home,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = view.findViewById(R.id.news_channel_swip);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        recyclerView = view.findViewById(R.id.news_channel_recyclerview);

//        adapter = new NewsListAdapter(list,getContext());
        adapter = new HeaderFooterAdapter(getContext(),list) {
            @Override
            public BaseViewHolder creatViewHolder(ViewGroup parent, int viewType) {
                return new BaseViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_news_list,parent,false));
            }

            @Override
            public void convert(BaseViewHolder holder, int position) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(),NewsDetailActivity.class);
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

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

        View header = LayoutInflater.from(getContext()).inflate(R.layout.layout_news_header,null);

        adapter.addHeaderView(header);

        viewPager = header.findViewById(R.id.news_channel_viewpager);
        ImageModel iamge = new ImageModel();
        iamge.setImage("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2054592640,401359956&fm=58");
        images.add(iamge);
        images.add(iamge);
        viewPager.setAdapter(new NewsImageAdapter(images,getContext()));
        viewPager.addOnPageChangeListener(this);

        indicator_layout = header.findViewById(R.id.news_channel_indcator);
        resetIndicator(0);

        autoRefresh();
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

    public static NewsChannelFragment newInstance(String s) {

        Bundle args = new Bundle();
        args.putString("title",s);
        NewsChannelFragment fragment = new NewsChannelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void resetIndicator(int position){
        indicator_layout.removeAllViews();
        for (int i = 0; i < images.size(); i++) {
            Button bt = new Button(getActivity());
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(20,20);
            p.setMargins(10,10,10,10);
            bt.setLayoutParams(p);
            if (i==position){
                bt.setBackgroundResource(R.drawable.ic_indicator_light);
            }else{
                bt.setBackgroundResource(R.drawable.ic_indicator_normal);
            }
            indicator_layout.addView(bt);
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        resetIndicator(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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

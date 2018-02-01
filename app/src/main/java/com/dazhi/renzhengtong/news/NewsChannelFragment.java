package com.dazhi.renzhengtong.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.news.adapter.NewsAdapter;
import com.dazhi.renzhengtong.news.adapter.NewsImageAdapter;
import com.dazhi.renzhengtong.news.model.SlideModel;
import com.dazhi.renzhengtong.news.model.NewsModel;
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
 * Created by mac on 2018/1/25.
 */

public class NewsChannelFragment extends Fragment implements ViewPager.OnPageChangeListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<NewsModel> list = new ArrayList<>();
    private ViewPager viewPager;
    private List<SlideModel> images = new ArrayList<>();
    private LinearLayout indicator_layout;
    private int page = 0;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int id = 0;
    private NewsImageAdapter imageAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_channel_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id = getArguments().getInt("id");

        swipeRefreshLayout = view.findViewById(R.id.news_channel_swip);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = view.findViewById(R.id.news_channel_recyclerview);
        if (id == 12) {
            adapter = new NewsAdapter(R.layout.item_huibian_layout, list);
        } else {
            adapter = new NewsAdapter(R.layout.item_news_list, list);
        }
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                NewsModel model = list.get(position);
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                intent.putExtra("id", model.getId());
                startActivity(intent);
            }
        });

        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestList();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DashlineItemDivider());

        if (id == 8) {
            View header = LayoutInflater.from(getContext()).inflate(R.layout.layout_news_header, null);
            adapter.addHeaderView(header);
            viewPager = header.findViewById(R.id.news_channel_viewpager);
            imageAdapter = new NewsImageAdapter(images, getContext());
            viewPager.setAdapter(imageAdapter);
            viewPager.addOnPageChangeListener(NewsChannelFragment.this);
            indicator_layout = header.findViewById(R.id.news_channel_indcator);
        }

        autoRefresh();
        requestList();
    }

    private void requestList() {

        NetRequest.getFormRequest(Constant.NEW_LIST_URL+"/category_id/"+id, null, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {

                JSONObject jsonObject = new JSONObject(result);
                List<NewsModel> array = Utils.decodeJSONARRAY(jsonObject.optJSONObject("data").optString("list"), NewsModel.class);
                JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONArray("slides");
                if (jsonArray.length()>0){
                    JSONObject object = jsonArray.getJSONObject(0);
                    List<SlideModel> slideArray = Utils.decodeJSONARRAY(object.optString("items"),SlideModel.class);
                    images.clear();
                    images.addAll(slideArray);
                    resetIndicator(0);
                    imageAdapter.notifyDataSetChanged();
                }

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


    public static NewsChannelFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt("id", id);
        NewsChannelFragment fragment = new NewsChannelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void resetIndicator(int position) {
        if (indicator_layout!=null){
            indicator_layout.removeAllViews();
            for (int i = 0; i < images.size(); i++) {
                Button bt = new Button(getActivity());
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(20, 20);
                p.setMargins(10, 10, 10, 10);
                bt.setLayoutParams(p);
                if (i == position) {
                    bt.setBackgroundResource(R.drawable.ic_indicator_light);
                } else {
                    bt.setBackgroundResource(R.drawable.ic_indicator_normal);
                }
                indicator_layout.addView(bt);
            }
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

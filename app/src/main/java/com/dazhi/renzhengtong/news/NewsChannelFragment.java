package com.dazhi.renzhengtong.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.news.adapter.NewsImageAdapter;
import com.dazhi.renzhengtong.news.adapter.NewsListAdapter;
import com.dazhi.renzhengtong.news.model.ImageModel;
import com.dazhi.renzhengtong.news.model.NewsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/1/25.
 */

public class NewsChannelFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsListAdapter adapter;
    private List<NewsModel>list = new ArrayList<>();
    private ViewPager viewPager;
    private List<ImageModel>images = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_channel_home,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.news_channel_recyclerview);
        NewsModel model = new NewsModel();
        model.setName("合肥蜀山区科技园");
        model.setContent("关于组织申报2017年促进服务业发展政策（电子商务及服务外包）时候讲不项目的通知");
        model.setTime("2018-01-25");
        model.setImage("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2054592640,401359956&fm=58");
        for (int i=0;i<20;i++){
            list.add(model);
        }
        adapter = new NewsListAdapter(list,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DashlineItemDivider());

        viewPager = view.findViewById(R.id.news_channel_viewpager);
        ImageModel iamge = new ImageModel();
        iamge.setImage("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2054592640,401359956&fm=58");
        images.add(iamge);
        images.add(iamge);
        viewPager.setAdapter(new NewsImageAdapter(images,getContext()));
    }

    public static NewsChannelFragment newInstance(String s) {
        
        Bundle args = new Bundle();
        args.putString("title",s);
        NewsChannelFragment fragment = new NewsChannelFragment();
        fragment.setArguments(args);
        return fragment;
    }

}

package com.dazhi.renzhengtong.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.news.adapter.NewsFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/1/25.
 */

public class NewsHomeFragment extends Fragment{

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private NewsFragmentAdapter adapter;
    private List<String>list;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.news_home_viewpager);
        tabLayout = view.findViewById(R.id.news_home_tablayout);

        list = new ArrayList<>();
        list.add("新闻");
        list.add("公告");
        list.add("认证");
        list.add("标准");
        adapter = new NewsFragmentAdapter(getChildFragmentManager(),list,getContext());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_news_home,container,false);
    }

}

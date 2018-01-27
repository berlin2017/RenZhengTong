package com.dazhi.renzhengtong.news.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.dazhi.renzhengtong.news.NewsChannelFragment;

import java.util.List;

/**
 * Created by mac on 2018/1/25.
 */

public class NewsFragmentAdapter extends FragmentStatePagerAdapter {

    private List<String>list;
    private Context context;

    public NewsFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public NewsFragmentAdapter(FragmentManager fm, List<String> list, Context context) {
        super(fm);
        this.list = list;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        NewsChannelFragment fragment = NewsChannelFragment.newInstance(list.get(position));
        return fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
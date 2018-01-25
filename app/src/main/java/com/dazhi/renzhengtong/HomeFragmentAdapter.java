package com.dazhi.renzhengtong;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by mac on 2018/1/25.
 */

public class HomeFragmentAdapter extends FragmentStatePagerAdapter{

    private List<Fragment> list;
    private Context context;


    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public HomeFragmentAdapter(FragmentManager fm, List<Fragment> list, Context context) {
        super(fm);
        this.list = list;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}

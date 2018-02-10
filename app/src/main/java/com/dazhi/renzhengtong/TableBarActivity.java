package com.dazhi.renzhengtong;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dazhi.renzhengtong.evaluation.EvaluationHomeFragment;
import com.dazhi.renzhengtong.news.NewsHomeFragment;
import com.dazhi.renzhengtong.search.SearchHomeFragment;
import com.dazhi.renzhengtong.services.ServicesHomeFragment;

import java.util.ArrayList;
import java.util.List;

public class TableBarActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private BottomNavigationView mBottomNavigationView;
    private ViewPager mViewPager;
    private HomeFragmentAdapter mAdapter;
    private List<Fragment> list = new ArrayList<>();
    private int current = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_news:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_search:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_evaluating:
                    mViewPager.setCurrentItem(2);
                    return true;

                case R.id.navigation_services:
                    mViewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_bar);

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewPager = findViewById(R.id.viewpage);
        NewsHomeFragment fragment1 = new NewsHomeFragment();
        list.add(fragment1);
        SearchHomeFragment fragment2 = new SearchHomeFragment();
        list.add(fragment2);
        EvaluationHomeFragment fragment3 = new EvaluationHomeFragment();
        list.add(fragment3);
        ServicesHomeFragment fragment4 = new ServicesHomeFragment();
        list.add(fragment4);
        mAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), list, this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
        current = getIntent().getIntExtra("current",0);
        changeindex(current);

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(receiver,new IntentFilter("updateTab"));
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            changeindex(2);
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int id = 0;
        switch (position) {
            case 0:

                id = R.id.navigation_news;
                break;
            case 1:

                id = R.id.navigation_search;
                break;
            case 2:

                id = R.id.navigation_evaluating;
                break;
            case 3:

                id = R.id.navigation_services;
                break;
        }
        mBottomNavigationView.setSelectedItemId(id);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        SharedPreferences sharedPreferences = getSharedPreferences("renzhengtong", MODE_PRIVATE);
        long old = sharedPreferences.getLong("key_time", 0l);
        long now = System.currentTimeMillis();
        if (now - old <= 3000) {
            finish();
        } else {
            SharedPreferences.Editor editor =  sharedPreferences.edit();
            editor.putLong("key_time", now);
            editor.commit();
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
        }

    }

    public void changeindex(int index) {
        mViewPager.setCurrentItem(index);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(receiver);
    }
}

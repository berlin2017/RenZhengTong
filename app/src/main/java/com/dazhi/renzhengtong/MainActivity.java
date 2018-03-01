package com.dazhi.renzhengtong;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.dazhi.renzhengtong.evaluation.EvaluationHomeFragment;
import com.dazhi.renzhengtong.news.NewsHomeFragment;
import com.dazhi.renzhengtong.search.SearchHomeFragment;
import com.dazhi.renzhengtong.services.ServicesHomeFragment;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    private BottomNavigationBar bottomNavigationBar;
    private Map<Integer, Fragment> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationBar = findViewById(R.id.main_tab);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.drawable_tab_news, "新闻").setActiveColorResource(android.R.color.holo_blue_light))
                .addItem(new BottomNavigationItem(R.drawable.drawable_tab_search, "查询").setActiveColorResource(android.R.color.holo_green_light))
                .addItem(new BottomNavigationItem(R.drawable.drawable_tab_evaluation, "测评").setActiveColorResource(android.R.color.holo_orange_light))
                .addItem(new BottomNavigationItem(R.drawable.drawable_tab_services, "服务").setActiveColorResource(android.R.color.holo_purple));
        bottomNavigationBar.setFirstSelectedPosition(0);
        bottomNavigationBar.initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        changeFragment(0);
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
    public void onTabSelected(int position) {
        changeFragment(position);
    }

    @Override
    public void onTabUnselected(int position) {
        hideFragment(position);
    }

    @Override
    public void onTabReselected(int position) {

    }

    public void changeFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = map.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new NewsHomeFragment();
                    break;
                case 1:
                    fragment = new SearchHomeFragment();
                    break;
                case 2:
                    fragment = new EvaluationHomeFragment();
                    break;
                case 3:
                    fragment = new ServicesHomeFragment();
                    break;
                default:
                    fragment = new NewsHomeFragment();
            }
            map.put(position, fragment);
        }
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.main_framelayout, fragment);
        }
        transaction.commitAllowingStateLoss();
    }

    public void hideFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = map.get(position);
        if (fragment != null) {
            transaction.hide(fragment);
        }
        transaction.commitAllowingStateLoss();
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
        bottomNavigationBar.selectTab(index);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(receiver);
    }
}

package com.dazhi.renzhengtong.news;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.loading.MyProgressView;
import com.dazhi.renzhengtong.menu.MyCollectActivity;
import com.dazhi.renzhengtong.menu.SettingsActivity;
import com.dazhi.renzhengtong.news.adapter.NewsFragmentAdapter;
import com.dazhi.renzhengtong.news.model.NewsModel;
import com.dazhi.renzhengtong.user.LoginActivity;
import com.dazhi.renzhengtong.user.UserInfo;
import com.dazhi.renzhengtong.user.UserInfoActivity;
import com.dazhi.renzhengtong.user.UserManager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mac on 2018/1/25.
 */

public class NewsHomeFragment extends Fragment implements View.OnClickListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private NewsFragmentAdapter adapter;
    private List<String> list;
    private SlidingMenu menu;
    private SimpleDraweeView simpleDraweeView;
    private SimpleDraweeView menu_image;

    private LinearLayout collect_layout;
    private LinearLayout sbjj_layout;
    private LinearLayout kefu_layout;
    private LinearLayout invite_layout;
    private LinearLayout settings_layout;
    private TextView location_tv;
    private TextView nickName_tv;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.news_home_viewpager);
        tabLayout = view.findViewById(R.id.news_home_tablayout);

        list = new ArrayList<>();
        list.add("新闻");
        list.add("公告");
        list.add("认证");
//        list.add("培训");
        list.add("标准");
        adapter = new NewsFragmentAdapter(getChildFragmentManager(), list, getContext());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        simpleDraweeView = view.findViewById(R.id.news_home_title_photo);
        simpleDraweeView.setOnClickListener(this);

        initMenu();
//        location_tv = view.findViewById(R.id.news_home_title_location);
//        location_tv.setOnClickListener(this);


        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(receiver, new IntentFilter("login_success"));

    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUser();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.unregisterReceiver(receiver);
    }

    public void updateUser() {
        UserInfo userInfo = UserManager.getUser(getContext());
        if (userInfo != null) {
            menu_image.setImageURI(Uri.parse(userInfo.getPhoto()));
            nickName_tv.setText(userInfo.getNickname());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_news_home, container, false);
    }

    public void initMenu() {
        menu = new SlidingMenu(getActivity());
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(getActivity(), SlidingMenu.SLIDING_CONTENT);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_menu, null);
        menu.setMenu(view);

        collect_layout = view.findViewById(R.id.home_menu_collect);
        collect_layout.setOnClickListener(this);

        sbjj_layout = view.findViewById(R.id.home_menu_sbjj);
        sbjj_layout.setOnClickListener(this);

        kefu_layout = view.findViewById(R.id.home_menu_kefu);
        kefu_layout.setOnClickListener(this);

        invite_layout = view.findViewById(R.id.home_menu_invite);
        invite_layout.setOnClickListener(this);

        settings_layout = view.findViewById(R.id.home_menu_settings);
        settings_layout.setOnClickListener(this);

        menu_image = view.findViewById(R.id.home_menu_photo);
        menu_image.setOnClickListener(this);
        nickName_tv = view.findViewById(R.id.home_menu_name);

        updateUser();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.news_home_title_photo:
                if (menu.isMenuShowing()) {
                    menu.showContent();
                } else {
                    menu.showMenu();
                }
                break;
            case R.id.home_menu_collect:
                menu.showContent();
                intent = new Intent(getContext(), MyCollectActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.home_menu_sbjj:

                break;
            case R.id.home_menu_kefu:

                break;
            case R.id.home_menu_invite:

                break;
            case R.id.home_menu_settings:
                menu.showContent();
                intent = new Intent(getContext(), SettingsActivity.class);
                getActivity().startActivity(intent);
                break;

            case R.id.home_menu_photo:
                UserInfo info = UserManager.getUser(getContext());
                if (info != null) {
                    intent = new Intent(getContext(), UserInfoActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;

//            case R.id.news_home_title_location:
//                intent = new Intent(getContext(), LocationActivity.class);
//                startActivity(intent);
//                break;
            default:


        }

    }
}

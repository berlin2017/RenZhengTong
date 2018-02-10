package com.dazhi.renzhengtong.news;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.loading.MyProgressView;
import com.dazhi.renzhengtong.menu.ContactActivity;
import com.dazhi.renzhengtong.menu.MenuJGActivity;
import com.dazhi.renzhengtong.menu.MyCollectActivity;
import com.dazhi.renzhengtong.menu.SettingsActivity;
import com.dazhi.renzhengtong.news.adapter.NewsFragmentAdapter;
import com.dazhi.renzhengtong.news.model.NewsModel;
import com.dazhi.renzhengtong.search.SearchListActivity;
import com.dazhi.renzhengtong.user.LoginActivity;
import com.dazhi.renzhengtong.user.UserInfo;
import com.dazhi.renzhengtong.user.UserInfoActivity;
import com.dazhi.renzhengtong.user.UserManager;
import com.dazhi.renzhengtong.utils.Constant;
import com.dazhi.renzhengtong.utils.ToastHelper;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
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
    private LinearLayout settings_layout;
    private LinearLayout hetong_layout;
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
//        localBroadcastManager.registerReceiver(outreceiver, new IntentFilter("login_out"));
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUser();
        }
    };

//    private BroadcastReceiver outreceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            updateUser();
//        }
//    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.unregisterReceiver(receiver);
//        localBroadcastManager.unregisterReceiver(outreceiver);
    }

    public void updateUser() {
        UserInfo userInfo = UserManager.getUser(getContext());
        if (userInfo != null) {
            menu_image.setImageURI(Uri.parse(Constant.BASE_URL+userInfo.getLogo()));
            simpleDraweeView.setImageURI(Uri.parse(Constant.BASE_URL+userInfo.getLogo()));
            if (TextUtils.isEmpty(userInfo.getUser_nickname())){
                nickName_tv.setText(userInfo.getMobile());
            }else{
                nickName_tv.setText(userInfo.getUser_nickname());
            }
        }else{
            menu_image.setImageResource(R.drawable.ic_default_user);
            simpleDraweeView.setImageResource(R.drawable.ic_default_user);
            nickName_tv.setText("您还未登录~");
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


        settings_layout = view.findViewById(R.id.home_menu_settings);
        settings_layout.setOnClickListener(this);

        menu_image = view.findViewById(R.id.home_menu_photo);
        menu_image.setOnClickListener(this);
        nickName_tv = view.findViewById(R.id.home_menu_name);

        hetong_layout = view.findViewById(R.id.home_menu_download);
        hetong_layout.setOnClickListener(this);

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
                if (UserManager.getUser(getContext())==null){
                    ToastHelper.showToast("请先登录");
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                intent = new Intent(getContext(), UserInfoActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.home_menu_sbjj:
                menu.showContent();
                if (UserManager.getUser(getContext())==null){
                    ToastHelper.showToast("请先登录");
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                intent = new Intent(getContext(), MenuJGActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.home_menu_kefu:
                menu.showContent();
                intent = new Intent(getContext(), ContactActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.home_menu_settings:
                menu.showContent();
                if (UserManager.getUser(getContext())==null){
                    ToastHelper.showToast("请先登录");
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
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
            case R.id.home_menu_download:
                if (UserManager.getUser(getContext()) != null) {
                    AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                    builder.setTitle("确认下载").setMessage("合同下载").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse("https://wkbos.bdimg.com/v1/wenku44//524b391375c7f19db4d9833dfb130ed4?responseContentDisposition=attachment%3B%20filename%3D%22DA%25E8%25BD%25AC%25E6%258D%25A2%25E7%2594%25B5%25E8%25B7%25AF.docx%22%3B%20filename%2A%3Dutf-8%27%27DA%25E8%25BD%25AC%25E6%258D%25A2%25E7%2594%25B5%25E8%25B7%25AF.docx&responseContentType=application%2Foctet-stream&responseCacheControl=no-cache&authorization=bce-auth-v1%2Ffa1126e91489401fa7cc85045ce7179e%2F2018-02-10T07%3A30%3A59Z%2F3600%2Fhost%2F991c7f74dc60f98a05fd4aa0ae2df1fa2d8c1ca73c55d9d30e5b54be0964bb4b&token=78ae6e82dc6b7b467d7c7a05c91d835905b0bd59d2ce098a3b311003d794ea10&expire=2018-02-10T08:30:59Z"));
                            //指定下载路径和下载文件名
                            request.setDestinationInExternalPublicDir("/download/", "renzhengtong.doc");
                            //获取下载管理器
                            DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                            //将下载任务加入下载队列，否则不会进行下载
                            downloadManager.enqueue(request);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();

                } else {
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            default:


        }

    }
}

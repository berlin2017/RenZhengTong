package com.dazhi.renzhengtong.services;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.loading.SystemInfoManager;
import com.dazhi.renzhengtong.menu.model.MenuJGModel;
import com.dazhi.renzhengtong.news.DashlineItemDivider;
import com.dazhi.renzhengtong.news.NewsDetailActivity;
import com.dazhi.renzhengtong.services.adapter.ServiceHomeAdapter;
import com.dazhi.renzhengtong.services.adapter.ServiceListAdapter;
import com.dazhi.renzhengtong.services.model.JiGouModel;
import com.dazhi.renzhengtong.user.UserManager;
import com.dazhi.renzhengtong.utils.Constant;
import com.dazhi.renzhengtong.utils.NetRequest;
import com.dazhi.renzhengtong.utils.ToastHelper;
import com.dazhi.renzhengtong.utils.Utils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * Created by mac on 2018/1/25.
 */

public class ServicesHomeFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout tixi;
    private RelativeLayout product;
    private RelativeLayout standard;
    private RecyclerView recyclerView;
    private ServiceHomeAdapter adapter;
    private List<MenuJGModel> list2 = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView title = view.findViewById(R.id.common_title);
        title.setText("服务");
        swipeRefreshLayout = view.findViewById(R.id.services_home_swip);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list2.clear();
                requestList();
            }
        });
        recyclerView = view.findViewById(R.id.services_home_recyclerview);
        adapter = new ServiceHomeAdapter(R.layout.item_jigou_layout,list2);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(),CompanyDetailActivity.class);
                intent.putExtra("id",list2.get(position).getId());
                getContext().startActivity(intent);
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId()==R.id.item_jigou_ask){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("拨打电话").setMessage(SystemInfoManager.getInfo(getContext()).getSite_tel()).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED||
                                    ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED   ){
                                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                            }else {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+SystemInfoManager.getInfo(getContext()).getSite_tel()));
                                startActivity(intent);
                            }
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                }
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DashlineItemDivider());

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestList();
            }
        },recyclerView);
        adapter.disableLoadMoreIfNotFullPage();
        View header1 = LayoutInflater.from(getActivity()).inflate(R.layout.layout_service_header,null);
        adapter.addHeaderView(header1);
        tixi = header1.findViewById(R.id.search_home_tixi);
        product = header1.findViewById(R.id.search_home_product);
        standard = header1.findViewById(R.id.search_home_standard);
        tixi.setOnClickListener(this);
        product.setOnClickListener(this);
        standard.setOnClickListener(this);

        View header = LayoutInflater.from(getActivity()).inflate(R.layout.layout_jigou_header,null);
        adapter.addHeaderView(header);
        autoRefresh();
        requestList();
    }

    private void requestList() {
        HashMap<String,String> map = new HashMap<>();
//        map.put("uid", UserManager.getUser(getActivity()).getId()+"");
        NetRequest.postFormRequest(Constant.SERVICE_HOME_URL, map, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.optInt("code")==1){
                    List<MenuJGModel> array = Utils.decodeJSONARRAY(jsonObject.optString("data"),MenuJGModel.class);
                    list2.addAll(array);
                    adapter.notifyDataSetChanged();
                    stopRefresh();
                    adapter.loadMoreComplete();
                }else{
                    ToastHelper.showToast(jsonObject.optString("msg"));
                    stopRefresh();
                    adapter.loadMoreComplete();
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastHelper.showToast(R.string.request_failed);
                stopRefresh();
                adapter.loadMoreComplete();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_services_home, container, false);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), ServiceListActivity.class);
        switch (v.getId()){
            case R.id.search_home_tixi:
                intent.putExtra("id",20);
                intent.putExtra("name","体系认证");
                startActivity(intent);
                break;

            case R.id.search_home_product:
                intent.putExtra("id",21);
                intent.putExtra("name","产品认证");
                startActivity(intent);
                break;

            case R.id.search_home_standard:
                intent.putExtra("id",22);
                intent.putExtra("name","标准制定");
                startActivity(intent);
                break;

        }
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

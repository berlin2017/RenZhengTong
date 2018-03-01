package com.dazhi.renzhengtong.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dazhi.renzhengtong.MyProgressDialog;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.menu.adapter.MenuRenZhengAdapter;
import com.dazhi.renzhengtong.menu.model.MenuJGModel;
import com.dazhi.renzhengtong.news.DashlineItemDivider;
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
import java.util.Map;

import okhttp3.Request;

/**
 * Created by mac on 2018/2/6.
 */

public class MenuJGActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private Button addBtn;
    private MenuRenZhengAdapter adapter;
    private List<MenuJGModel> list = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private View emptyView;
    private Button emptyButton;
    private int page = 0;
    private MyProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_menu_jigou);
        progressDialog = new MyProgressDialog(this,R.style.Dialog);
        initTitle();
        recyclerView = findViewById(R.id.menu_jigou_recyclerview);
        addBtn = findViewById(R.id.menu_jigou_add);
        addBtn.setOnClickListener(this);
        emptyView = LayoutInflater.from(this).inflate(R.layout.layout_jigou_empty,null);
        emptyButton = emptyView.findViewById(R.id.menu_jigou_empty);
        emptyButton.setOnClickListener(this);
        swipeRefreshLayout = findViewById(R.id.menu_jigou_swip);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                requstList();
            }
        });

        adapter = new MenuRenZhengAdapter(R.layout.item_menu_jigou, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DashlineItemDivider());
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requstList();
            }
        },recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MenuJGActivity.this,MenuJGDetailActivity.class);
                intent.putExtra("id",list.get(position).getId());
                startActivityForResult(intent,1000);
            }
        });
        adapter.disableLoadMoreIfNotFullPage(recyclerView);
        autoRefresh();
        requstList();
    }

    public void requstList() {
        progressDialog.show();
        HashMap<String,String>map = new HashMap<>();
        map.put("uid",UserManager.getUser(this).getId()+"");
        NetRequest.postFormRequest(Constant.MENU_JIGOU_LIST_URL, map, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.optInt("code")==1){
                    List<MenuJGModel> array = Utils.decodeJSONARRAY(jsonObject.optString("data"),MenuJGModel.class);
                    list.addAll(array);
                    if (list.size()<=0){
                        adapter.setEmptyView(emptyView);
                        addBtn.setVisibility(View.GONE);
                    }else{
                        addBtn.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                    stopRefresh();
                    adapter.loadMoreComplete();
                }else{
                    ToastHelper.showToast(jsonObject.optString("msg"));
                    stopRefresh();
                    adapter.loadMoreComplete();
                }
                progressDialog.dismiss();
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastHelper.showToast(R.string.request_failed);
                stopRefresh();
                adapter.loadMoreComplete();
                progressDialog.dismiss();
            }
        });


    }

    public void initTitle() {
        ImageView back = findViewById(R.id.common_back);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        textView.setText("机构认证");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.common_back) {
            finish();
        } else if (v.getId() == R.id.menu_jigou_add) {
            Intent intent = new Intent(MenuJGActivity.this,MenuJGDetailActivity.class);
            startActivityForResult(intent,1000);

        }else if (v.getId() == R.id.menu_jigou_empty){
            Intent intent = new Intent(MenuJGActivity.this,MenuJGDetailActivity.class);
            startActivityForResult(intent,1000);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            autoRefresh();
            list.clear();
            requstList();
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

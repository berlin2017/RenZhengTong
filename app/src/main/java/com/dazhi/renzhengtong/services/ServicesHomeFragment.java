package com.dazhi.renzhengtong.services;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.dazhi.renzhengtong.news.DashlineItemDivider;
import com.dazhi.renzhengtong.services.adapter.ServiceHomeAdapter;
import com.dazhi.renzhengtong.services.adapter.ServiceListAdapter;
import com.dazhi.renzhengtong.services.model.JiGouModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/1/25.
 */

public class ServicesHomeFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout tixi;
    private RelativeLayout product;
    private RelativeLayout standard;
    private RecyclerView recyclerView;
    private ServiceHomeAdapter adapter;
    private List<JiGouModel> list2 = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView title = view.findViewById(R.id.common_title);
        title.setText("服务");


        JiGouModel model = new JiGouModel();
        model.setImage("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2054592640,401359956&fm=58");
        model.setName("安徽中规认证有限公司");
        list2.add(model);
        list2.add(model);
        list2.add(model);
        list2.add(model);

        recyclerView = view.findViewById(R.id.services_home_recyclerview);
        adapter = new ServiceHomeAdapter(R.layout.item_jigou_layout,list2);
//        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                //...
//            }
//        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId()==R.id.item_jigou_ask){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("拨打电话").setMessage("15605662015").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+"15605662015"));
                            startActivity(intent);
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
                intent.putExtra("id",0);
                startActivity(intent);
                break;

            case R.id.search_home_product:
                intent.putExtra("id",1);
                startActivity(intent);
                break;

            case R.id.search_home_standard:
                intent.putExtra("id",2);
                startActivity(intent);
                break;

        }
    }
}

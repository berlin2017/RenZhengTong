package com.dazhi.renzhengtong.services;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.news.DashlineItemDivider;
import com.dazhi.renzhengtong.news.adapter.BaseViewHolder;
import com.dazhi.renzhengtong.services.adapter.MySectionAdapter;
import com.dazhi.renzhengtong.services.adapter.SectionAdapter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/1/25.
 */

public class ServicesHomeFragment extends Fragment {
    private SimpleDraweeView first;
    private SimpleDraweeView two;
    private SimpleDraweeView three;
    private RecyclerView recyclerView;
    private MySectionAdapter adapter;
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
        adapter = new MySectionAdapter(getContext(),list2);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DashlineItemDivider());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_services_home, container, false);
    }


}

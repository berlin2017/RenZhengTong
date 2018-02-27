package com.dazhi.renzhengtong.evaluation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.evaluation.adapter.EvaluationAdapter;
import com.dazhi.renzhengtong.evaluation.model.EvaluationItem;
import com.dazhi.renzhengtong.news.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/2/7.
 */

public class EvaluationListActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private EvaluationAdapter adapter;
    private List<EvaluationItem> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_evaluation_list);
        list =  (List<EvaluationItem>) getIntent().getSerializableExtra("list");

        recyclerView = findViewById(R.id.evaluation_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EvaluationAdapter(R.layout.item_service_list_layout, list);

        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        recyclerView.setAdapter(adapter);
        adapter.disableLoadMoreIfNotFullPage(recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                Intent intent = new Intent(EvaluationListActivity.this, NewsDetailActivity.class);
                intent.putExtra("id", list.get(position).getAid());
                startActivity(intent);
            }
        });
        initTitle();
    }

    public void initTitle() {
        ImageView back = findViewById(R.id.common_back);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        textView.setText("方案推荐");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                finish();
                break;
        }
    }
}

package com.dazhi.renzhengtong.search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dazhi.renzhengtong.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by mac on 2018/1/25.
 */

public class SearchHomeFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout tixi;
    private RelativeLayout standard;
    private RelativeLayout download;
    private TextView title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_search_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.common_title);
        title.setText("查询");

        tixi = view.findViewById(R.id.search_home_tixi);
        standard = view.findViewById(R.id.search_home_standard);
        download = view.findViewById(R.id.search_home_download);

        tixi.setOnClickListener(this);
        download.setOnClickListener(this);
        standard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(),SearchListActivity.class);
        switch (v.getId()) {
            case R.id.search_home_tixi:
                intent.putExtra("id",15);
                intent.putExtra("name","体系查询");
                startActivity(intent);
                break;
            case R.id.search_home_standard:
                intent.putExtra("id",16);
                intent.putExtra("name","标准查询");
                startActivity(intent);
                break;
            case R.id.search_home_download:
                intent.putExtra("id",17);
                intent.putExtra("name","标准下载");
                startActivity(intent);
                break;

        }
    }
}

package com.dazhi.renzhengtong.services.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.news.model.NewsModel;
import com.dazhi.renzhengtong.services.model.TiXiModel;

import java.util.List;

/**
 * Created by mac on 2018/2/1.
 */

public class ServiceListAdapter extends BaseQuickAdapter<NewsModel,BaseViewHolder>{

    public ServiceListAdapter(int layoutResId, @Nullable List<NewsModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsModel item) {
        helper.setText(R.id.item_services_list_text,item.getPost_title());
    }
}

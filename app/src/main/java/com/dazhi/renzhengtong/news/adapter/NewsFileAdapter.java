package com.dazhi.renzhengtong.news.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.news.model.NewsFileModel;

import java.util.List;

/**
 * Created by mac on 2018/2/9.
 */

public class NewsFileAdapter extends BaseQuickAdapter<NewsFileModel,BaseViewHolder>{

    public NewsFileAdapter(int layoutResId, @Nullable List<NewsFileModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsFileModel item) {
        helper.setText(R.id.item_detail_file_title,item.getName());
    }
}

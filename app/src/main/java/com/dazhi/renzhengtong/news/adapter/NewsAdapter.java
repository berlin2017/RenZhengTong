package com.dazhi.renzhengtong.news.adapter;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.news.model.NewsModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by mac on 2018/1/30.
 */

public class NewsAdapter extends BaseQuickAdapter<NewsModel,BaseViewHolder>{

    private List<NewsModel>list ;

    public NewsAdapter(int layoutResId, @Nullable List<NewsModel> data) {
        super(layoutResId, data);
        list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsModel item) {
        helper.setText(R.id.item_news_name,item.getPost_title());
        helper.setText(R.id.item_news_time,item.getPublished_time());
        helper.setText(R.id.item_news_content,item.getPost_excerpt());
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.item_news_imageview);
        simpleDraweeView.setImageURI(Uri.parse(item.getMore().getThumbnail()));
    }
}

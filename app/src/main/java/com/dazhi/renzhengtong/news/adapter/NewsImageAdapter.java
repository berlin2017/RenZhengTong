package com.dazhi.renzhengtong.news.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.news.model.ImageModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.net.URI;
import java.util.List;

/**
 * Created by mac on 2018/1/25.
 */

public class NewsImageAdapter extends PagerAdapter{

    private List<ImageModel>list;
    private Context context;
    private LayoutInflater mInflater;

    public NewsImageAdapter(List<ImageModel> list,Context context) {
        this.list = list;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.item_news_image,container,false);
        SimpleDraweeView simpleDraweeView = view.findViewById(R.id.item_news_pager_image);
        simpleDraweeView.setImageURI(Uri.parse(list.get(position).getImage()));
        container.addView(view);
        return  container;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}

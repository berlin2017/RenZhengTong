package com.dazhi.renzhengtong.news.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.news.model.NewsModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by mac on 2018/1/25.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsHolder> {
    private List<NewsModel>list;
    private Context context;

    public NewsListAdapter(List<NewsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsHolder(LayoutInflater.from(context).inflate(R.layout.item_news_list,parent,false));
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        NewsModel model = list.get(position);
        holder.content.setText(model.getContent());
        holder.name.setText(model.getName());
        holder.time.setText(model.getTime());
        holder.imageview.setImageURI(Uri.parse(model.getImage()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class NewsHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView content;
        TextView time;
        SimpleDraweeView imageview;

        public NewsHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_news_name);
            content = itemView.findViewById(R.id.item_news_content);
            time = itemView.findViewById(R.id.item_news_time);
            imageview = (SimpleDraweeView) itemView.findViewById(R.id.item_news_imageview);
        }
    }
}

package com.dazhi.renzhengtong.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by mac on 2018/1/26.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder{

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public void setText(int resId,String text){
        TextView textView = itemView.findViewById(resId);
        if (textView!=null){
            textView.setText(text);
        }

    }

    private SparseArray<View> childViews = new SparseArray<>();

    public <T extends View> T getView(int id) {
        View childView = childViews.get(id);
        if (childView == null) {
            childView = itemView.findViewById(id);
            if (childView != null) {
                childViews.put(id, childView);
            }
        }
        return null == childView ? null : (T) childView;
    }

    public void setText(int resId,int textRes){
        TextView textView = getView(resId);
        if (textView!=null){
            textView.setText(textRes);
        }
    }

    public void setImageResource(int resId,int imageId){
        ImageView imageView = getView(resId);
        imageView.setImageResource(imageId);
    }

    public ImageView getImageView(int resId){
        ImageView imageView = getView(resId);
        return  imageView;
    }

}

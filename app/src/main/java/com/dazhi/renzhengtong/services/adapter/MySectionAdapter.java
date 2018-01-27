package com.dazhi.renzhengtong.services.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.services.JiGouModel;
import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/1/27.
 */

public class MySectionAdapter extends GroupedRecyclerViewAdapter{

    private List<JiGouModel> mData = new ArrayList<>();

    public MySectionAdapter(Context context) {
        super(context);
    }

    public MySectionAdapter(Context context, List<JiGouModel> mData) {
        super(context);
        this.mData = mData;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mData.size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.layout_jigou_header;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.item_jigou_layout;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        JiGouModel model = mData.get(childPosition);
        holder.setText(R.id.item_jigou_name, model.getName());
        SimpleDraweeView simpleDraweeView = holder.get(R.id.item_jigou_image);
        simpleDraweeView.setImageURI(Uri.parse(model.getImage()));
    }

}

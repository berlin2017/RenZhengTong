package com.dazhi.renzhengtong.services.adapter;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.menu.model.MenuJGModel;
import com.dazhi.renzhengtong.services.model.JiGouModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by mac on 2018/1/30.
 */

public class ServiceHomeAdapter extends BaseQuickAdapter<MenuJGModel,BaseViewHolder>{

    public ServiceHomeAdapter(int layoutResId, @Nullable List<MenuJGModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuJGModel item) {
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.item_jigou_image);
        if (!TextUtils.isEmpty(item.getJglogo())){
            simpleDraweeView.setImageURI(Uri.parse(item.getJglogo()));
        }
        helper.setText(R.id.item_jigou_name,item.getJgname());
        helper.addOnClickListener(R.id.item_jigou_ask);
    }
}

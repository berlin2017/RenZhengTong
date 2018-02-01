package com.dazhi.renzhengtong.services.adapter;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.services.model.JiGouModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by mac on 2018/1/30.
 */

public class ServiceHomeAdapter extends BaseQuickAdapter<JiGouModel,BaseViewHolder>{

    public ServiceHomeAdapter(int layoutResId, @Nullable List<JiGouModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JiGouModel item) {
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.item_jigou_image);
        simpleDraweeView.setImageURI(Uri.parse(item.getImage()));
        helper.setText(R.id.item_jigou_name,item.getName());
        helper.addOnClickListener(R.id.item_jigou_ask);
    }
}

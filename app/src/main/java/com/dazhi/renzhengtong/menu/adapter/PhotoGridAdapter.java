package com.dazhi.renzhengtong.menu.adapter;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dazhi.renzhengtong.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by mac on 2018/2/27.
 */

public class PhotoGridAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    public PhotoGridAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        SimpleDraweeView simpleDraweeView = helper.getView(R.id.item_company_photo);
        simpleDraweeView.setImageURI(Uri.parse(item));
    }
}

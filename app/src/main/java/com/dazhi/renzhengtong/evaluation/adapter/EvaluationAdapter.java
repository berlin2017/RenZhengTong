package com.dazhi.renzhengtong.evaluation.adapter;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.evaluation.model.EvaluationItem;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by mac on 2018/2/8.
 */

public class EvaluationAdapter extends BaseQuickAdapter<EvaluationItem,BaseViewHolder> {


    public EvaluationAdapter(int layoutResId, @Nullable List<EvaluationItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EvaluationItem item) {
        helper.setText(R.id.item_services_list_text,item.getName());

//        helper.setText(R.id.item_search_click,String.format("%d人已咨询",item.getPost_hits()));
//        helper.setText(R.id.item_search_name,item.getPost_title());
//        if (TextUtils.isEmpty(item.getPost_excerpt())){
//            helper.setText(R.id.item_search_remake,item.getPost_title());
//        }else{
//            helper.setText(R.id.item_search_remake,item.getPost_excerpt());
//        }
//        SimpleDraweeView simpleDraweeView = helper.getView(R.id.item_search_imageview);
//        simpleDraweeView.setImageURI(Uri.parse(item.getMore().getThumbnail()));
    }

}

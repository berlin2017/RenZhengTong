package com.dazhi.renzhengtong.evaluation.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.evaluation.model.EvaluationItem;

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
    }
}

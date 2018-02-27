package com.dazhi.renzhengtong.evaluation.adapter;

import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.evaluation.model.ProductType;

import java.util.List;

/**
 * Created by mac on 2018/2/23.
 */

public class EvaluationHomeAdapter extends BaseQuickAdapter<ProductType,BaseViewHolder>{

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

    public EvaluationHomeAdapter(int layoutResId, @Nullable List<ProductType> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductType item) {
        helper.setText(R.id.item_evaluation_check,item.getTitle());
        helper.setChecked(R.id.item_evaluation_check,false);
        if (onCheckedChangeListener!=null){
            helper.setOnCheckedChangeListener(R.id.item_evaluation_check,onCheckedChangeListener);
            helper.getView(R.id.item_evaluation_check).setTag(item);
        }

    }

    public void setOnCheckChangeListener(CompoundButton.OnCheckedChangeListener listener){
        onCheckedChangeListener = listener;
    }
}

package com.dazhi.renzhengtong.evaluation.adapter;

import android.support.annotation.Nullable;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.evaluation.model.OptionItem;
import com.dazhi.renzhengtong.evaluation.model.TestModel;

import java.util.List;

/**
 * Created by mac on 2018/2/1.
 */

public class OptionsAdapter extends BaseQuickAdapter<OptionItem,BaseViewHolder> {

    private TestModel model;

    public OptionsAdapter(int layoutResId, @Nullable List<OptionItem> data) {
        super(layoutResId, data);
    }

    public OptionsAdapter(int layoutResId, @Nullable TestModel model) {
        this(layoutResId, model.getList());
        this.model = model;
    }

    @Override
    protected void convert(BaseViewHolder helper, OptionItem item) {
        helper.setText(R.id.item_options_text,item.getText());
        helper.setText(R.id.item_options_number,helper.getAdapterPosition()+"");
        RadioButton radioButton = helper.getView(R.id.item_options_radiobutton);
        if (model.getSelectPosition() == helper.getAdapterPosition()){
            radioButton.setChecked(true);
        }else{
            radioButton.setChecked(false);
        }
        helper.addOnClickListener(R.id.item_options_radiobutton);
    }

}

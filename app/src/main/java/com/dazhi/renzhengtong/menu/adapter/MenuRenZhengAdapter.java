package com.dazhi.renzhengtong.menu.adapter;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.menu.model.MenuJGModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by mac on 2018/2/6.
 */

public class MenuRenZhengAdapter extends BaseQuickAdapter<MenuJGModel,BaseViewHolder>{

    public MenuRenZhengAdapter(int layoutResId, @Nullable List<MenuJGModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuJGModel item) {
       SimpleDraweeView photo =  helper.getView(R.id.item_menu_jigou_photo);
       if (!TextUtils.isEmpty(item.getJglogo())){
           photo.setImageURI(Uri.parse(item.getJglogo()));
       }
       helper.setText(R.id.item_menu_jigou_name,item.getJgname());
       String s = String.format("%s   %s",item.getJgxm(),item.getJgtel());
       helper.setText(R.id.item_menu_jigou_phone,s);
       if (item.getStatus()==1){
           helper.setText(R.id.item_menu_jigou_type,"已认证");
       }else{
           helper.setText(R.id.item_menu_jigou_type,"正在审核");
       }

    }

}

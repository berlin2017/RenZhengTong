package com.dazhi.renzhengtong.evaluation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.dazhi.renzhengtong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/1/25.
 */

public class EvaluationHomeFragment extends Fragment implements View.OnClickListener {
    private EditText people_edit;
    private EditText qiye_edit;
    private EditText hangye_edit;
    private EditText guimo_edit;
    private EditText product_edit;
    private Button commit_btn;
    private List<String> list = new ArrayList<>();
    private List<String> guimo_list = new ArrayList<>();
    private List<String> hangye_list = new ArrayList<>();
    private List<List<String>> hangye_list2 = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        people_edit = view.findViewById(R.id.evaluation_people_edit);
        people_edit.setOnClickListener(this);
        list.add("1-25人");
        list.add("26-45人");
        list.add("46-64人");
        list.add("65-125人");
        list.add("126-275人");
        list.add("276人以上");

        guimo_edit = view.findViewById(R.id.evaluation_guimo_edit);
        guimo_edit.setOnClickListener(this);
        guimo_list.add("500万以下");
        guimo_list.add("500-1000万");
        guimo_list.add("1000-2000万");
        guimo_list.add("2000-5000万");
        guimo_list.add("5000万以上");

        qiye_edit = view.findViewById(R.id.evaluation_qiye_edit);
        hangye_edit = view.findViewById(R.id.evaluation_hangye_edit);
        hangye_edit.setOnClickListener(this);
        product_edit = view.findViewById(R.id.evaluation_product_edit);

        commit_btn = view.findViewById(R.id.evaluation_commit_btn);
        commit_btn.setOnClickListener(this);

        TextView title = view.findViewById(R.id.common_title);
        title.setText("测评");

        hangye_list.add("生产型");
        hangye_list.add("非生产型");
        List<String>shengchan = new ArrayList<>();
        shengchan.add("含有环评批复验收");
        shengchan.add("食品加工业");
        shengchan.add("含有环评批复验收");
        shengchan.add("汽车相关企业");
        shengchan.add("是否出口");

        List<String>feishengchan = new ArrayList<>();
        feishengchan.add("农业企业");
        feishengchan.add("信息技术服务业");
        feishengchan.add("建筑型企业");

        hangye_list2.add(shengchan);
        hangye_list2.add(feishengchan);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_evaluation_home, container, false);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.evaluation_people_edit) {
            //条件选择器
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    people_edit.setText(list.get(options1));
                }
            }).setTitleText("人数选择").setCancelText("取消").setSubmitText("确定").build();
            pvOptions.setPicker(list);
            pvOptions.show();
        } else if (v.getId() == R.id.evaluation_guimo_edit) {
            //条件选择器
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    guimo_edit.setText(guimo_list.get(options1));
                }
            }).setTitleText("规模选择").setCancelText("取消").setSubmitText("确定").build();
            pvOptions.setPicker(guimo_list);
            pvOptions.show();
        } else if (v.getId() == R.id.evaluation_commit_btn) {
            commit();
        } else if (v.getId() == R.id.evaluation_hangye_edit) {
            //条件选择器
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    people_edit.setText(hangye_list2.get(options1).get(option2));
                }
            }).setTitleText("行业选择").setCancelText("取消").setSubmitText("确定").build();
            pvOptions.setPicker(hangye_list,hangye_list2);
            pvOptions.show();
        }
    }

    public void commit() {

    }
}

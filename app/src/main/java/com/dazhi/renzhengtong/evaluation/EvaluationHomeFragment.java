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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        people_edit = view.findViewById(R.id.evaluation_people_edit);
        people_edit.setOnClickListener(this);
        list.add("1-10人");
        list.add("11-20人");
        list.add("21-50人");
        list.add("51-100人");
        list.add("101-200人");
        list.add("200人以上");

        guimo_edit = view.findViewById(R.id.evaluation_guimo_edit);
        guimo_edit.setOnClickListener(this);
        guimo_list.add("小规模");
        guimo_list.add("中小规模");
        guimo_list.add("中规模");
        guimo_list.add("大规模");
        guimo_list.add("超大规模");

        qiye_edit = view.findViewById(R.id.evaluation_qiye_edit);
        hangye_edit = view.findViewById(R.id.evaluation_hangye_edit);
        product_edit = view.findViewById(R.id.evaluation_product_edit);

        commit_btn = view.findViewById(R.id.evaluation_commit_btn);
        commit_btn.setOnClickListener(this);

        TextView title = view.findViewById(R.id.common_title);
        title.setText("测评");
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
            }).build();
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
            }).build();
            pvOptions.setPicker(guimo_list);
            pvOptions.show();
        }else if (v.getId() == R.id.evaluation_commit_btn){
            commit();
        }
    }

    public void commit(){

    }
}

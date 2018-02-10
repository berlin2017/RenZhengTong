package com.dazhi.renzhengtong.evaluation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.news.model.NewsModel;
import com.dazhi.renzhengtong.user.LoginActivity;
import com.dazhi.renzhengtong.user.UserManager;
import com.dazhi.renzhengtong.utils.Constant;
import com.dazhi.renzhengtong.utils.NetRequest;
import com.dazhi.renzhengtong.utils.ToastHelper;
import com.dazhi.renzhengtong.utils.Utils;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * Created by mac on 2018/1/25.
 */

public class EvaluationHomeFragment extends Fragment implements View.OnClickListener {
    private EditText people_edit;
    private EditText qiye_edit;
    private EditText hangye_edit;
    private EditText guimo_edit;
    private EditText product_edit;
    private EditText product_type_edit;
    private Button commit_btn;
    private List<String> list = new ArrayList<>();
    private List<String> guimo_list = new ArrayList<>();
    private List<String> hangye_list = new ArrayList<>();
    private List<List<String>> hangye_list2 = new ArrayList<>();
    private List<String> hangye_value = new ArrayList<>();
    private List<String> hangye_value2 = new ArrayList<>();
    private String selected_hangye = "";
    private String selected_guimo = "";
    private String selected_people = "";
    private String selected_product = "";
    private List<String> product_value_list = new ArrayList<>();
    private List<String> product_list = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.evaluation_home_progress);
        people_edit = view.findViewById(R.id.evaluation_people_edit);
        product_type_edit = view.findViewById(R.id.evaluation_product_type_edit);
        product_type_edit.setOnClickListener(this);
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
        List<String> shengchan = new ArrayList<>();
        shengchan.add("含有环评批复验收");
        shengchan.add("食品加工业");
        shengchan.add("含有环评批复验收");
        shengchan.add("汽车相关企业");
        shengchan.add("是否出口");

        List<String> feishengchan = new ArrayList<>();
        feishengchan.add("农业企业");
        feishengchan.add("信息技术服务业");
        feishengchan.add("建筑型企业");

        hangye_list2.add(shengchan);
        hangye_list2.add(feishengchan);
        hangye_value.add("B1-1");
        hangye_value.add("B1-2");
        hangye_value.add("B1-3");
        hangye_value.add("B1-4");

        hangye_value2.add("B2-1");
        hangye_value2.add("B2-2");
        hangye_value2.add("B2-3");

        product_value_list.add("E1");
        product_value_list.add("E2");

        product_list.add("机械电气类产品");
        product_list.add("医疗器械类产品");
        product_list.add("无");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_evaluation_home, container, false);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.evaluation_people_edit) {
            showPeopleSelect();
        } else if (v.getId() == R.id.evaluation_guimo_edit) {
            showGuiMoSelect();
        } else if (v.getId() == R.id.evaluation_commit_btn) {
            commit();
        } else if (v.getId() == R.id.evaluation_hangye_edit) {
            showHangYeSelect();
        } else if (v.getId() == R.id.evaluation_product_type_edit) {
            showProductSelect();
        }
    }

    public void showHangYeSelect(){
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                hangye_edit.setText(hangye_list2.get(options1).get(option2));
                if (options1 == 0) {
                    selected_hangye = hangye_value.get(option2);
                } else {
                    selected_hangye = hangye_value2.get(option2);
                }

            }
        }).setTitleText("行业选择").setCancelText("取消").setSubmitText("确定").build();
        pvOptions.setPicker(hangye_list, hangye_list2);
        pvOptions.show();
    }

    public void showGuiMoSelect(){
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                guimo_edit.setText(guimo_list.get(options1));
                selected_guimo = "C"+(options1+1);
            }
        }).setTitleText("规模选择").setCancelText("取消").setSubmitText("确定").build();
        pvOptions.setPicker(guimo_list);
        pvOptions.show();
    }

    public void showPeopleSelect(){
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                people_edit.setText(list.get(options1));
                selected_people = "D"+(options1+1);
            }
        }).setTitleText("人数选择").setCancelText("取消").setSubmitText("确定").build();
        pvOptions.setPicker(list);
        pvOptions.show();
    }

    public void showProductSelect(){
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                product_type_edit.setText(product_list.get(options1));
                selected_product = product_value_list.get(options1);
            }
        }).setTitleText("产品类型选择").setCancelText("取消").setSubmitText("确定").build();
        pvOptions.setPicker(product_list);
        pvOptions.show();
    }

    public void commit() {

        if (com.dazhi.renzhengtong.user.UserManager.getUser(getContext())==null){
            ToastHelper.showToast("请先登录");
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            return;
        }

        if (TextUtils.isEmpty(qiye_edit.getText().toString())) {
            ToastHelper.showToast("请填写企业名称");
            return;
        }

        if (TextUtils.isEmpty(selected_hangye)) {
            ToastHelper.showToast("请选择行业类型");
            showHangYeSelect();
            return;
        }

        if (TextUtils.isEmpty(selected_guimo)) {
            ToastHelper.showToast("请选择规模大小");
            showGuiMoSelect();
            return;
        }

        if (TextUtils.isEmpty(selected_people)) {
            ToastHelper.showToast("请选择人数");
            showPeopleSelect();
            return;
        }

        if (TextUtils.isEmpty(selected_product)) {
            ToastHelper.showToast("请选择产品类型");
            showProductSelect();
            return;
        }

        if (TextUtils.isEmpty(product_edit.getText().toString())) {
            ToastHelper.showToast("请填写产品名称");
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        HashMap<String, String> map = new HashMap<>();
        map.put("A", hangye_edit.getText().toString());
        map.put("B", selected_hangye);
        map.put("C", selected_guimo);
        map.put("D", selected_people);
        map.put("E", selected_product);
        map.put("product", hangye_edit.getText().toString());
        map.put("uid", UserManager.getUser(getContext()).getId()+"");
        NetRequest.postFormRequest(Constant.EVALUATION_URL, map, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.optInt("code") == 1) {
                    List<EvaluationItem> list = Utils.decodeJSONARRAY(jsonObject.optString("data"), EvaluationItem.class);
                    Intent intent = new Intent(getActivity(), EvaluationListActivity.class);
                    intent.putExtra("list", (Serializable) list);
                    startActivity(intent);
                } else {
                    ToastHelper.showToast(jsonObject.optString("msg"));
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastHelper.showToast(R.string.request_failed);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}

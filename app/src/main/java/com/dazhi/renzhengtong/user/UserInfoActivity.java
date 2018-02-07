package com.dazhi.renzhengtong.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.utils.Constant;
import com.dazhi.renzhengtong.utils.NetRequest;
import com.dazhi.renzhengtong.utils.ToastHelper;
import com.dazhi.renzhengtong.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * Created by Admin on 2018/1/29 0029.
 */

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView name;
    private SimpleDraweeView simpleDraweeView;
    private TextView company;
    private TextView type;
    private TextView time;
    private RelativeLayout photo_layout;
    private RelativeLayout name_layout;
    private RelativeLayout company_layout;
    private RelativeLayout type_layout;
    private RelativeLayout time_layout;
    private PopupWindow pop;
    private View rootview;
    private List<String> list = new ArrayList<>();
    private UserInfo userInfo;
    private ProgressBar progressBar;
    private Button commit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootview = getLayoutInflater().inflate(R.layout.layout_userinfo, null);
        setContentView(rootview);
        initTitle();
        name = findViewById(R.id.userinfo_name_value);
        simpleDraweeView = findViewById(R.id.userinfo_photo_image);
        company = findViewById(R.id.userinfo_company_value);
        time = findViewById(R.id.userinfo_time_value);
        type = findViewById(R.id.userinfo_type_value);
        UserInfo info = UserManager.getUser(this);
        if (info != null) {
            name.setText(info.getUser_nickname());
            simpleDraweeView.setImageURI(Uri.parse(info.getAvatar()));
            company.setText(info.getCompany());
            time.setText(info.getRztime());
            type.setText(info.getRztype());
        }

        photo_layout = findViewById(R.id.photo_layout);
        name_layout = findViewById(R.id.name_layout);
        company_layout = findViewById(R.id.company_layout);
        type_layout = findViewById(R.id.type_layout);
        time_layout = findViewById(R.id.time_layout);
        photo_layout.setOnClickListener(this);
        name_layout.setOnClickListener(this);
        company_layout.setOnClickListener(this);
        type_layout.setOnClickListener(this);
        time_layout.setOnClickListener(this);

        list.add("1-10人");
        list.add("11-20人");
        list.add("21-50人");
        list.add("51-100人");
        list.add("101-200人");
        list.add("200人以上");
        userInfo = UserManager.getUser(this);
        if (userInfo == null || userInfo.getRztype() == null || userInfo.getRztype().isEmpty()) {
            type.setText("输入认证类别");
//            type.setTextColor(getResources().getColor(android.R.color.darker_gray));
        } else {
            type.setText(userInfo.getRztype());
//            type.setTextColor(getResources().getColor(android.R.color.black));
        }

        if (userInfo == null || userInfo.getRztime() == null || userInfo.getRztime().isEmpty()) {
            time.setText("选择认证时间");
//            type.setTextColor(getResources().getColor(android.R.color.darker_gray));
        } else {
            time.setText(userInfo.getRztime());
//            time.setTextColor(getResources().getColor(android.R.color.black));
        }

        progressBar = findViewById(R.id.userinfo_progress);
        commit = findViewById(R.id.userinfo_commit_btn);
        commit.setOnClickListener(this);
    }

    public void initTitle() {
        ImageView back = findViewById(R.id.common_back);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        textView.setText("我的资料");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.photo_layout:
                showPopupWindow();
                break;
            case R.id.company_layout:
                final EditText editText = new EditText(this);
                if (userInfo != null && userInfo.getCompany() != null && !userInfo.getCompany().isEmpty()) {
                    editText.setText(UserManager.getUser(this).getCompany());
                }

                new AlertDialog.Builder(this)
                        .setTitle("请填写公司名称")
                        .setView(editText)
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = editText.getText().toString();
                                if (input.equals("")) {
                                    Toast.makeText(getApplicationContext(), "公司名称不能为空！" + input, Toast.LENGTH_LONG).show();
                                } else {
                                    company.setText(input);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.name_layout:
                final EditText editText2 = new EditText(this);
                if (userInfo != null && userInfo.getUser_nickname() != null && !userInfo.getUser_nickname().isEmpty()) {
                    editText2.setText(UserManager.getUser(this).getUser_nickname());
                }

                new AlertDialog.Builder(this)
                        .setTitle("请输入姓名")
                        .setView(editText2)
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = editText2.getText().toString();
                                if (input.equals("")) {
                                    Toast.makeText(getApplicationContext(), "姓名不能为空！" + input, Toast.LENGTH_LONG).show();
                                } else {
                                    name.setText(input);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.type_layout:
//                //条件选择器
//                OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
//                    @Override
//                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
//                        //返回的分别是三个级别的选中位置
//                        type.setText(list.get(options1));
//                    }
//                }).build();
//                pvOptions.setPicker(list);
//                pvOptions.show();
                final EditText editText3 = new EditText(this);
                if (userInfo != null && userInfo.getRztype() != null &&! userInfo.getRztype().isEmpty()) {
                    editText3.setText(UserManager.getUser(this).getRztype());
                }

                new AlertDialog.Builder(this)
                        .setTitle("请输入认证类别")
                        .setView(editText3)
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String input = editText3.getText().toString();
                                if (input.equals("")) {
                                    Toast.makeText(getApplicationContext(), "认证类别不能为空！" + input, Toast.LENGTH_LONG).show();
                                } else {
                                    type.setText(input);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.time_layout:
                boolean[] types = {true, true, true, false, false, false};
                TimePickerView pickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Utils.DATE_FORMAT_MONTH_DAY);
                        String timestring = simpleDateFormat.format(date);
                        time.setText(timestring);
                    }
                }).setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示
                        .setDividerColor(Color.DKGRAY)
                        .setContentSize(20)
                        .setType(types)
                        .setDate(Calendar.getInstance()).build();
                pickerView.show();
                break;

            case R.id.userinfo_commit_btn:

                //网络请求
                commit();

                break;
        }
    }

    private void commit() {
        if (TextUtils.isEmpty(name.getText())){
            ToastHelper.showToast("姓名不能为空");
            return;
        }

        if (TextUtils.isEmpty(company.getText())){
            ToastHelper.showToast("公司不能为空");
            return;
        }
        HashMap<String,String>map = new HashMap<>();
        map.put("uid",userInfo.getId()+"");
        map.put("user_nickname",name.getText().toString());
        map.put("company",company.getText().toString());
        map.put("rztype",type.getText().toString());
        map.put("rztime",time.getText().toString());
        progressBar.setVisibility(View.VISIBLE);
        NetRequest.postFormRequest(Constant.USER_CHSNGE_INFO_URL, map, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.optInt("code")==1){
                    userInfo.setCompany(company.getText().toString());
                    userInfo.setUser_nickname(name.getText().toString());
                    userInfo.setRztime(time.getText().toString());
                    userInfo.setRztype(type.getText().toString());
                    UserManager.saveUser(UserInfoActivity.this,userInfo);
                    ToastHelper.showToast("修改成功");
                }else{
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            cutImage(data.getData());
        } else if (requestCode == 2000 && resultCode == RESULT_OK) {
            cutImage(data.getData());
        } else if (requestCode == 3000 && resultCode == RESULT_OK) {
            Bitmap bitmap = data.getExtras().getParcelable("data");
            simpleDraweeView.setImageBitmap(bitmap);
        }
    }

    public void cutImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", "1");
        intent.putExtra("aspectY", "1");
        intent.putExtra("outputX", "200");
        intent.putExtra("outputY", "200");
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3000);
    }


    public void showPopupWindow() {
        pop = new PopupWindow(this);
        View view = getLayoutInflater().inflate(R.layout.layout_imagepick_pop,
                null);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setAnimationStyle(R.style.MyPopStyle);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        Button bt1 = (Button) view.findViewById(R.id.imagepick_pop_local);
        Button bt2 = (Button) view.findViewById(R.id.imagepick_pop_camer);
        Button bt3 = (Button) view.findViewById(R.id.imagepick_pop_cancel);
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1000);
                pop.dismiss();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent2, 2000);
                pop.dismiss();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
            }
        });
        pop.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }


}

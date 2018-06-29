package com.dazhi.renzhengtong.user;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
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
import com.dazhi.renzhengtong.MyProgressDialog;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.menu.MenuJGDetailActivity;
import com.dazhi.renzhengtong.utils.Constant;
import com.dazhi.renzhengtong.utils.NetRequest;
import com.dazhi.renzhengtong.utils.ToastHelper;
import com.dazhi.renzhengtong.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    private String select_image;
    private MyProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new MyProgressDialog(this, R.style.Dialog);
        rootview = getLayoutInflater().inflate(R.layout.layout_userinfo, null);
        setContentView(rootview);
        initTitle();
        name = findViewById(R.id.userinfo_name_value);
        simpleDraweeView = findViewById(R.id.userinfo_photo_image);
        company = findViewById(R.id.userinfo_company_value);
        time = findViewById(R.id.userinfo_time_value);
        type = findViewById(R.id.userinfo_type_value);

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

        progressBar = findViewById(R.id.userinfo_progress);
        commit = findViewById(R.id.userinfo_commit_btn);
        commit.setOnClickListener(this);
        userInfo = UserManager.getUser(this);
        getInfo();
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
                if (userInfo != null && userInfo.getRztype() != null && !userInfo.getRztype().isEmpty()) {
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
        if (TextUtils.isEmpty(name.getText())) {
            ToastHelper.showToast("姓名不能为空");
            return;
        }

        if (TextUtils.isEmpty(company.getText())) {
            ToastHelper.showToast("公司不能为空");
            return;
        }
        progressDialog.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", userInfo.getId() + "");
        map.put("user_nickname", name.getText().toString());
        map.put("company", company.getText().toString());
        map.put("rztype", type.getText().toString());
        map.put("rztime", time.getText().toString());
        if (select_image != null) {
            NetRequest.postFile(Constant.USER_CHANGE_INFO_URL, map, "logo", new File(select_image), new NetRequest.DataCallBack() {

                @Override
                public void requestSuccess(String result) throws Exception {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.optInt("code") == 1) {
                        getInfo();
                        ToastHelper.showToast("修改成功");
                    } else {
                        ToastHelper.showToast(jsonObject.optString("msg"));
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void requestFailure(Request request, IOException e) {
                    ToastHelper.showToast(R.string.request_failed);
                    progressDialog.dismiss();
                }
            });
        } else {
            NetRequest.postFormRequest(Constant.USER_CHANGE_INFO_URL, map, new NetRequest.DataCallBack() {
                @Override
                public void requestSuccess(String result) throws Exception {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.optInt("code") == 1) {
                        getInfo();
                        ToastHelper.showToast("修改成功");
                    } else {
                        ToastHelper.showToast(jsonObject.optString("msg"));
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void requestFailure(Request request, IOException e) {
                    ToastHelper.showToast(R.string.request_failed);
                    progressDialog.dismiss();
                }
            });
        }


    }

    public void getInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", userInfo.getId() + "");
        progressDialog.show();
        NetRequest.postFormRequest(Constant.USER_INFO_URL, map, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.optInt("code") == 1) {
                    UserInfo userInfo1 = Utils.decodeJSON(jsonObject.optJSONArray("data").optString(0), UserInfo.class);
                    userInfo = userInfo1;
                    UserManager.saveUser(UserInfoActivity.this, userInfo);
                    updataUser();
                    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(UserInfoActivity.this);
                    Intent intent = new Intent("login_success");
                    localBroadcastManager.sendBroadcastSync(intent);
                } else {
                    ToastHelper.showToast(jsonObject.optString("msg"));
                }
                progressDialog.dismiss();
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastHelper.showToast(R.string.request_failed);
                progressDialog.dismiss();
            }
        });
    }

    public void updataUser() {
        if (userInfo != null) {
            name.setText(userInfo.getUser_nickname());
            simpleDraweeView.setImageURI(Uri.parse(Constant.BASE_URL + userInfo.getLogo()));
            company.setText(userInfo.getCompany());
            time.setText(userInfo.getRztime());
            type.setText(userInfo.getRztype());
        }

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4000) {
            List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
            if (list.size() > 0) {
                simpleDraweeView.setImageURI(Uri.fromFile(new File(list.get(0).getCutPath())));
                select_image = list.get(0).getCutPath();
            }
        }
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
                openGalley();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UserInfoActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    openCamera();
                }

            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
            }
        });
        pop.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void openCamera() {
        PictureSelector pictureSelector = PictureSelector.create(UserInfoActivity.this);
        pictureSelector.openCamera(PictureMimeType.ofImage()).maxSelectNum(1).enableCrop(true).forResult(4000);
        pop.dismiss();
    }

    public void openGalley() {
        PictureSelector pictureSelector = PictureSelector.create(UserInfoActivity.this);
        pictureSelector.openGallery(PictureMimeType.ofImage()).maxSelectNum(1).enableCrop(true).forResult(4000);
        pop.dismiss();
    }


}

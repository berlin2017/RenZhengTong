package com.dazhi.renzhengtong.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dazhi.renzhengtong.MainActivity;
import com.dazhi.renzhengtong.MyProgressDialog;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.menu.adapter.GridImageAdapter;
import com.dazhi.renzhengtong.menu.adapter.PhotoGridAdapter;
import com.dazhi.renzhengtong.menu.model.MenuJGModel;
import com.dazhi.renzhengtong.user.UserManager;
import com.dazhi.renzhengtong.utils.Constant;
import com.dazhi.renzhengtong.utils.NetRequest;
import com.dazhi.renzhengtong.utils.ToastHelper;
import com.dazhi.renzhengtong.utils.Utils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

/**
 * Created by mac on 2018/2/6.
 */

public class MenuJGDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private int id = 0;
    private ProgressBar progressBar;
    private SimpleDraweeView simpleDraweeView;
    private EditText company;
    private EditText name;
    private EditText phone;
    private Button save;
    private PopupWindow pop;
    private View rootview;
    private MenuJGModel model;
    private Bitmap select_image;
    private final String PATH = "mnt/sdcard/jglogo.jpg";
    private RecyclerView recyclerView;
    private EditText location_edit;
    private EditText info_edit;
    private EditText product_edit;
    private GridImageAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 3;
    private List<String>list = new ArrayList<>();
    private PictureSelector pictureSelector;
    private MyProgressDialog progressDialog;
    private RecyclerView recyclerView2;
    private GridImageAdapter adapter2;
    private int maxSelectNum2 = 1;
    private List<LocalMedia> selectList2 = new ArrayList<>();
    private List<String>list2 = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new MyProgressDialog(this,R.style.Dialog);
        id = getIntent().getIntExtra("id", 0);
        rootview = getLayoutInflater().inflate(R.layout.layout_menu_jigou_detail, null);
        setContentView(rootview);
        initTitle();
        recyclerView = findViewById(R.id.jigou_detail_photo_recyclerview);
        recyclerView2 = findViewById(R.id.jigou_detail_yingye_recyclerview);
        location_edit = findViewById(R.id.jigou_detail_location_edit);
        info_edit = findViewById(R.id.jigou_detail_info_edit);
        product_edit = findViewById(R.id.jigou_detail_product_edit);
        progressBar = findViewById(R.id.jigou_detail_progressbar);
        simpleDraweeView = findViewById(R.id.jigou_detail_logo);
        simpleDraweeView.setOnClickListener(this);
        company = findViewById(R.id.jigou_detail_company_edit);
        name = findViewById(R.id.jigou_detail_name_edit);
        phone = findViewById(R.id.jigou_detail_phone_edit);
        save = findViewById(R.id.jigou_detail_save);
        save.setOnClickListener(this);
        if (id != 0) {
            requestDetail();
        }

        FullyGridLayoutManager manager = new FullyGridLayoutManager(MenuJGDetailActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(MenuJGDetailActivity.this, onAddPicClickListener);
        adapter.setList(list);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);

        adapter2 = new GridImageAdapter(MenuJGDetailActivity.this, onAddPicClickListener2);
        adapter.setSelectMax(maxSelectNum2);
        FullyGridLayoutManager manager2 = new FullyGridLayoutManager(MenuJGDetailActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(manager2);
        recyclerView2.setAdapter(adapter2);
//        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, View v) {
//                if (selectList.size() > 0) {
//                    LocalMedia media = selectList.get(position);
//                    String pictureType = media.getPictureType();
//                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
//                    switch (mediaType) {
//                        case 1:
//                            // 预览图片 可自定长按保存路径
//                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
//                            PictureSelector.create(MenuJGDetailActivity.this).externalPicturePreview(position, selectList);
//                            break;
//                        case 2:
//                            // 预览视频
//                            PictureSelector.create(MenuJGDetailActivity.this).externalPictureVideo(media.getPath());
//                            break;
//                        case 3:
//                            // 预览音频
//                            PictureSelector.create(MenuJGDetailActivity.this).externalPictureAudio(media.getPath());
//                            break;
//                    }
//                }
//            }
//        });
    }



    private GridImageAdapter.onAddPicClickListener onAddPicClickListener2 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
                // 进入相册 以下是例子：不需要的api可以不写
            int number = maxSelectNum2;
            if (list2!=null&&list2.size()>0){
                number = maxSelectNum2 - list2.size();
            }
            pictureSelector = PictureSelector.create(MenuJGDetailActivity.this);
            pictureSelector.openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .maxSelectNum(number)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.MULTIPLE )// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                        .enableCrop(false)// 是否裁剪
                        .compress(false)// 是否压缩
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        //.compressSavePath(getPath())//压缩图片保存地址
                        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                        .selectionMedia(selectList2)// 是否传入已选图片
                        //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled(true) // 裁剪是否可旋转图片
                        //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.videoSecond()//显示多少秒以内的视频or音频也可适用
                        //.recordVideoSecond()//录制视频秒数 默认60s
                        .forResult(2001);//结果回调onActivityResult code

        }

    };

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            // 进入相册 以下是例子：不需要的api可以不写
            int number = maxSelectNum;
            if (list!=null&&list.size()>0){
                number = maxSelectNum - list.size();
            }
            pictureSelector = PictureSelector.create(MenuJGDetailActivity.this);
            pictureSelector.openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .maxSelectNum(number)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE )// 多选 or 单选
                    .previewImage(true)// 是否可预览图片
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .enableCrop(false)// 是否裁剪
                    .compress(false)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //.compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .selectionMedia(selectList)// 是否传入已选图片
                    //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    //.rotateEnabled(true) // 裁剪是否可旋转图片
                    //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                    //.videoQuality()// 视频录制质量 0 or 1
                    //.videoSecond()//显示多少秒以内的视频or音频也可适用
                    //.recordVideoSecond()//录制视频秒数 默认60s
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

        }

    };


    public void initTitle() {
        ImageView back = findViewById(R.id.common_back);
        back.setOnClickListener(this);
        TextView textView = findViewById(R.id.common_title);
        if (id != 0) {
            textView.setText("机构详情");
            TextView del = findViewById(R.id.common_title_right);
            del.setVisibility(View.VISIBLE);
            del.setOnClickListener(this);
        }else{
            textView.setText("机构认证");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.common_title_right:
                delete();
                break;

            case R.id.jigou_detail_save:
                commit();
                break;

            case R.id.jigou_detail_logo:
                showPopupWindow();
                break;
        }

    }

    public void requestDetail() {
//        progressBar.setVisibility(View.VISIBLE);
        progressDialog.show();
        HashMap<String,String>map = new HashMap<>();
        map.put("id", id+"");
//        map.put("uid",UserManager.getUser(this).getId()+"");
        NetRequest.postFormRequest(Constant.MENU_JIGOU_INFO_URL, map, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.optInt("code")==1){
                    model = Utils.decodeJSON(jsonObject.optString("data"),MenuJGModel.class);
                    updateInfo();
                }else{
                    ToastHelper.showToast(jsonObject.optString("msg"));
                }
                progressDialog.dismiss();
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastHelper.showToast(R.string.request_failed);
                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
            }
        });
    }

    public void updateInfo(){
        if (model!=null){
            company.setText(model.getJgname());
            name.setText(model.getJgxm());
            phone.setText(model.getJgtel());
            location_edit.setText(model.getAddress());
            product_edit.setText(model.getServices());
            info_edit.setText(model.getIntro());
            if (!TextUtils.isEmpty(model.getJglogo())){
                simpleDraweeView.setImageURI(Uri.parse(Constant.BASE_URL+model.getJglogo()));
            }
            list.clear();

            if (model.getImages()!=null&&model.getImages().size()>0){
                list.addAll(model.getImages());
                adapter.notifyDataSetChanged();
            }
        }
    }


    public void commit() {
//        progressBar.setVisibility(View.VISIBLE);
        progressDialog.show();
//        ProgressDialog.show(this,"","请稍等");
        if (TextUtils.isEmpty(company.getText().toString())) {
            progressBar.setVisibility(View.GONE);
            progressDialog.dismiss();
            ToastHelper.showToast("公司名称不能为空");
            return;
        }
        if (TextUtils.isEmpty(name.getText().toString())) {
            progressBar.setVisibility(View.GONE);
            progressDialog.dismiss();
            ToastHelper.showToast("姓名不能为空");
            return;
        }
        if (TextUtils.isEmpty(phone.getText().toString())) {
            progressBar.setVisibility(View.GONE);
            progressDialog.dismiss();
            ToastHelper.showToast("电话不能为空");
            return;
        }
        if (list2==null||list2.size()==0){
            ToastHelper.showToast("请选择营业执照");
            return;
        }

        if (id!=0){
            HashMap<String,String>map = new HashMap<>();
            map.put("jgname",company.getText().toString());
            map.put("jgxm",name.getText().toString());
            map.put("jgtel",phone.getText().toString());
            map.put("address",location_edit.getText().toString());
            map.put("services",product_edit.getText().toString());
            map.put("intro",info_edit.getText().toString());
            map.put("uid", com.dazhi.renzhengtong.user.UserManager.getUser(this).getId()+"");
            map.put("id", id+"");
//            NetRequest.postFormRequest(Constant.MENU_JIGOU_UPDATE_URL, map, new NetRequest.DataCallBack() {
//                @Override
//                public void requestSuccess(String result) throws Exception {
//                    JSONObject jsonObject = new JSONObject(result);
//                    if (jsonObject.optInt("code")==1){
//                        ToastHelper.showToast("修改成功");
//                        progressBar.setVisibility(View.GONE);
//                        finish();
//                    }else{
//                        ToastHelper.showToast(jsonObject.optString("msg"));
//                        progressBar.setVisibility(View.GONE);
//                    }
//                }
//
//                @Override
//                public void requestFailure(Request request, IOException e) {
//                    ToastHelper.showToast(R.string.request_failed);
//                    progressBar.setVisibility(View.GONE);
//                }
//            });
            List<File>files = new ArrayList<>();
            List<String>keys = new ArrayList<>();
            File file = null;
            if (select_image != null) {
                file = new File(PATH);
                keys.add("jglogo");
                files.add(file);
            }
            for (int i =0;i<list.size();i++) {
                if(!list.get(i).startsWith("uploads")){
                    keys.add("logo"+(i+1));
                    file = new File(list.get(i));
                    files.add(file);
                }else{
                    map.put("logo"+(i+1),list.get(i));
                }
            }

            for (int i =0;i<list2.size();i++) {
                if(!list.get(i).startsWith("uploads")){
                    keys.add("yyzz");
                    file = new File(list2.get(i));
                    files.add(file);
                }else{
                    map.put("yyzz",list2.get(i));
                }
            }

//            NetRequest.postFile(Constant.MENU_JIGOU_UPDATE_URL, map, "jglogo", file, new NetRequest.DataCallBack() {
//
//                @Override
//                public void requestSuccess(String result) throws Exception {
//                    JSONObject jsonObject = new JSONObject(result);
//                    if (jsonObject.optInt("code")==1){
//                        ToastHelper.showToast("修改成功");
//                        progressBar.setVisibility(View.GONE);
//                        finish();
//                    }else{
//                        ToastHelper.showToast(jsonObject.optString("msg"));
//                        progressBar.setVisibility(View.GONE);
//                    }
//                }
//
//                @Override
//                public void requestFailure(Request request, IOException e) {
//                    ToastHelper.showToast(R.string.request_failed);
//                    progressBar.setVisibility(View.GONE);
//                }
//            });

            NetRequest.postFiles(Constant.MENU_JIGOU_UPDATE_URL, map, keys, files, new NetRequest.DataCallBack() {

                @Override
                public void requestSuccess(String result) throws Exception {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.optInt("code")==1){
                        ToastHelper.showToast("修改成功");
                        progressBar.setVisibility(View.GONE);
                        progressDialog.dismiss();
                        finish();
                    }else{
                        ToastHelper.showToast(jsonObject.optString("msg"));
                        progressBar.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void requestFailure(Request request, IOException e) {
                    ToastHelper.showToast(R.string.request_failed);
                    progressBar.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            });
        }else{
            HashMap<String,String>map = new HashMap<>();
            map.put("jgname",company.getText().toString());
            map.put("jgxm",name.getText().toString());
            map.put("jgtel",phone.getText().toString());
            map.put("uid", com.dazhi.renzhengtong.user.UserManager.getUser(this).getId()+"");
            map.put("address",location_edit.getText().toString());
            map.put("services",product_edit.getText().toString());
            map.put("intro",info_edit.getText().toString());

            List<File>files = new ArrayList<>();
            List<String>keys = new ArrayList<>();
            File file = null;
            if (select_image != null) {
                file = new File(PATH);
                keys.add("logo");
                files.add(file);
            }
            for (int i =0;i<list.size();i++) {
                if(!list.get(i).startsWith("uploads")){
                    keys.add("logo"+(i+1));
                    file = new File(list.get(i));
                    files.add(file);
                }else{
                    map.put("logo"+(i+1),list.get(i));
                }
            }

            for (int i =0;i<list2.size();i++) {
                if(!list.get(i).startsWith("uploads")){
                    keys.add("yyzz");
                    file = new File(list2.get(i));
                    files.add(file);
                }else{
                    map.put("yyzz",list2.get(i));
                }
            }
            NetRequest.postFiles(Constant.USRE_JGRZ_URL, map, keys, files, new NetRequest.DataCallBack() {

                @Override
                public void requestSuccess(String result) throws Exception {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.optInt("code")==1){
                        ToastHelper.showToast("认证成功");
                        progressBar.setVisibility(View.GONE);
                        progressDialog.dismiss();
                        setResult(2000);
                        finish();
                    }else{
                        ToastHelper.showToast(jsonObject.optString("msg"));
                        progressBar.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void requestFailure(Request request, IOException e) {
                    ToastHelper.showToast(R.string.request_failed);
                    progressBar.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            });
        }

    }



    public void delete() {
//        progressBar.setVisibility(View.VISIBLE);
        progressDialog.show();
        HashMap<String,String>map = new HashMap<>();
        map.put("uid", com.dazhi.renzhengtong.user.UserManager.getUser(this).getId()+"");
        map.put("id", id+"");
        NetRequest.postFormRequest(Constant.MENU_JIGOU_DELETE_URL, map, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.optInt("code")==1){
                    ToastHelper.showToast("删除成功");
                    progressBar.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    setResult(2000);
                    finish();
                }else{
                    ToastHelper.showToast(jsonObject.optString("msg"));
                    progressBar.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                ToastHelper.showToast(R.string.request_failed);
                progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
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
            select_image = bitmap;
            setFile(select_image);
            simpleDraweeView.setImageBitmap(bitmap);
        }

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                        list.add(media.getPath());
                    }
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
                    break;
                case 2001:
                    // 图片选择结果回调
                    selectList2 = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList2) {
                        Log.i("图片-----》", media.getPath());
                        list2.add(media.getPath());
                    }
                    adapter2.setList(list2);
                    adapter2.notifyDataSetChanged();
                    break;
            }
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

    private void setFile(Bitmap photo) {
        File file = new File(PATH);
        try {
            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(file));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bout);
            bout.flush();
            bout.close();
        } catch (Exception e) {
            e.printStackTrace();
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

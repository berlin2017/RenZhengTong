package com.dazhi.renzhengtong.evaluation;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dazhi.renzhengtong.R;
import com.dazhi.renzhengtong.evaluation.adapter.OptionsAdapter;
import com.dazhi.renzhengtong.evaluation.model.OptionItem;
import com.dazhi.renzhengtong.evaluation.model.TestModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/2/1.
 */

public class TestActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<TestModel> list = new ArrayList<>();
    private TestAdapter testAdapter;
    public static final float MAX_SCALE = 1.2f;
    public static final float MIN_SCALE = 0.6f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        viewPager = findViewById(R.id.test_viewpager);
        final TestModel testModel = new TestModel();
        testModel.setTitle("设置view的背景颜色，有两种方法，一种是通过代码写的形式，一种是通过写一个xml的形式先说第一种，用代码实现view的背景渐变");
        List<OptionItem>items = new ArrayList<>();
        OptionItem item = new OptionItem();
        item.setText("是");
        OptionItem item2 = new OptionItem();
        item2.setText("否");
        items.add(item);
        items.add(item2);
        testModel.setList(items);
        list.add(testModel);
        list.add(testModel);
        list.add(testModel);
        list.add(testModel);
        testAdapter = new TestAdapter(this,list);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setPageMargin(40);
        viewPager.setAdapter(testAdapter);

        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                if (position < -1) {
                    position = -1;
                } else if (position > 1) {
                    position = 1;
                }

                float tempScale = position < 0 ? 1 + position : 1 - position;

                float slope = (MAX_SCALE - MIN_SCALE) / 1;
                //一个公式
                float scaleValue = MIN_SCALE + tempScale * slope;
                page.setScaleX(scaleValue);
                page.setScaleY(scaleValue);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    page.getParent().requestLayout();
                }
            }
        });

    }

    public class TestAdapter extends PagerAdapter {
        private Context context;
        private List<TestModel> list;

        public TestAdapter(Context context, List<TestModel> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_test_layout,container,false);
            RecyclerView recyclerView = view.findViewById(R.id.item_test_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(TestActivity.this));
            OptionsAdapter adapter = new OptionsAdapter(R.layout.item_test_options,list.get(position));
            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position2) {
                    if (view.getId() == R.id.item_options_radiobutton){
                        TestModel model = list.get(position);
                        model.setSelectPosition(position2);
                        list.remove(position);
                        list.add(position,model);
                        testAdapter.notifyDataSetChanged();
                        adapter.notifyDataSetChanged();
                        if (position<list.size()-1){
                            viewPager.setCurrentItem(position+1);
                        }
                    }
                }
            });
            recyclerView.setAdapter(adapter);
            TextView textView = view.findViewById(R.id.item_test_title);
            textView.setText(position+1+"."+list.get(position).getTitle());
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        }
    }

}

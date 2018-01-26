package com.dazhi.renzhengtong.search;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dazhi.renzhengtong.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by mac on 2018/1/25.
 */

public class SearchHomeFragment extends Fragment{
    private SimpleDraweeView first;
    private SimpleDraweeView two;
    private SimpleDraweeView three;
    private TextView title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_search_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.common_title);
        title.setText("查询");

        first = view.findViewById(R.id.search_home_first);
        two = view.findViewById(R.id.search_home_two);
        three = view.findViewById(R.id.search_home_three);

        first.setImageURI(Uri.parse("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2054592640,401359956&fm=58"));
        two.setImageURI(Uri.parse("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2054592640,401359956&fm=58"));
        three.setImageURI(Uri.parse("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2054592640,401359956&fm=58"));


    }
}

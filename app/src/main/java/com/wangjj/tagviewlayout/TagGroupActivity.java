package com.wangjj.tagviewlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wangjj.library.widget.FlowLayout;
import com.wangjj.library.widget.TagAdapter;
import com.wangjj.library.widget.TagViewLayout;

import java.util.ArrayList;
import java.util.List;

public class TagGroupActivity extends AppCompatActivity {
    private TagViewLayout mTagViewLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_group);
        mTagViewLayout = (TagViewLayout) findViewById(R.id.tag_view_layout);
        List<String> stringList = new ArrayList<>();
        stringList.add("聪明");
        stringList.add("善良的");
        stringList.add("可爱的人");
        stringList.add("帅气到迷人");
        stringList.add("一定要帅哦");
        stringList.add("一定要帅哦");
        stringList.add("一定要帅哦");
        stringList.add("一定要帅哦");
        stringList.add("一定要帅哦");
        mTagViewLayout.setTagAdapter(new TagAdapter(stringList) {
            @Override
            public View getView(int position, FlowLayout parent, Object o) {
//                View view = View.inflate(TagGroupActivity.this, R.layout.item_layout_tag_group, null);
                View view = TagGroupActivity.this.getLayoutInflater().inflate(R.layout.item_layout_tag_group, parent, false);
                TextView tv_des = (TextView) view.findViewById(R.id.tv_des);
                String s = (String) o;
                tv_des.setText(s);
                return view;
            }
        });
        mTagViewLayout.setOnTagClickListener(new TagViewLayout.onTagClickListener() {
            @Override
            public <T> void onTagClick(int position, T t) {
                String str = (String) t;
                Toast.makeText(TagGroupActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });



    }
}

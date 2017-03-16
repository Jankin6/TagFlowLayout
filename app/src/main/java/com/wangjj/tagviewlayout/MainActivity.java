package com.wangjj.tagviewlayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mBtnFlowlayout;
    private Button mBtnTaglayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnFlowlayout = (Button) findViewById(R.id.btn_flow_layout);
        mBtnTaglayout = (Button) findViewById(R.id.btn_tag_layout);
        mBtnFlowlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FlowLayoutActivity.class));
            }
        });

        mBtnTaglayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TagGroupActivity.class));
            }
        });
    }
}

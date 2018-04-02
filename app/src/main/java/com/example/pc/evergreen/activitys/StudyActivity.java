package com.example.pc.evergreen.activitys;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.pc.evergreen.R;
import com.example.pc.evergreen.adapters.TabAdapterDetails;
import com.example.pc.evergreen.fragments.StudyFragment1;
import com.example.pc.evergreen.fragments.StudyFragment2;
import com.example.pc.evergreen.fragments.StudyFragment3;
import com.example.pc.evergreen.fragments.StudyFragment4;

import java.util.ArrayList;
import java.util.List;

public class StudyActivity extends AppCompatActivity implements View.OnClickListener{
    public  String[] tabTitle_study = new String[]{"课程详情", "课程安排","教师简介","报名"};
    private TabLayout tabLayout_study;
    private TabAdapterDetails adapter;
    private ViewPager viewPager_study;
    private TextView title;//活动项目标题
    private String itemTitleStr;//活动标题
    private String Tag;//项目类型标签
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        initView();
    }

    private void initView() {
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        title = findViewById(R.id.title);
        itemTitleStr = getIntent().getStringExtra("ITEM_NAME");
        Tag = getIntent().getStringExtra("TAG");
        System.out.println("--------tag--------"+Tag);
        title.setText(itemTitleStr);
        viewPager_study = (ViewPager) findViewById(R.id.viewPager_study);
        //tabLayout 与viewPager配合使用
        tabLayout_study = (TabLayout) findViewById(R.id.table_layout_study);
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(new StudyFragment1());
        fragments.add(new StudyFragment2());
        fragments.add(new StudyFragment3());
        fragments.add(new StudyFragment4());

        //设置tabLayouot可以滑动
        tabLayout_study.setTabMode(TabLayout.MODE_SCROLLABLE);
        //ViewPager适配器赋值
        adapter = new TabAdapterDetails(getSupportFragmentManager(), fragments, tabTitle_study);

        //设置可以滑动
        tabLayout_study.setTabMode(TabLayout.MODE_FIXED);
        //给ViewPager设置适配器
        viewPager_study.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来。
        tabLayout_study.setupWithViewPager(viewPager_study);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                onBackPressed();
        }
    }
}

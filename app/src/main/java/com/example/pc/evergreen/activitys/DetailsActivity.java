package com.example.pc.evergreen.activitys;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.pc.evergreen.R;
import com.example.pc.evergreen.adapters.TabAdapterDetails;
import com.example.pc.evergreen.fragments.DetailsFragment1;
import com.example.pc.evergreen.fragments.DetailsFragment2;
import com.example.pc.evergreen.fragments.DetailsFragment3;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{
    public  String[] tabTitle_details = new String[]{"活动详情", "预约列表","预约活动"};
    private TabLayout tabLayout_details;
    private TabAdapterDetails adapter;
    private ViewPager viewPager_details;
    private TextView title;//活动项目标题
    private String itemTitleStr;//活动标题
    private String Tag;//项目类型标签
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
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
        viewPager_details = (ViewPager) findViewById(R.id.viewPager_details);
        //tabLayout 与viewPager配合使用
        tabLayout_details = (TabLayout) findViewById(R.id.table_layout_details);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new DetailsFragment1());
        fragments.add(new DetailsFragment2());
        fragments.add(new DetailsFragment3());

        //设置tabLayouot可以滑动
        tabLayout_details.setTabMode(TabLayout.MODE_SCROLLABLE);
        //ViewPager适配器赋值
        adapter = new TabAdapterDetails(getSupportFragmentManager(), fragments, tabTitle_details);

        //设置可以滑动
        tabLayout_details.setTabMode(TabLayout.MODE_FIXED);
        //给ViewPager设置适配器
        viewPager_details.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来。
        tabLayout_details.setupWithViewPager(viewPager_details);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                onBackPressed();
        }
    }
}

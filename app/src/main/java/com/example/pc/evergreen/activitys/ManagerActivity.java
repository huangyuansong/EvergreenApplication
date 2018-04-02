package com.example.pc.evergreen.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pc.evergreen.R;
import com.example.pc.evergreen.adapters.ManagerAdapeter;
import com.example.pc.evergreen.fragments.EditorFragment;
import com.example.pc.evergreen.utils.DummyContentManager;


import java.util.ArrayList;
import java.util.List;

public class ManagerActivity extends AppCompatActivity implements View.OnClickListener,EditorFragment.OnListFragmentInteractionListener {
    private TextView title;
    private ImageButton back;


    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;

    private ViewPager mViewPagerManager;// 用来放置界面切换
    private TabLayout mTabLayoutManager;
    private ManagerAdapeter mPagerAdapter;// 初始化View适配器

    public  String[] tabTitleManager = new String[]{"资料编辑", "通知管理","积分管理","数据统计"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        initView();
        initEvents();
    }
    private void initView() {
        title = findViewById(R.id.title);
        title.setText("管理界面");
        back = findViewById(R.id.back);
        back.setOnClickListener(ManagerActivity.this);
        mViewPagerManager = findViewById(R.id.manager_viewPager);
        //tabLayout 与viewPager配合使用
        mTabLayoutManager = findViewById(R.id.table_layout_manager);
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            fragments.add(EditorFragment.newInstance(i + 1));
        }

        //设置tabLayouot可以滑动
        mTabLayoutManager.setTabMode(TabLayout.MODE_FIXED);
        //ViewPager适配器赋值
        mPagerAdapter = new ManagerAdapeter(getSupportFragmentManager(), fragments, tabTitleManager);
        //给ViewPager设置适配器
        mViewPagerManager.setAdapter(mPagerAdapter);
        //将TabLayout和ViewPager关联起来。
        mTabLayoutManager.setupWithViewPager(mViewPagerManager);


        one = mTabLayoutManager.getTabAt(0);
        two = mTabLayoutManager.getTabAt(1);
        three = mTabLayoutManager.getTabAt(2);
        four = mTabLayoutManager.getTabAt(3);

        one.setIcon(getResources().getDrawable(R.drawable.button_data_pressed));
        two.setIcon(getResources().getDrawable(R.drawable.button_notice_normal));
        three.setIcon(getResources().getDrawable(R.drawable.button_place_normal));
        four.setIcon(getResources().getDrawable(R.drawable.button_statistical_normal));

    }

    private void initEvents() {

        mTabLayoutManager.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == mTabLayoutManager.getTabAt(0)) {
                    one.setIcon(getResources().getDrawable(R.drawable.button_data_pressed));
                    mTabLayoutManager.setTabTextColors(Color.parseColor("#939493"),Color.parseColor("#0db60a"));
                    mViewPagerManager.setCurrentItem(0);
                } else if (tab == mTabLayoutManager.getTabAt(1)) {
                    two.setIcon(getResources().getDrawable(R.drawable.button_notice_pressed));
                    mTabLayoutManager.setTabTextColors(Color.parseColor("#939493"),Color.parseColor("#0db60a"));
                    mViewPagerManager.setCurrentItem(1);
                } else if (tab == mTabLayoutManager.getTabAt(2)) {
                    three.setIcon(getResources().getDrawable(R.drawable.button_place_pressed));
                    mTabLayoutManager.setTabTextColors(Color.parseColor("#939493"),Color.parseColor("#0db60a"));
                    mViewPagerManager.setCurrentItem(2);
                } else if (tab == mTabLayoutManager.getTabAt(3)) {
                    four.setIcon(getResources().getDrawable(R.drawable.button_statistical_presse));
                    mTabLayoutManager.setTabTextColors(Color.parseColor("#939493"),Color.parseColor("#0db60a"));
                    mViewPagerManager.setCurrentItem(3);
                }

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == mTabLayoutManager.getTabAt(0)) {
                    one.setIcon(getResources().getDrawable(R.drawable.button_data_normal));
                    mTabLayoutManager.setTabTextColors(Color.parseColor("#939493"),Color.parseColor("#0db60a"));
                } else if (tab == mTabLayoutManager.getTabAt(1)) {
                    two.setIcon(getResources().getDrawable(R.drawable.button_notice_normal));
                    mTabLayoutManager.setTabTextColors(Color.parseColor("#939493"),Color.parseColor("#0db60a"));
                } else if (tab == mTabLayoutManager.getTabAt(2)) {
                    three.setIcon(getResources().getDrawable(R.drawable.button_place_normal));
                    mTabLayoutManager.setTabTextColors(Color.parseColor("#939493"),Color.parseColor("#0db60a"));
                } else if (tab == mTabLayoutManager.getTabAt(3)) {
                    four.setIcon(getResources().getDrawable(R.drawable.button_statistical_normal));
                    mTabLayoutManager.setTabTextColors(Color.parseColor("#939493"),Color.parseColor("#0db60a"));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        }
        ); }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.back:
                onBackPressed();
                break;

        }
    }

    @Override
    public void onListFragmentInteraction(DummyContentManager.DummyItemManager item) {
        switch (item.service_title){
            case "老干部资料":
                Intent intentUpdateCadrs = new Intent(ManagerActivity.this,UpdateCadresActivity.class);
                startActivity(intentUpdateCadrs);
                break;
            case "积分商城":
                Intent intentMallManager = new Intent(ManagerActivity.this,MallManagerActivity.class);
                startActivity(intentMallManager);
                break;
            case "积分兑换":
                Intent intentExchangeManager = new Intent(ManagerActivity.this,ExchangeManagerActivity.class);
                startActivity(intentExchangeManager);
                break;
    }

    }
}

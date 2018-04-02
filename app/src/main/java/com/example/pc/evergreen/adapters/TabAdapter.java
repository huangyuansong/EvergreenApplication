package com.example.pc.evergreen.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.pc.evergreen.fragments.PageFragment;

import java.util.List;


/**
 * Created by huangyuansong on 16/10/25.
 */
public class TabAdapter extends FragmentPagerAdapter {

    private List<PageFragment> mfragments;
    private String[] mTitles;

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    public TabAdapter(FragmentManager fm, List<PageFragment> fragments, String[] titles) {
        super(fm);
        mfragments = fragments;
        mTitles = titles;
    }


    @Override
    public Fragment getItem(int position) {
        return mfragments.get(position);
    }

    @Override
    public int getCount() {
        return mfragments.size();
    }

    //设置tablayout标题
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];

    }
}

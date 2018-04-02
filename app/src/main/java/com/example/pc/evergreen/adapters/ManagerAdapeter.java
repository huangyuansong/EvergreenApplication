package com.example.pc.evergreen.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by pc on 2018/3/15.
 */

public class ManagerAdapeter extends FragmentPagerAdapter {

    private List<Fragment> mfragments;
    private String[] mTitles;

    public ManagerAdapeter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
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

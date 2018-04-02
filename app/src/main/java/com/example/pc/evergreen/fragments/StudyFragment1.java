package com.example.pc.evergreen.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pc.evergreen.R;

/**
 * 课程详情
 */
public class StudyFragment1 extends Fragment {
    public StudyFragment1() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study1, container, false);


        return view;
    }
}

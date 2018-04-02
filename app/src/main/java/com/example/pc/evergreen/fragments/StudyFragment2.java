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
 * 课程安排
 */
public class StudyFragment2 extends Fragment {
    public StudyFragment2() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study2, container, false);


        return view;
    }
}

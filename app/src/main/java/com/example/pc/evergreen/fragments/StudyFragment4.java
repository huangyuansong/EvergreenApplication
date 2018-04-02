package com.example.pc.evergreen.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pc.evergreen.R;
import com.example.pc.evergreen.dialog.MessageDialog;

/**
 * 报名
 */
public class StudyFragment4 extends Fragment implements View.OnClickListener {

    private Button subscribe;



    public StudyFragment4() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study4, container, false);
        subscribe = view.findViewById(R.id.subscribe);
        subscribe.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.subscribe:
                final MessageDialog dialog = new MessageDialog(getContext(), "暂未开放！");
        }
    }
}

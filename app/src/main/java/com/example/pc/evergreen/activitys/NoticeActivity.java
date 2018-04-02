package com.example.pc.evergreen.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pc.evergreen.R;

public class NoticeActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView title;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        initView();
    }





    private void initView() {
        title = findViewById(R.id.title);
        title.setText("通知中心");
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}

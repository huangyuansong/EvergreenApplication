package com.example.pc.evergreen.activitys;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.evergreen.R;
import com.example.pc.evergreen.beans.User;
import com.example.pc.evergreen.beans.UserData;
import com.example.pc.evergreen.utils.VolleyLoadPicture;

//老干部个人信息界面
public class PersonalInformationActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton back;
    private TextView title;
    private ImageView face_image_information;
    private TextView tv_name_information;
    private TextView tv_account_information;
    private TextView tv_gender_information;
    private TextView tv_unit_information;
    private TextView tv_position_information;
    private TextView tv_level_information;
    private TextView tv_birth_information;
    private TextView tv_retired_information;
    private TextView tv_retired_time;
    private TextView tv_home_address_information;
    private TextView tv_linkman_information;
    private TextView tv_relation_information;
    private TextView tv_linkman_phone_information;
    private UserData userInformation = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        initData();
        initView();
    }

    private void initData() {
        userInformation  = (UserData) getIntent().getSerializableExtra("userItem");
    }

    private void initView() {

        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        title = findViewById(R.id.title);
        title.setText("个人信息");
        face_image_information = findViewById(R.id.face_image_information);
        tv_name_information = findViewById(R.id.tv_name_information);
        tv_account_information = findViewById(R.id.tv_account_information);
        tv_gender_information = findViewById(R.id.tv_gender_information);
        tv_unit_information = findViewById(R.id.tv_unit_information);
        tv_position_information = findViewById(R.id.tv_position_information);
        tv_level_information = findViewById(R.id.tv_level_information);
        tv_birth_information = findViewById(R.id.tv_birth_information);
        tv_retired_information = findViewById(R.id.tv_retired_information);
        tv_retired_time = findViewById(R.id.tv_retired_time);
        tv_home_address_information = findViewById(R.id.tv_home_address_information);
        tv_linkman_information = findViewById(R.id.tv_linkman_information);
        tv_relation_information = findViewById(R.id.tv_relation_information);
        tv_linkman_phone_information = findViewById(R.id.tv_linkman_phone_information);

       // 通过Volley加载图片到头像控件
        VolleyLoadPicture vlp = new VolleyLoadPicture(PersonalInformationActivity.this, face_image_information);
        if (userInformation.getPhoto() != null) {
            vlp.getmImageLoader().get(userInformation.getPhoto(), vlp.getOne_listener());
        }else {
            face_image_information.setImageResource(R.mipmap.icon_head);
        }

        tv_name_information.setText(userInformation.getName());
        tv_account_information.setText(userInformation.getAccount());
        tv_gender_information.setText(userInformation.getGender());
        tv_unit_information.setText(userInformation.getUnit());
        tv_position_information.setText(userInformation.getDuty());
        tv_level_information.setText(userInformation.getGrade());
        tv_birth_information.setText(userInformation.getBirth());
        tv_retired_information.setText(userInformation.getTreatment());
        tv_retired_time.setText(userInformation.getTreatmentData());
        tv_home_address_information.setText(userInformation.getAddress());
        tv_linkman_information.setText(userInformation.getContact());
        tv_relation_information.setText(userInformation.getRelation());
        tv_linkman_phone_information.setText(userInformation.getContactPhone());

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}

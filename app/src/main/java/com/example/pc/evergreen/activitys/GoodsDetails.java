package com.example.pc.evergreen.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.evergreen.R;
import com.example.pc.evergreen.beans.Goods;
import com.example.pc.evergreen.dialog.MessageDialog;
import com.example.pc.evergreen.dialog.SelectPopupWindow;
import com.example.pc.evergreen.utils.PictureUtils;
import com.example.pc.evergreen.utils.VolleyLoadPicture;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

//修改商品
public class GoodsDetails extends SelectPopupWindow implements View.OnClickListener{
    private ImageButton back;
    private TextView title;
    private ImageView goods_image_details;
    private TextView et_goods_name_details;
    private TextView et_goods_model_details;
    private TextView et_goods_origin_details;
    private TextView et_goods_date_details;
    private TextView et_goods_integral_details;
    private Button bt_goods_details;
    private TextView et_goods_inventory_details;





    private String goodsName;
    private String goodsModel;
    private String goodsOrigin;
    private String goodsDate;
    private Goods goodsData;
    private String goodsIntegral;
    private String inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        goodsData = (Goods) getIntent().getSerializableExtra("goodsrItem");
        initView();
    }

    private void initView() {
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        title = findViewById(R.id.title);
        title.setText("修改商品");
        goods_image_details = findViewById(R.id.goods_image_details);
        et_goods_name_details = findViewById(R.id.et_goods_name_details);
        et_goods_model_details = findViewById(R.id.et_goods_model_details);
        et_goods_origin_details = findViewById(R.id.et_goods_origin_details);
        et_goods_date_details = findViewById(R.id.et_goods_date_details);
        et_goods_integral_details = findViewById(R.id.et_goods_integral_details);
        et_goods_inventory_details = findViewById(R.id.et_goods_inventory_details);
        bt_goods_details = findViewById(R.id.bt_goods_details);
        bt_goods_details.setOnClickListener(this);

        VolleyLoadPicture vlp = new VolleyLoadPicture(GoodsDetails.this, goods_image_details);
        if (goodsData.getGoodsImage() != null) {
            vlp.getmImageLoader().get(goodsData.getGoodsImage(), vlp.getOne_listener());
        }else {
            goods_image_details.setImageResource(R.mipmap.noimage);
        }

        et_goods_name_details.setText(goodsData.getGoodsName());
        et_goods_model_details.setText(goodsData.getGoodsModel());
        et_goods_origin_details.setText(goodsData.getGoodsOrigin());
        et_goods_date_details.setText(goodsData.getGoodsDate());
        et_goods_integral_details.setText(goodsData.getGoodsIntegral().toString());
        et_goods_inventory_details.setText(goodsData.getInventory().toString());




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.bt_goods_details:
             onBackPressed();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}

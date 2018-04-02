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
import com.example.pc.evergreen.beans.UserData;
import com.example.pc.evergreen.dialog.MessageDialog;
import com.example.pc.evergreen.dialog.SelectPopupWindow;
import com.example.pc.evergreen.utils.PictureUtils;
import com.example.pc.evergreen.utils.VolleyLoadPicture;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class ModifyGoods extends SelectPopupWindow implements View.OnClickListener{
    private ImageButton back;
    private TextView title;
    private ImageView goods_image_modify;
    private Button bt_goods_image_modify;
    private EditText et_goods_name_modify;
    private EditText et_goods_model_modify;
    private EditText et_goods_origin_modify;
    private EditText et_goods_date_modify;
    private EditText et_goods_integral_modify;
    private EditText et_goods_inventory_modify;
    private Button bt_goods_modify;
    private Button btn_goods_exet_modify;

    private Uri tempUri = null;//商品文件uri
    private Bitmap photo;
    private String imagePath;
    private boolean headImageTag = false;




    private String goodsImagePath;
    private String goodsName;
    private String goodsModel;
    private String goodsOrigin;
    private String goodsDate;
    private Goods goodsData;
    private String goodsIntegral;
    private Integer inventory;//库存


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_goods);
        goodsData = (Goods) getIntent().getSerializableExtra("goodsData");

        initView();
    }

    private void initView() {
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        title = findViewById(R.id.title);
        title.setText("修改商品");
        goods_image_modify = findViewById(R.id.goods_image_modify);
        et_goods_name_modify = findViewById(R.id.et_goods_name_modify);
        et_goods_model_modify = findViewById(R.id.et_goods_model_modify);
        et_goods_origin_modify = findViewById(R.id.et_goods_origin_modify);
        et_goods_date_modify = findViewById(R.id.et_goods_date_modify);
        et_goods_integral_modify = findViewById(R.id.et_goods_integral_modify);
        et_goods_inventory_modify = findViewById(R.id.et_goods_inventory_modify);

        bt_goods_image_modify = findViewById(R.id.bt_goods_image_modify);
        bt_goods_modify = findViewById(R.id.bt_goods_modify);
        btn_goods_exet_modify = findViewById(R.id.btn_goods_exet_modify);

        bt_goods_image_modify.setOnClickListener(this);
        bt_goods_modify.setOnClickListener(this);
        btn_goods_exet_modify.setOnClickListener(this);
        tempUri = Uri.fromFile(new File(String.valueOf(Environment.getExternalStorageDirectory()), "goods_image"  + ".png"));

        VolleyLoadPicture vlp = new VolleyLoadPicture(ModifyGoods.this, goods_image_modify);
        if (goodsData.getGoodsImage() != null) {
            vlp.getmImageLoader().get(goodsData.getGoodsImage(), vlp.getOne_listener());
        }else {
            goods_image_modify.setImageResource(R.mipmap.noimage);
        }

        et_goods_name_modify.setText(goodsData.getGoodsName());
        et_goods_model_modify.setText(goodsData.getGoodsModel());
        et_goods_origin_modify.setText(goodsData.getGoodsOrigin());
        et_goods_date_modify.setText(goodsData.getGoodsDate());
        et_goods_integral_modify.setText(goodsData.getGoodsIntegral().toString());
        et_goods_inventory_modify.setText(goodsData.getInventory().toString());



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.bt_goods_modify:
                goodsName = et_goods_name_modify.getText().toString();
                goodsModel = et_goods_model_modify.getText().toString();
                goodsOrigin = et_goods_origin_modify.getText().toString();
                goodsDate = et_goods_date_modify.getText().toString();
                goodsIntegral = et_goods_integral_modify.getText().toString();
                inventory = Integer.valueOf(et_goods_inventory_modify.getText().toString());
                if (TextUtils.isEmpty(goodsName)){
                    new MessageDialog(this,"商品名称不能为空！");
                }
                if (TextUtils.isEmpty(goodsIntegral)){
                    new MessageDialog(this,"兑换积分不能为空！");
                }
                final Goods goods = new Goods();
                goods.setGoodsName(goodsName);
                goods.setGoodsModel(goodsModel);
                goods.setGoodsOrigin(goodsOrigin);
                goods.setGoodsDate(goodsDate);
                goods.setInventory(inventory);

                goods.setGoodsIntegral(Integer.valueOf(goodsIntegral));
                if (imagePath !=null && headImageTag == true){
                    final BmobFile bmobFile = new BmobFile(new File(imagePath));
                    bmobFile.uploadblock(ModifyGoods.this, new UploadFileListener() {
                        @Override
                        public void onSuccess() {
                            String fileUrl = bmobFile.getFileUrl(ModifyGoods.this);
                            goods.setGoodsImage(fileUrl);
                            goods.update(ModifyGoods.this, goodsData.getObjectId(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    new MessageDialog(ModifyGoods.this,"商品修改成功！");
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    new MessageDialog(ModifyGoods.this,"商品修改失败！"+s);
                                }
                            });
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            new MessageDialog(ModifyGoods.this,"商品图片上传失败！错误代码是："+i);
                        }
                    });

                }else {
                    goods.update(ModifyGoods.this,goodsData.getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            new MessageDialog(ModifyGoods.this,"商品修改成功！");

                        }

                        @Override
                        public void onFailure(int i, String s) {
                            new MessageDialog(ModifyGoods.this,"商品修改失败！错误代码是："+i);

                        }
                    });
                }
                break;
            case R.id.btn_goods_exet_modify:
                onBackPressed();
                break;
            case R.id.bt_goods_image_modify://添加商品图片按钮
                showPopupWindow(this,null,tempUri,TAKE_PICTURE_HEAD);
                break;



        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //获取并裁剪图片并显示
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    startPhotoZoom(uri, 200, 200); // 开始对图片进行裁剪处理
                } else {
                }
                break;
            case TAKE_PICTURE_HEAD:
                if (resultCode == RESULT_OK) {
                    startPhotoZoom(tempUri, 200, 200); // 开始对图片进行裁剪处理
                } else {

                }
                break;
            case CROP_SMALL_PICTURE:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        photo = extras.getParcelable("data");
                        //  photo = PictureUtils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
                        goods_image_modify.setImageBitmap(photo);
                        imagePath = PictureUtils.savePhoto(photo, String.valueOf(Environment.getExternalStorageDirectory()), "goods_image");
                        headImageTag = true;
                    }
                }
                break;
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri,int outputX,int outputY) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }



}

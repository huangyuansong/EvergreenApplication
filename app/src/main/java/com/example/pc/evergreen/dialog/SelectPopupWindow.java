package com.example.pc.evergreen.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.pc.evergreen.R;

import java.io.File;
import java.net.URI;


/**
 * Created by huangyuansong on 2018/1/12.
 */
public abstract class SelectPopupWindow extends AppCompatActivity{

    public final int CHOOSE_PICTURE = 0;
    public final int TAKE_PICTURE_HEAD = 1;
    public final int TAKE_PICTURE_RANK = 2;
    public final int CROP_SMALL_PICTURE = 3;
    public final int CROP_SMALL_PICTURE_NO_TOROUND_BITMAT = 4;//不处理成圆形
    private   PopupWindow mPopupWindow;//自定义弹窗（图片添加途径选择窗口）
    private   LinearLayout ll_popup;
    private   RelativeLayout parent;




    public  void showPopupWindow(Context context, String account, final Uri imageUri, final int TAG){

        View mView = View.inflate(context, R.layout.popupwindows_select_image, null);
        mPopupWindow = new PopupWindow(mView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        Button mCamera = (Button) mView.findViewById(R.id.item_popupwindows_camera);
        Button mPhoto = (Button) mView.findViewById(R.id.item_popupwindows_Photo);
        Button mCancle = (Button) mView.findViewById(R.id.item_popupwindows_cancel);
        parent = (RelativeLayout) mView.findViewById(R.id.parent);
        ll_popup = (LinearLayout) mView.findViewById(R.id.ll_popup);

        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击拍照
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(openCameraIntent, TAG);
                mPopupWindow.dismiss();
                ll_popup.clearAnimation();
            }
        });
        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击取消
                mPopupWindow.dismiss();
                ll_popup.clearAnimation();
            }
        });
        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击从相册获取
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");//相片类型
                startActivityForResult(intent, CHOOSE_PICTURE);
                mPopupWindow.dismiss();
                ll_popup.clearAnimation();

            }
        });
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击提示框之外
                mPopupWindow.dismiss();
                ll_popup.clearAnimation();
            }
        });

        mPopupWindow.setContentView(mView);
        //点击事件
        mPopupWindow.setOutsideTouchable(true);
        //弹出窗体可点击
        mPopupWindow.setTouchable(true);
        mPopupWindow.setAnimationStyle(R.anim.activity_translate_in);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x5e000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        mPopupWindow.setBackgroundDrawable(dw);

        //显示PopupWindow
        View rootview = LayoutInflater.from(context).inflate(R.layout.activity_main, null);
        mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

    }
}

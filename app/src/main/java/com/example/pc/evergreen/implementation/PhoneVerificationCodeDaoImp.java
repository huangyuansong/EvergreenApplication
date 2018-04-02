package com.example.pc.evergreen.implementation;

import android.content.Context;
import android.view.View;


import com.example.pc.evergreen.dialog.MessageDialog;
import com.example.pc.evergreen.interfaces.PhoneVerificationCodeDAO;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;


/**
 * Created by huangyuansong on 2017/12/26.
 * 用Bmob获取手机验证码工具类
 */
public class PhoneVerificationCodeDaoImp implements PhoneVerificationCodeDAO {

    private String BMOB_APP_KEY="a56887b54d529e249d33bd1f96b444af";
    private Context context;
    private String phoneNumber;

    public PhoneVerificationCodeDaoImp(String phoneNumber, Context context) {
        this.phoneNumber = phoneNumber;
        this.context = context;
        Bmob.initialize(context,BMOB_APP_KEY);
    }
//获取验证码
    public void getBmobVerificationCode() {
        BmobSMS.requestSMSCode(context, phoneNumber, "阿凡提", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if(e==null){//验证码发送成功
                    final MessageDialog messageDialog = new MessageDialog(context,"验证码发送成功，请留意手机短信。");
                    messageDialog.button_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            messageDialog.finish();
                        }
                    });
                }
            }
        });
    }




}

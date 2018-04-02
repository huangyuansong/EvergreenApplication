package com.example.pc.evergreen.interfaces;

import android.content.Context;

/**
 * Created by huangyuansong on 2017/12/26.
 * 获取手机验证码接口
 */
public interface PhoneVerificationCodeDAO {
    //通过Bmob获取手机验证码
     void  getBmobVerificationCode();
}
    //通过其它渠道获取手机验证码

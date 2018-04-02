package com.example.pc.evergreen.interfaces;

import android.content.Context;

import com.example.pc.evergreen.beans.User;
import com.example.pc.evergreen.beans.UserData;

import java.util.List;

import cn.bmob.v3.BmobObject;


/**
 * Created by huangyuansong on 2017/12/31.
 * 用户操作接口
 */
public interface AccountDAO {
    //登录
    void login(Context context, String account, String password);
    //重置密码
    void resetPassword(String phone, Context context, String code, String passwordone, String passwordtwo);
    //注册
    void register(Context context, String phoneNumber, String password_one, String password_two, String code);
    //新增老干部信息
    void addCadres(Context context,UserData userData);
    //修改
    void update(Context context, UserData object, String tag,String objectId);
    //查询单个用户

    /**
     *
     * @param field 字段名
     * @param value 字段值
     * @return
     */

    void select(Context context, String field, String value);

    //查询多个用户
    List<User> selects(Context context, String field, String value);
}

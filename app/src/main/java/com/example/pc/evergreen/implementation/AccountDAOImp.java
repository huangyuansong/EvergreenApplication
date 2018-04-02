package com.example.pc.evergreen.implementation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.example.pc.evergreen.MainActivity;
import com.example.pc.evergreen.activitys.AddCadresActivity;
import com.example.pc.evergreen.activitys.LoginActivity;
import com.example.pc.evergreen.activitys.PersonalDataActivity;
import com.example.pc.evergreen.activitys.RegisterActivity;
import com.example.pc.evergreen.beans.User;
import com.example.pc.evergreen.beans.UserData;
import com.example.pc.evergreen.dialog.MessageDialog;
import com.example.pc.evergreen.dialog.PromptMessageDialog;
import com.example.pc.evergreen.interfaces.AccountDAO;
import com.example.pc.evergreen.utils.DateUtils;
import com.example.pc.evergreen.utils.SaveUserInformation;

import java.util.List;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.ResetPasswordByCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static android.content.Context.RECEIVER_VISIBLE_TO_INSTANT_APPS;
import static com.example.pc.evergreen.activitys.PersonalDataActivity.updatedTime;

/**
 * Created by huangyuansong on 2017/12/31.
 * 通过Bmob实现注册、登录、重置密码、更新、查询
 */
public class AccountDAOImp implements AccountDAO {

    private static final String BMOB_APP_KEY="a56887b54d529e249d33bd1f96b444af";
    private User user;
    private UserData userData;
    public AccountDAOImp(){
    };

    //登录
    @Override
    public void login(final Context context, final String account, final String password) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("正在登录中...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        Bmob.initialize(context,BMOB_APP_KEY);
        BmobUser.loginByAccount(context, account, password, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (user!=null){
                    Intent mainItent = new Intent(context, MainActivity.class);
                    context.startActivity(mainItent);
                    progress.dismiss();
                    //保存用户名密码
                    SaveUserInformation.saveLoginInfo(context,account,password);
                    if (!LoginActivity.log_checkBox.isChecked()==true){
                        MainActivity.checkBox = false;
                    }
                }else {
                    MessageDialog messageDialog = new MessageDialog(context,"账号密码不正确！");
                    progress.dismiss();

                }
            }
        });
    }
    //重置密码
    public void resetPassword(String phoneNumber,final Context context, String code, final String passwordone,String passwordtwo){
        if (TextUtils.isEmpty(phoneNumber)) {
            //用户名不能为空
            new MessageDialog(context,"用户名不能为空！");
            return;
        }
        if (TextUtils.isEmpty(passwordone)) {
            //密码不能为空
            new MessageDialog(context,"密码不能为空！");
            return;
        }
        if (!passwordone.equals(passwordtwo)) {
            //两次密码不一样
            new MessageDialog(context,"两次密码不一致！");
            return;
        }
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("正在提交信息...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        BmobUser.resetPasswordBySMSCode(context, code, passwordone, new ResetPasswordByCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e ==null){
                    final MessageDialog messageDialog = new MessageDialog(context,"重置密码成功,请重新登录！");
                    messageDialog.button_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context,LoginActivity.class);
                            context.startActivity(intent);
                            progress.dismiss();
                            progress.dismiss();
                        }
                    });

                }else {
                    final MessageDialog messageDialog = new MessageDialog(context,"请认真填写相关信息！");
                    messageDialog.button_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progress.dismiss();
                            messageDialog.finish();
                        }
                    });

                }

            }
        });
    }
    //注册
    @Override
    public void register(final Context context, final String phoneNumber, final String password_one, String password_two, String code) {
        if (TextUtils.isEmpty(phoneNumber)) {
            //用户名不能为空
            new MessageDialog(context,"用户名不能为空！");
            return;
        }
        if (TextUtils.isEmpty(password_one)) {
            //密码不能为空
            new MessageDialog(context,"密码不能为空！");
            return;
        }
        if (!password_one.equals(password_two)) {
            //两次密码不一样
            new MessageDialog(context,"两次密码不一致！");
            return;
        }
        user = new User();
        user.setMobilePhoneNumber(phoneNumber);
              user.setPassword(password_one);
              user.setPermissions("members");
        user.signOrLogin(context, code, new SaveListener() {
            @Override
            public void onSuccess() {
                //将默认数据写到UserData表中
                System.out.println("user.getobjectiId="+user.getObjectId()+"-----------------------------");
                userData = new UserData();
                userData.setPermissions("members");
                userData.setIntegral(2);
                userData.setPhone(user.getMobilePhoneNumber());
                userData.setName(user.getUsername());
                userData.setAccount(user.getMobilePhoneNumber());
                userData.setGrade("请选择级别");
                userData.save(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        final PromptMessageDialog dialog = new PromptMessageDialog(context,"注册成功，积分增加5分请及时完善您的个人资料，");
                        dialog.button_yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context,PersonalDataActivity.class);
                                context.startActivity(intent);
                                dialog.close();
                            }
                        });
                        dialog.button_no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context,MainActivity.class);
                                context.startActivity(intent);
                                dialog.close();
                            }
                        });
                        //保存用户名密码
                        if(RegisterActivity.checkBox_register.isChecked()){
                            SaveUserInformation.saveLoginInfo(context,phoneNumber,password_one);
                        }
                    }

                    @Override
                    public void onFailure(int i, String s) {
                    }
                });


            }

            @Override
            public void onFailure(int i, String s) {
                new MessageDialog(context,"验证码不正确！");
            }
        });
    }

    @Override
    public void addCadres(final Context context, final UserData userData) {
        final String[] userObjectId = new String[1];

        //首先添加用户注册数据（User表注册），再将User表中objectId更新到userData表中
        //1、注册账号：
        final User user = new User();
        String mobilePhone = userData.getPhone();
        user.setMobilePhoneNumber(mobilePhone);
        user.setMobilePhoneNumberVerified(true);
        user.setUsername(userData.getName());
        user.setPermissions("members");
        user.setPassword("000000");
        user.signUp(context, new SaveListener() {
            @Override
            public void onSuccess() {
                userData.save(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        new MessageDialog(context,"添加成功！");

                    }

                    @Override
                    public void onFailure(int i, String s) {
                        new MessageDialog(context,"添加错误！错误代码为："+i);

                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                new MessageDialog(context,"操作失败，错误代码为："+i);

            }
        });


    }



    //更新数据
    @Override
    public void update(final Context context, UserData userData, String tag,String objectId) {


       //如果上次更新时间和本次更新时间相差大于一周，则加10积分


        if ("members".equals(tag)) {
            long times = DateUtils.timeCalculation(updatedTime);
            if (times>60*24*7){
                userData.increment("integral",10); // 分数递增10
            }
            BmobUser user1 = BmobUser.getCurrentUser(context);
            userData.update(context, objectId, new UpdateListener() {
                @Override
                public void onSuccess() {
                    final MessageDialog dialog = new MessageDialog(context, "资料上传成功！");
                    dialog.button_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                            dialog.finish();
                        }
                    });

                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
           return;
        }else if ("manager".equals(tag)){


            System.out.println("进入管理员修改方法－－－－－－");
            BmobUser user1 = BmobUser.getCurrentUser(context);

            user.update(context, user1.getObjectId(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    final MessageDialog dialog = new MessageDialog(context, "资料修改成功！");
                    dialog.button_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                            dialog.finish();
                        }
                    });

                }

                @Override
                public void onFailure(int i, String s) {
                    System.out.println("错误－－－－－－："+i+"-----------"+s);

                }
            });
            return;
        }else {
            final MessageDialog dialog = new MessageDialog(context, "非法操作！");
                ;
        };
        return;
    }

    //查询单个
    @Override
    public void select(final Context context, String field, String value ) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo(field, value);
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                for (User user1 : list){
                    user1 = list.get(0);
                    user = user1;
                }

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    //查询多个用户
    @Override
    public List<User> selects(Context context, String field, String value) {

        final List<User> userList = null;

        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
        query.addWhereEqualTo(field, value);
        query.findObjects(context, new FindListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> list) {
                for (int i = 0;i<list.size()+1;i++){
                    userList.add((User) list.get(i));
                }

            }

            @Override
            public void onError(int i, String s) {

            }
        });
        return userList;
    }


}

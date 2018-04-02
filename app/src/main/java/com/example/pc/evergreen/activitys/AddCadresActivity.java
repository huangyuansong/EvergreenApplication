package com.example.pc.evergreen.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pc.evergreen.MainActivity;
import com.example.pc.evergreen.R;
import com.example.pc.evergreen.beans.User;
import com.example.pc.evergreen.beans.UserData;
import com.example.pc.evergreen.dialog.MessageDialog;
import com.example.pc.evergreen.dialog.SelectPopupWindow;
import com.example.pc.evergreen.implementation.AccountDAOImp;
import com.example.pc.evergreen.implementation.FileDaoImp;
import com.example.pc.evergreen.interfaces.AccountDAO;
import com.example.pc.evergreen.utils.DateUtils;
import com.example.pc.evergreen.utils.PhoneFormatCheckUtils;
import com.example.pc.evergreen.utils.PictureUtils;

import java.io.File;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class AddCadresActivity extends SelectPopupWindow implements View.OnClickListener {

private ImageButton back;//返回
private TextView title;//界面标题
private ImageView face_image;//免冠照
private Button bt_set_head;//上传免冠照按钮
private EditText et_personal_account;//账号
private EditText et_name;//姓名输入控件
private RadioGroup et_rgSex;//性别控件
private EditText et_unit;//单位输入控件
private EditText et_position;//职务输入控件
private Spinner spinner_level;//级别输入控件
private EditText et_birth;//出生日期
private RadioGroup et_retired;//离/退休
private EditText et_retired_time;//离退休时间控件
private EditText et_home_address;//家庭住址控件
private EditText et_linkman;//联系人姓名输入控件
private RadioGroup et_relation;//关系选择控件
private EditText et_linkman_phone;//联系人电话
private Button btn_register;//提交按钮
private Button btn_register_exet;//取消退出按钮
private User user;
private RadioButton male;
private RadioButton female;
private RadioButton retired1;
private RadioButton retired2;
private RadioButton spouse;
private RadioButton children;
private RadioButton escort;
private EditText et_phone_add;

private String phone;
private String filePath = null;
private String name;
private String gender;
private String birth;
private String unit;
private String duty;
private String treatment;
private String treatmentData;
private String grade;
private String address;
private String contact;
private String relation;
private String contactPhone;
public static String updatedTime = null;
private Bitmap photo;
private Uri tempUri = null;//头像文件uri
private boolean headImageTag;
private String userObjectId;
private String account;
private String imagePath;



@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cadres);
        initView();

        }

private void initView() {


        //初始化各个组件
        user = new User();
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        et_personal_account = findViewById(R.id.et_personal_account_add);
        et_name = (EditText) findViewById(R.id.et_real_name_add);
        face_image = (ImageView) findViewById(R.id.face_image_add);
        bt_set_head = (Button) findViewById(R.id.bt_set_head_add);
        bt_set_head.setOnClickListener(this);
        et_rgSex = findViewById(R.id.rgSex_add);
        et_unit = findViewById(R.id.et_unit_add);
        et_position = findViewById(R.id.et_position_add);
        spinner_level = findViewById(R.id.spinner_level_add);
        et_birth = findViewById(R.id.et_birth_add);
        et_retired = findViewById(R.id.et_retired_add);
        et_retired_time = findViewById(R.id.et_retired_time_add);
        et_home_address = (EditText) findViewById(R.id.et_home_address_add);
        et_linkman = findViewById(R.id.et_linkman_add);
        et_relation = findViewById(R.id.et_relation_add);
        et_linkman_phone = findViewById(R.id.et_linkman_phone_add);
        btn_register = findViewById(R.id.btn_register_personal_add);
        btn_register_exet = findViewById(R.id.btn_register_exet_add);
        btn_register_exet.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        male = findViewById(R.id.male_add);
        female = findViewById(R.id.female_add);
        retired1 = findViewById(R.id.retired1_add);
        retired2 = findViewById(R.id.retired2_add);
        spouse = findViewById(R.id.spouse_add);
        children = findViewById(R.id.children_add);
        escort = findViewById(R.id.escort_add);
        et_phone_add = findViewById(R.id.et_phone_add);

        title.setText("添加老干部信息");
        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        tempUri = Uri.fromFile(new File(String.valueOf(Environment.getExternalStorageDirectory()), "head_phonto"  + ".png"));







        }

@Override
public void onClick(View v) {
        switch (v.getId()){
        case R.id.back:
                onBackPressed();
                break;
        case R.id.bt_set_head_add:
                showPopupWindow(this,null,tempUri,TAKE_PICTURE_HEAD);
                break;
        case R.id.btn_register_personal_add:


                //上传资料
                submitPersonalData();
                break;
        case R.id.btn_register_exet_add:
                onBackPressed();
                break;
        default:
                 return;
        }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                switch (requestCode) {
                        case CHOOSE_PICTURE:
                                if (resultCode == RESULT_OK){
                                        Uri uri = data.getData();
                                        startPhotoZoom(uri, 150, 150); // 开始对图片进行裁剪处理
                                }else {
                                }
                                break;
                        case TAKE_PICTURE_HEAD:
                                if (resultCode == RESULT_OK){
                                        startPhotoZoom(tempUri, 150, 150); // 开始对图片进行裁剪处理
                                }else {

                                }
                                break;
                        case CROP_SMALL_PICTURE:
                                if (data != null) {
                                        Bundle extras = data.getExtras();
                                        if (extras != null) {
                                                photo = extras.getParcelable("data");
                                                photo = PictureUtils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
                                                face_image.setImageBitmap(photo);
                                                imagePath = PictureUtils.savePhoto(photo,String.valueOf(Environment.getExternalStorageDirectory()),"head_image");
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

/**
 * 提交上传个人资料数据到服务器
 */
private void submitPersonalData() {
        //首先获取值，其次注册账号，再次上传图片，最后注册个人资料


        //1、获取值
        account = et_personal_account.getText().toString();
        name = et_name.getText().toString();
        int genderId = et_rgSex.getCheckedRadioButtonId();
        if (male.isChecked()){
        gender ="男";
        }else {
        gender = "女";
        }
        birth = et_birth.getText().toString();
        unit = et_unit.getText().toString();
        duty = et_position.getText().toString();
        if (retired1.isChecked()){
        treatment = "离休";
        }else
        treatment = "退休";
        treatmentData = et_retired_time.getText().toString();
        grade = spinner_level.getSelectedItem().toString();
        address = et_home_address.getText().toString();
        contact = et_linkman.getText().toString();
        int relationId = et_relation.getCheckedRadioButtonId();
        if (spouse.isChecked() ){
        relation = "配偶";
        }else if (children.isChecked()){
        relation = "子女";
        }else {
        relation = "其它";
        }
        contactPhone = et_linkman_phone.getText().toString();
        phone = et_phone_add.getText().toString();
        if (TextUtils.isEmpty(phone)){
                new MessageDialog(this,"手机号码不能为空！");
                return;
        };
        if (!PhoneFormatCheckUtils.isPhoneLegal(account)){
                new MessageDialog(this,"账号只能是手机号码！");
                return;
        }
        if (TextUtils.isEmpty(name)){
        new MessageDialog(this,"真实姓名不能为空！");
        return;
        };

        if (TextUtils.isEmpty(unit)){
        new MessageDialog(this,"请填写原单位！");
        return;
        };
        if (TextUtils.isEmpty(duty)){
        new MessageDialog(this,"请填写原职务！");
        return;
        };
;
        if (DateUtils.isValidDate(birth) == false){
                new MessageDialog(this,"出生日期格式不正确！(应如：2018-01-01)");
                return;
        }
;
        if (DateUtils.isValidDate(treatmentData) == false){
                new MessageDialog(this,"离退休日期格式不正确！(应如：2018-01-01)");
                return;
        }
        if (TextUtils.isEmpty(address)){
        new MessageDialog(this,"请填写家庭住址！");
        return;
        };

        phone = et_phone_add.getText().toString();
        final UserData userData = new UserData();
        userData.setObjectId(userObjectId);
        userData.setAccount(account);
        userData.setPhone(phone);
        userData.setNumber("");
        userData.setName(name);;
        userData.setGender(gender);
        userData.setBirth(birth);
        userData.setUnit(unit);
        userData.setDuty(duty);
        userData.setTreatment(treatment);
        userData.setTreatmentData(treatmentData);
        userData.setGrade(grade);
        userData.setAddress(address);
        userData.setContact(contact);
        userData.setRelation(relation);
        userData.setContactPhone(contactPhone);
        userData.setIntegral(1);
        userData.setPermissions("members");

        User user = new User();
        user.setUsername(name);
        user.setPassword("000000");
        user.setPermissions("members");
        user.setMobilePhoneNumber(account);
        user.setMobilePhoneNumberVerified(true);
        user.signUp(AddCadresActivity.this, new SaveListener() {
                @Override
                public void onSuccess() {
                                                if (imagePath !=null && headImageTag == true){
                                final BmobFile bmobFile1 = new BmobFile(new File(imagePath));
                                bmobFile1.uploadblock(AddCadresActivity.this, new UploadFileListener() {
                                        @Override
                                        public void onSuccess() {
                                                String fileUr = bmobFile1.getFileUrl(AddCadresActivity.this);
                                                userData.setPhoto(fileUr);
                                              userData.save(AddCadresActivity.this, new SaveListener() {
                                                      @Override
                                                      public void onSuccess() {
                                                              new MessageDialog(AddCadresActivity.this,"资料添加成功！");

                                                      }

                                                      @Override
                                                      public void onFailure(int i, String s) {
                                                              new MessageDialog(AddCadresActivity.this,"资料添加失败！错误代码是："+i);

                                                      }
                                              });

                                        }
                                        @Override
                                        public void onFailure(int i, String s) {
                                                new MessageDialog(AddCadresActivity.this,"照片上传错误！错误代码是："+i);

                                        }
                                });

                        }else {
                                                        userData.save(AddCadresActivity.this, new SaveListener() {
                                                                @Override
                                                                public void onSuccess() {
                                                                        new MessageDialog(AddCadresActivity.this,"资料添加成功！");

                                                                }

                                                                @Override
                                                                public void onFailure(int i, String s) {
                                                                        new MessageDialog(AddCadresActivity.this,"资料添加失败！错误代码是："+i);

                                                                }
                                                        });

                                                }
                }

                @Override
                public void onFailure(int i, String s) {

                }
        });
        }
  }

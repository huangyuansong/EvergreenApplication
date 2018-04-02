package com.example.pc.evergreen.activitys;import android.content.Intent;import android.content.SharedPreferences;import android.graphics.Bitmap;import android.net.Uri;import android.os.Bundle;import android.os.Environment;import android.provider.MediaStore;import android.support.v4.widget.NestedScrollView;import android.text.TextUtils;import android.util.Log;import android.view.View;import android.widget.Button;import android.widget.EditText;import android.widget.ImageButton;import android.widget.ImageView;import android.widget.RadioButton;import android.widget.RadioGroup;import android.widget.Spinner;import android.widget.TextView;import com.example.pc.evergreen.R;import com.example.pc.evergreen.beans.UserData;import com.example.pc.evergreen.dialog.MessageDialog;import com.example.pc.evergreen.dialog.SelectPopupWindow;import com.example.pc.evergreen.implementation.AccountDAOImp;import com.example.pc.evergreen.implementation.FileDaoImp;import com.example.pc.evergreen.interfaces.AccountDAO;import com.example.pc.evergreen.utils.DateUtils;import com.example.pc.evergreen.utils.PictureUtils;import com.example.pc.evergreen.utils.VolleyLoadPicture;import java.io.File;import cn.bmob.v3.datatype.BmobFile;import cn.bmob.v3.listener.DeleteListener;import cn.bmob.v3.listener.UpdateListener;import cn.bmob.v3.listener.UploadFileListener;/** * 老干部个人资料修改界面（管理员修改界面） */public class ModifyPersonalDataActivity extends SelectPopupWindow implements View.OnClickListener{    private ImageButton back;//返回    private TextView title;//界面标题    private ImageView face_image;//免冠照    private Button bt_set_head;//上传免冠照按钮    private EditText et_personal_account;//账号    private EditText et_name;//姓名输入控件    private RadioGroup et_rgSex;//性别控件    private EditText et_unit;//单位输入控件    private EditText et_position;//职务输入控件    private Spinner spinner_level;//级别输入控件    private EditText et_birth;//出生日期    private RadioGroup et_retired;//离/退休    private EditText et_retired_time;//离退休时间控件    private EditText et_home_address;//家庭住址控件    private EditText et_phone_modify;//老干部电话控件    private EditText et_linkman;//联系人姓名输入控件    private RadioGroup et_relation;//关系选择控件    private EditText et_linkman_phone;//联系人电话    private Button btn_register;//提交按钮    private Button btn_register_exet;//取消退出按钮    private RadioButton male;    private RadioButton female;    private RadioButton retired1;    private RadioButton retired2;    private RadioButton spouse;    private RadioButton children;    private RadioButton escort;    private String Tag = "manager";    private String fileUrl;    private String account;    private String name;    private String gender;    private String birth;    private String unit;    private String duty;    private String treatment;    private String treatmentData;    private String grade;    private String address;    private String contact;    private String relation;    private String contactPhone;    private Integer integral;    public static String updatedTime = null;    private UserData userData;    private String phone;    private SharedPreferences sharedPre;    private Bitmap photo;    private Uri tempUri;//头像文件uri    private String headImagePath = null;//头像本地地址url,用于上传到服务器    private String imagePath;//头像的远程地址    private NestedScrollView scroll_view;    private boolean headImageTag = false;//用户是否更换了头像    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_modify_personl);        userData = (UserData) getIntent().getSerializableExtra("userMotify");        initView();            }    @Override    protected void onResume() {        super.onResume();    }    private void initView() {        //初始化各个组件        title = findViewById(R.id.title);        back = findViewById(R.id.back);        et_personal_account = findViewById(R.id.et_personal_account_modify);        et_name = (EditText) findViewById(R.id.et_real_name_modify);        face_image = (ImageView) findViewById(R.id.face_image_modify);        bt_set_head = (Button) findViewById(R.id.bt_set_head_modify);        bt_set_head.setOnClickListener(this);        et_rgSex = findViewById(R.id.rgSex_modify);        et_unit = findViewById(R.id.et_unit_modify);        et_position = findViewById(R.id.et_position_modify);        spinner_level = findViewById(R.id.spinner_level_modify);        et_birth = findViewById(R.id.et_birth_modify);        et_retired = findViewById(R.id.et_retired_modify);        et_retired_time = findViewById(R.id.et_retired_time_modify);        et_home_address = (EditText) findViewById(R.id.et_home_address_modify);        et_linkman = findViewById(R.id.et_linkman_modify);        et_relation = findViewById(R.id.et_relation_modify);        et_linkman_phone = findViewById(R.id.et_linkman_phone_modify);        btn_register = findViewById(R.id.btn_register_personal_modify);        btn_register_exet = findViewById(R.id.btn_register_exet_modify);        btn_register_exet.setOnClickListener(this);        btn_register.setOnClickListener(this);        male = findViewById(R.id.male_modify);        female = findViewById(R.id.female_modify);        retired1 = findViewById(R.id.retired1_modify);        retired2 = findViewById(R.id.retired2_modify);        spouse = findViewById(R.id.spouse_modify);        children = findViewById(R.id.children_modify);        escort = findViewById(R.id.escort_modify);        et_phone_modify = findViewById(R.id.et_phone_modify);        title.setText("修改资料");        back = findViewById(R.id.back);        back.setOnClickListener(this);        tempUri = Uri.fromFile(new File(String.valueOf(Environment.getExternalStorageDirectory()), "head_phonto_" + userData.getAccount()+ ".png"));        et_personal_account.setText(userData.getAccount());       // 通过Volley加载图片到头像控件        VolleyLoadPicture vlp = new VolleyLoadPicture(ModifyPersonalDataActivity.this, face_image);        if (userData.getPhoto() != null) {            vlp.getmImageLoader().get(userData.getPhoto(), vlp.getOne_listener());        }else {            face_image.setImageResource(R.mipmap.icon_head);        }                        et_name.setText(userData.getName());                        et_home_address.setText( userData.getAddress());                        if ("男".equals( userData.getGender())){                            male.setChecked(true);                        }else {                            female.setChecked(true);                        }                        et_unit.setText( userData.getUnit());                        et_position.setText(userData.getDuty());                        if (userData.getGrade()!=null) {                            switch (userData.getGrade()) {                                case "正省部级":                                    spinner_level.setSelection(0);                                    break;                                case "副省部级":                                    spinner_level.setSelection(1);                                    break;                                case "正地级":                                    spinner_level.setSelection(2);                                    break;                                case "副地级":                                    spinner_level.setSelection(3);                                    break;                                case "正县级":                                    spinner_level.setSelection(4);                                    break;                                case "副县级":                                    spinner_level.setSelection(5);                                    break;                                case "正科级":                                    spinner_level.setSelection(6);                                    break;                                case "副科级":                                    spinner_level.setSelection(7);                                    break;                                case "其它":                                    spinner_level.setSelection(8);                                    break;                            }                        }                        et_birth.setText( userData.getBirth());                        if ("离休".equals( userData.getTreatment())){                            retired1.setChecked(true);                        }else {                            retired2.setChecked(true);                        }                        et_retired_time.setText( userData.getTreatmentData());                        et_home_address.setText( userData.getAddress());                        et_linkman.setText( userData.getContact());                        et_linkman_phone.setText(userData.getContactPhone());                        if (userData.getRelation() !=null) {                            switch (userData.getRelation()) {                                case "配偶":                                    spouse.setChecked(true);                                    return;                                case "子女":                                    children.setChecked(true);                                    return;                                case "其它":                                    escort.setChecked(true);                                default:                                    return;                            }                        }        }    @Override    public void onClick(View v) {        switch (v.getId()) {            case R.id.back:                onBackPressed();                break;            case R.id.bt_set_head_modify:                showPopupWindow(this, account, tempUri, TAKE_PICTURE_HEAD);                break;            case R.id.btn_register_personal_modify:                //上传资料                submitPersonalData();                break;            case R.id.btn_register_exet_modify:                onBackPressed();                break;            default:                break;        }    }    @Override    protected void onActivityResult(int requestCode, int resultCode, Intent data) {        switch (requestCode) {            case CHOOSE_PICTURE:                if (resultCode == RESULT_OK){                    Uri uri = data.getData();                    startPhotoZoom(uri, 150, 150); // 开始对图片进行裁剪处理                }else {                }                break;            case TAKE_PICTURE_HEAD:                if (resultCode == RESULT_OK){                    startPhotoZoom(tempUri, 150, 150); // 开始对图片进行裁剪处理                }else {                }                break;            case CROP_SMALL_PICTURE:                if (data != null) {                    Bundle extras = data.getExtras();                    if (extras != null) {                        photo = extras.getParcelable("data");                        photo = PictureUtils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了                        face_image.setImageBitmap(photo);                        headImagePath = PictureUtils.savePhoto(photo,String.valueOf(Environment.getExternalStorageDirectory()),"head_"+userData.getAccount());                        headImageTag = true;                    }                }                break;        }    }    /**     * 裁剪图片方法实现     *     * @param uri     */    protected void startPhotoZoom(Uri uri,int outputX,int outputY) {        if (uri == null) {            Log.i("tag", "The uri is not exist.");        }        Intent intent = new Intent("com.android.camera.action.CROP");        intent.setDataAndType(uri, "image/*");        // 设置裁剪        intent.putExtra("crop", "true");        // aspectX aspectY 是宽高的比例        intent.putExtra("aspectX", 1);        intent.putExtra("aspectY", 1);        // outputX outputY 是裁剪图片宽高        intent.putExtra("outputX", outputX);        intent.putExtra("outputY", outputY);        intent.putExtra("return-data", true);        startActivityForResult(intent, CROP_SMALL_PICTURE);    }    /**     * 提交修改资料数据到服务器     */    private void submitPersonalData() {        //从UI中获取值        name = et_name.getText().toString();        int genderId = et_rgSex.getCheckedRadioButtonId();        if (male.isChecked()){            gender ="男";        }else {            gender = "女";        }        birth = et_birth.getText().toString();        unit = et_unit.getText().toString();        duty = et_position.getText().toString();        if (retired1.isChecked()){            treatment = "离休";        }else            treatment = "退休";        treatmentData = et_retired_time.getText().toString();        grade = spinner_level.getSelectedItem().toString();        address = et_home_address.getText().toString();        contact = et_linkman.getText().toString();        int relationId = et_relation.getCheckedRadioButtonId();        if (spouse.isChecked() ){            relation = "配偶";        }else if (children.isChecked()){            relation = "子女";        }else {            relation = "其它";        }        contactPhone = et_linkman_phone.getText().toString();        if (TextUtils.isEmpty(name)){            new MessageDialog(this,"真实姓名不能为空！");            return;        };        if (TextUtils.isEmpty(unit)){            new MessageDialog(this,"请填写原单位！");            return;        };        if (TextUtils.isEmpty(duty)){            new MessageDialog(this,"请填写原职务！");            return;        };        if (DateUtils.isValidDate(birth) == false){            new MessageDialog(this,"出生日期格式不正确！(应如：2018-01-01)");            return;        }        if (DateUtils.isValidDate(treatmentData) == false){            new MessageDialog(this,"离退休日期格式不正确！(应如：2018-01-01)");            return;        }        if (TextUtils.isEmpty(address)){            new MessageDialog(this,"请填写家庭住址！");            return;        };        phone = et_phone_modify.getText().toString();        //设置userData的值        userData.setPhone(phone);        userData.setNumber("");        userData.setName(name);;        userData.setGender(gender);        userData.setBirth(birth);        userData.setUnit(unit);        userData.setDuty(duty);        userData.setTreatment(treatment);        userData.setTreatmentData(treatmentData);        userData.setGrade(grade);        userData.setAddress(address);        userData.setContact(contact);        userData.setRelation(relation);        userData.setContactPhone(contactPhone);        //首先上传图片，上传时确保用户已点击设置头像并取得头像文件；其次上传成功后将删除以往的头像（若有）        if (headImagePath !=null && headImageTag == true){            //上传图片，获取图片地址,并赋值到userData,同时删除以往的图片（若有）            final BmobFile bmobFile = new BmobFile(new File(headImagePath));            bmobFile.uploadblock(ModifyPersonalDataActivity.this, new UploadFileListener() {                @Override                public void onSuccess() {                    //查看数据库中有无头像文件，若有则取得地址,并删除                    String headImageUrl = userData.getPhoto();                    if (!(headImageUrl==null)){                        //删除以往的头像文件                        BmobFile file = new BmobFile();                        file.setUrl(headImageUrl);                        file.delete(ModifyPersonalDataActivity.this, new DeleteListener() {                            @Override                            public void onSuccess() {                            }                            @Override                            public void onFailure(int i, String s) {                            }                        });                    }                   String photoUrl =  bmobFile.getFileUrl(ModifyPersonalDataActivity.this);                    userData.setPhoto(photoUrl);                    userData.update(ModifyPersonalDataActivity.this, new UpdateListener() {                        @Override                        public void onSuccess() {                            new MessageDialog(ModifyPersonalDataActivity.this,"信息修改成功！");                        }                        @Override                        public void onFailure(int i, String s) {                            new MessageDialog(ModifyPersonalDataActivity.this,"信息修改失败！错误代码是："+i+"----"+s);                        }                    });                }                @Override                public void onFailure(int i, String s) {                }            });        }else {            userData.update(ModifyPersonalDataActivity.this, new UpdateListener() {                @Override                public void onSuccess() {                    new MessageDialog(ModifyPersonalDataActivity.this,"信息修改成功！");                }                @Override                public void onFailure(int i, String s) {                    new MessageDialog(ModifyPersonalDataActivity.this,"信息修改失败！错误代码是："+i+"----"+s);                }            });        }    } }
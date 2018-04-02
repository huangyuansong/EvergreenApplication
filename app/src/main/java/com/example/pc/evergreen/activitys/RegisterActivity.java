package com.example.pc.evergreen.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pc.evergreen.R;
import com.example.pc.evergreen.dialog.MessageDialog;
import com.example.pc.evergreen.implementation.AccountDAOImp;
import com.example.pc.evergreen.implementation.PhoneVerificationCodeDaoImp;
import com.example.pc.evergreen.utils.PhoneFormatCheckUtils;


/**
 * 注册功能
 * @class  RegisterActivity  
 * @author smile   
 * @date   2015-6-5 上午11:16:04  
 *   
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


	private TextView tv_title;//标题
	private ImageButton back;//返回控件
	private EditText register_et_account;//账号输入控件
	private String phoneNumber;//账号（只能是手机号码）
	private EditText editText_code;
	private String code;//验证码
	private EditText editText_password_one;//密码输入控件
	private EditText editText_password_two;//密码确认输入控件
	private Button btn_register;//注册按钮
	private EditText verification_code;//验证码输入控件
	private Button button_code;//获取验证码按钮
	private String password_one;//第一次密码
	private String password_two;//第二次密码
	public static CheckBox checkBox_register;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();

	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.title);
		back = findViewById(R.id.back);
		register_et_account = (EditText) findViewById(R.id.register_et_account);
		editText_password_one = (EditText) findViewById(R.id.et_password_one);
		editText_password_two = (EditText) findViewById(R.id.et_password_two);
		btn_register = (Button) findViewById(R.id.btn_register);
		editText_code = (EditText) findViewById(R.id.verification_code);
		button_code = (Button) findViewById(R.id.get_code);
		checkBox_register = (CheckBox) findViewById(R.id.checkBox_register);
		tv_title.setText("注  册");
		back.setOnClickListener(this);
		button_code.setOnClickListener(this);
		btn_register.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.back:
				onBackPressed();
				break;
			case R.id.get_code:
				phoneNumber = register_et_account.getText().toString();

				//首先判断是否为空，再判断是不是一个手机号，再通过该号发送并获取验证码
				if (!TextUtils.isEmpty(phoneNumber)) {
					if (!PhoneFormatCheckUtils.isPhoneLegal(phoneNumber)){
						new MessageDialog(this,"请正确填写手机号码！");
						return;
					}else {
						new PhoneVerificationCodeDaoImp(phoneNumber,this).getBmobVerificationCode();
					}

				}else {
					new MessageDialog(this,"手机号码不能为空！");
					return;
				}
					break;
			case R.id.btn_register:
				registerUser();
				break;
			default:
				break;

		}
	}

	private void registerUser() {
		phoneNumber = register_et_account.getText().toString();
		code = editText_code.getText().toString();
		password_one = editText_password_one.getText().toString();
		password_two = editText_password_two.getText().toString();
		AccountDAOImp implementLogin = new AccountDAOImp();
		implementLogin.register(this,phoneNumber,password_one,password_two,code);
	}
}

package com.example.pc.evergreen.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pc.evergreen.R;
import com.example.pc.evergreen.dialog.MessageDialog;
import com.example.pc.evergreen.implementation.AccountDAOImp;
import com.example.pc.evergreen.implementation.PhoneVerificationCodeDaoImp;
import com.example.pc.evergreen.utils.PhoneFormatCheckUtils;

/**
 * 重置密码
 * @class ResetPasswordActivity
 * @author smile
 * @date 2015-6-5 上午11:23:44
 * 
 */
public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener{

	private ImageButton back;
	private TextView title;
	private EditText et_phone;
	private EditText et_code;
	private Button btn_send;
	private EditText et_pwd_one;
	private EditText et_pwd_two;
	private Button btn_reset;
	private String phoneNumber;
	private String code;
	private String re_password_one;
	private String re_password_two;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_pwd);
		title = (TextView) findViewById(R.id.title);
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_pwd_two = (EditText) findViewById(R.id.et_pwd_two);
		et_code = (EditText) findViewById(R.id.et_verify_code);
		btn_send = (Button) findViewById(R.id.btn_send);
		et_pwd_one = (EditText) findViewById(R.id.et_pwd_one);
		btn_reset = (Button) findViewById(R.id.btn_reset);
		btn_send.setOnClickListener(this);
		btn_reset.setOnClickListener(this);
		back = findViewById(R.id.back);
		back.setOnClickListener(this);
		title.setText("重置密码");
		et_phone.setText(getIntent().getStringExtra("phone"));

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.back:
				onBackPressed();
				break;
			case R.id.btn_send:
				phoneNumber = et_phone.getText().toString();

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
			case R.id.btn_reset:
				code = et_code.getText().toString();
				re_password_one = et_pwd_one.getText().toString();
				re_password_two = et_pwd_two.getText().toString();
				AccountDAOImp implementLogin = new AccountDAOImp();
				implementLogin.resetPassword(phoneNumber,this,code,re_password_one,re_password_two);
				break;
		}
	}

}

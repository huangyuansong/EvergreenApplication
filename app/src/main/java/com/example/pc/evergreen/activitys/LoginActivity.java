package com.example.pc.evergreen.activitys;

import android.content.Intent;
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
import com.example.pc.evergreen.utils.SaveUserInformation;


/**
 * 登录 
 * @class  LoginActivity  
 * @author smile   
 * @date   2015-6-5 上午11:16:04  
 *   
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


	private static final String BMOB_APP_KEY="b7c9dbe6bc290400d077938869fc9988";

	private EditText et_account;
	private EditText et_login_password;
	private Button btn_login;
	private Button btn_register;
	private Button password_reset;
	private String password;
	private String account;
	private ImageButton back;
	private TextView title;
	public static CheckBox log_checkBox;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();

	}

	private void initView() {
		back = findViewById(R.id.back);
		back.setOnClickListener(this);
		title = findViewById(R.id.title);
		title.setText("登  录");
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(this);
		log_checkBox = (CheckBox) findViewById(R.id.log_checkBox);
		password_reset = (Button) findViewById(R.id.password_reset);
		password_reset.setOnClickListener(this);
		btn_register = (Button) findViewById(R.id.btn__login_register);
		et_account = (EditText) findViewById(R.id.et_account);
		et_login_password = (EditText) findViewById(R.id.et_login_password);
		btn_register.setOnClickListener(this);

	}

	/**
	 * 登录界面按钮选择跳转功能
	 */
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
				//首先判断账号密码都不能为空
				account = et_account.getText().toString();
				password = et_login_password.getText().toString();
				if (TextUtils.isEmpty(account)|TextUtils.isEmpty(password)){
					MessageDialog dialog = new MessageDialog(this,"用户名密码不能为空！");
					return;
				}
				SaveUserInformation.saveLoginInfo(this,account,password);
				AccountDAOImp impementLogin = new AccountDAOImp();
				impementLogin.login(LoginActivity.this,account,password);
				break;
			case R.id.password_reset:
				reset();
				break;
            case R.id.btn__login_register:
                register();
                break;
			case R.id.back:
				onBackPressed();
				break;
            default:
                break;
        }

    }

	/**
	 * 进入重设置密码界面
	 */
	private void reset() {
		String phone = et_account.getText().toString();
		Intent intent = new Intent(LoginActivity.this,ResetPasswordActivity.class);
		intent.putExtra("phone",phone);
		startActivity(intent);
	}

	/**
	 * 进入注册界面
	 */
	public void register() {
		Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

}

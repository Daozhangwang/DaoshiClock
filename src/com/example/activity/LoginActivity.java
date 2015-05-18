package com.example.activity;

import com.example.daoshiclock.IsLogin;
import com.example.thread.PostThread;
import com.example.daoshiclock.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	public Button login;
	public Button enroll;
	public EditText input_name;
	public EditText input_pass;
	public String username;
	public String password;
	public int result;
	public Handler postHandler;
	public IsLogin islogin;
	private ProgressDialog login_progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initview();
		initevent();

	}

	private void initevent() {
		islogin = (IsLogin) getApplication();
		postHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					login_progress.dismiss();
					islogin.setlogin(username);
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
					break;
				case 0:
					login_progress.dismiss();
					Toast.makeText(LoginActivity.this, "登录失败",
							Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
			}
		};

		enroll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, EnrollActivity.class);
				startActivity(intent);

			}
		});
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				password = input_pass.getText().toString();
				username = input_name.getText().toString();
				login_progress = new ProgressDialog(LoginActivity.this);
				login_progress.setMessage("正在向服务器提交请求");
				login_progress.setTitle("请稍候");
				login_progress.show();
				PostThread pt_login = new PostThread(username, password,
						postHandler);
				pt_login.start();

			}

		});

	}

	private void initview() {
		input_name = (EditText) findViewById(R.id.input_name);
		input_pass = (EditText) findViewById(R.id.input_pass);
		login = (Button) findViewById(R.id.bt_login);
		enroll = (Button) findViewById(R.id.bt_enroll);

	}

}

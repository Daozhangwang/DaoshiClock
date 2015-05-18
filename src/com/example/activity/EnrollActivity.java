package com.example.activity;

import com.example.thread.EnrollThread;
import com.example.daoshiclock.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

public class EnrollActivity extends Activity {
	public EditText input_password1;
	public EditText input_password2;
	public EditText input_username;
	public EditText input_age;
	public Button enroll;
	public RadioGroup sexgroup;
	public RadioButton boy;
	public RadioButton girl;
	public int sex = 2;
	private int input_key = 1;
	public String username;
	public String password1;
	public String password2;
	public String age_s;
	public int age;
	public Handler Enrollhandler;
	private ProgressDialog enroll_progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enroll);
		initview();
		initevent();
	}

	private void initevent() {
		Enrollhandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					enroll_progress.dismiss();
					Toast.makeText(EnrollActivity.this, "×¢²áÊ§°Ü",
							Toast.LENGTH_LONG).show();
					Intent intent = new Intent();
					intent.setClass(EnrollActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
					break;
				case 2:
					enroll_progress.dismiss();
					Toast.makeText(EnrollActivity.this, "×¢²áÊ§°Ü",
							Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
			}
		};
		sexgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {

				if (checkedId == boy.getId()) {
					sex = 1;
				} else {
					sex = 0;
				}

			}
		});
		enroll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				input_key = check();
				if (input_key == 1) {
					enroll_progress = new ProgressDialog(EnrollActivity.this);
					enroll_progress.setMessage("ÕýÔÚÏò·þÎñÆ÷Ìá½»ÇëÇó");
					enroll_progress.setTitle("ÇëÉÔºò");
					enroll_progress.show();
					EnrollThread enrollthread = new EnrollThread(username,
							password1, sex, age, Enrollhandler);
					enrollthread.start();
				} else {

					Log.i("key", String.valueOf(input_key));
				}

			}

		});

	}

	protected int check() {
		username = input_username.getText().toString();
		password1 = input_password1.getText().toString();
		password2 = input_password2.getText().toString();
		age_s = input_age.getText().toString();

		if (username.isEmpty() || password1.isEmpty() || password2.isEmpty()
				|| age_s.isEmpty()) {
			return 2;

		} else {
			age = Integer.parseInt(age_s);
		}

		if (age > 100 || age < 5) {

			return 3;
		}
		if (!password1.equals(password2)) {

			return 4;
		}
		if (password1.length() > 14 || password1.length() < 6) {
			return 5;
		}
		return 1;
	}

	private void initview() {
		input_password1 = (EditText) findViewById(R.id.enroll_password_1);
		input_password2 = (EditText) findViewById(R.id.enroll_password_2);
		input_username = (EditText) findViewById(R.id.enroll_username);
		input_age = (EditText) findViewById(R.id.enroll_age);
		enroll = (Button) findViewById(R.id.enroll);
		sexgroup = (RadioGroup) findViewById(R.id.sex_radioGroup);
		boy = (RadioButton) findViewById(R.id.boy);
		girl = (RadioButton) findViewById(R.id.girl);

	}

}

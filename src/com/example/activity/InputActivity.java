package com.example.activity;

import com.example.daoshiclock.IsLogin;
import com.example.daoshiclock.R;
import com.example.shared.myshared;
import com.example.thread.introducethread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.Activity;

public class InputActivity extends Activity {
	public EditText introduce;
	public ImageView back;
	public Button save;
	public String myintroduce;
	public IsLogin islogin;

	private String username;

	public Handler introduceHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_input);
		initview();
		initevent();

	}

	private void initevent() {
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		introduceHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:

					myshared s1 = new myshared(InputActivity.this);
					s1.setintroduce(myintroduce);
					finish();
					break;
				case 0:
					Toast.makeText(InputActivity.this, "wrong",
							Toast.LENGTH_LONG).show();

				default:
					break;
				}
			}
		};
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myintroduce = introduce.getText().toString();
				username = islogin.getlogin();
				introducethread it = new introducethread(username, myintroduce,
						introduceHandler);
				it.start();
				Log.i("myintroduce", myintroduce);

			}
		});

	}

	private void initview() {
		introduce = (EditText) findViewById(R.id.introduce);
		save = (Button) findViewById(R.id.input_save);
		back = (ImageView) findViewById(R.id.input_back);
		islogin = (IsLogin) getApplication();

	}

}

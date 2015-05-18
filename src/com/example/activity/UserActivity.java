package com.example.activity;

import com.example.daoshiclock.IsLogin;
import com.example.daoshiclock.R;
import com.example.daoshiclock.User;
import com.example.thread.attentionThread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

public class UserActivity extends Activity {
	public String name;
	public String myname;
	public TextView username;
	public TextView songs;
	public TextView fans;
	public TextView attention;
	public TextView introduce;
	public ImageView back;
	public Button follow_situation;
	public Boolean isattention = false;
	public static Handler handler;
	public static Handler mhandler;
	public ProgressDialog attention_progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user);
		initview();
		getdata();
		showdata();
		initevent();

	}

	private void initevent() {

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case 1:
					isattention = msg.getData().getBoolean("key");
					try {
						introduce.setText(msg.getData().getString("introduce"));
						fans.setText(msg.getData().getString("fans"));
						attention.setText(msg.getData().getString("attention"));
						songs.setText(msg.getData().getString("songs"));

						if (!isattention) {
							follow_situation.setText(R.string.situation_no);

						} else {

							follow_situation
									.setText(R.string.situation_already);
						}
						if (name.equals(myname)) {

							follow_situation.setVisibility(View.GONE);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					break;

				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
		mhandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case 1:
					attention_progress.dismiss();
					isattention = !isattention;
					if (!isattention) {
						follow_situation.setText(R.string.situation_no);

					} else {
						follow_situation.setText(R.string.situation_already);

					}

					break;
				case 2:
					attention_progress.dismiss();
					Toast.makeText(UserActivity.this, "wrong",
							Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
		follow_situation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				attention_progress = new ProgressDialog(UserActivity.this);
				attention_progress.setMessage("正在向服务器提交请求");
				attention_progress.setTitle("请稍候");
				attention_progress.show();
				attentionThread at = new attentionThread(myname, name);
				at.start();

			}

		});
		fans.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("type", "fans");
				bundle.putString("username", name);
				intent.putExtras(bundle);
				intent.setClass(UserActivity.this, FanlistActivity.class);
				startActivity(intent);

			}
		});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				finish();

			}
		});
		attention.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("type", "star");
				bundle.putString("username", name);
				intent.putExtras(bundle);
				intent.setClass(UserActivity.this, FanlistActivity.class);
				startActivity(intent);

			}
		});

	}

	private void showdata() {
		username.setText(name);

	}

	private void getdata() {
		IsLogin islogin = (IsLogin) getApplication();
		Bundle bundle = this.getIntent().getExtras();
		name = bundle.getString("username");
		myname = islogin.getlogin();
		User user = new User(name);
		user.getmessage(myname);

	}

	private void initview() {
		username = (TextView) findViewById(R.id.user_username);
		introduce = (TextView) findViewById(R.id.user_introduce);
		songs = (TextView) findViewById(R.id.user_numberofupload);
		fans = (TextView) findViewById(R.id.user_follower);
		attention = (TextView) findViewById(R.id.user_attention);
		follow_situation = (Button) findViewById(R.id.follow_situation);
		back = (ImageView) findViewById(R.id.user_back);

	}

}

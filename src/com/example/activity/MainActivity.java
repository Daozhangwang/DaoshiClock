package com.example.activity;

import java.io.File;

import com.example.daoshiclock.IsLogin;
import com.example.daoshiclock.MainPagerAdapter;
import com.example.daoshiclock.R;
import com.example.thread.MineThread;

import android.os.Bundle;
import android.os.Environment;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener {
	public TextView Clock;
	public TextView base;
	public TextView online;
	public TextView mine;
	public ViewPager mainpager;
	public MainPagerAdapter mainadapter;
	private ImageButton gorecord;
	private String path;
	public File clockpath;
	public IsLogin islogin;
	public String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initview();
		initevent();
		initfile();

		islogin = (IsLogin) getApplication();
		username = islogin.getlogin();
		setusername(username);
		updatemine();

	}

	private void updatemine() {

		MineThread mine_th = new MineThread(username, this);
		mine_th.start();

	}

	private void setusername(String username) {
		this.username = username;

	}

	public String getusername() {

		return username;

	}

	private void initfile() {

		path = Environment.getExternalStorageDirectory().getAbsolutePath();
		path += "/Clock";
		clockpath = new File(path);
		if (clockpath.exists()) {
			Log.i("filepath", "exist this folder");

		} else {
			Log.i("filepath", "not exist");
			clockpath.mkdirs();
		}

	}

	private void initevent() {
		gorecord.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				// intent.setClass(MainActivity.this,RecordActivity.class);
				intent.setClass(MainActivity.this, RecordActivity.class);
				startActivity(intent);

			}
		});
		Clock.setTextColor(Color.BLUE);
		mainpager.setAdapter(mainadapter);
		Clock.setOnClickListener(this);
		base.setOnClickListener(this);
		online.setOnClickListener(this);
		mine.setOnClickListener(this);
		mainpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				Clock.setTextColor(Color.BLACK);
				base.setTextColor(Color.BLACK);
				online.setTextColor(Color.BLACK);
				mine.setTextColor(Color.BLACK);
				int currentItem = mainpager.getCurrentItem();

				switch (currentItem) {
				case 0:

					Clock.setTextColor(Color.BLUE);

					break;
				case 1:

					base.setTextColor(Color.BLUE);

					break;
				case 2:

					online.setTextColor(Color.BLUE);
					break;
				case 3:

					mine.setTextColor(Color.BLUE);

					break;

				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	private void initview() {
		Clock = (TextView) findViewById(R.id.clock_tv);
		base = (TextView) findViewById(R.id.musicbase_tv);
		online = (TextView) findViewById(R.id.online_tv);
		mine = (TextView) findViewById(R.id.mine_tv);
		gorecord = (ImageButton) findViewById(R.id.record);
		mainpager = (ViewPager) findViewById(R.id.mainviewpager);
		mainadapter = new MainPagerAdapter(this.getSupportFragmentManager());

	}

	@Override
	public void onClick(View v) {
		Clock.setTextColor(Color.BLACK);
		base.setTextColor(Color.BLACK);
		online.setTextColor(Color.BLACK);
		mine.setTextColor(Color.BLACK);
		switch (v.getId()) {
		case R.id.clock_tv:
			// Log.i("Click","1");
			mainpager.setCurrentItem(0);
			Clock.setTextColor(Color.BLUE);

			break;
		case R.id.musicbase_tv:
			mainpager.setCurrentItem(1);
			// Log.i("Click","2");
			base.setTextColor(Color.BLUE);
			break;
		case R.id.online_tv:
			mainpager.setCurrentItem(2);
			// Log.i("Click","3");
			online.setTextColor(Color.BLUE);
			break;
		case R.id.mine_tv:
			mainpager.setCurrentItem(3);
			// Log.i("Click","4");
			mine.setTextColor(Color.BLUE);
			break;

		default:
			break;
		}

	}

	public ViewPager getpager() {

		return mainpager;

	}

}

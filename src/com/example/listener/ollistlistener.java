package com.example.listener;

import java.util.Map;

import com.example.AsyncTask.OnlineAsyncTask;
import com.example.activity.UserActivity;
import com.example.daoshiclock.R;
import com.example.daoshiclock.baseplayer;
import com.example.thread.zanThread;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ollistlistener implements OnItemClickListener {
	public Context context;
	public ImageView img_isliker;
	public TextView tv_upper;
	public Button bt_musicplay;

	public baseplayer ollistplayer;
	public Boolean playing = false;

	public String str_upper;
	public String str_music;

	public ollistlistener(Context context) {
		this.context = context;

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
			long arg3) {
		initview(arg1);
		getdata(arg2);
		ollistplayer = new baseplayer(context, str_music, 2);

		tv_upper.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("username", str_upper);
				intent.putExtras(bundle);
				intent.setClass(context, UserActivity.class);
				context.startActivity(intent);

			}
		});

		bt_musicplay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!playing) {
					Log.i("uplist", "play" + String.valueOf(arg2));
					ollistplayer.start();
					playing = true;
					bt_musicplay.setText("ÔÝÍ£");

				} else {

					ollistplayer.pause();
					playing = false;
					bt_musicplay.setText("²¥·Å");

				}
			}
		});
		img_isliker.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				zanThread zanThread = new zanThread(str_upper, str_music);
				zanThread.start();
			}
		});

	}

	private void getdata(int arg2) {
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) OnlineAsyncTask.onlinelistAdapter
				.getItem(arg2);
		str_upper = map.get("upper");
		str_music = map.get("music_name");

	}

	private void initview(View arg1) {
		img_isliker = (ImageView) arg1.findViewById(R.id.online_isliker_img);
		tv_upper = (TextView) arg1.findViewById(R.id.online_upper);
		bt_musicplay = (Button) arg1.findViewById(R.id.online_play);

	}

}

package com.example.listener;

import java.io.File;
import java.util.Map;

import com.example.AsyncTask.MySongsAsyncTask;
import com.example.daoshiclock.R;
import com.example.daoshiclock.baseplayer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.content.Context;

public class uplistlistener implements OnItemClickListener {
	public TextView play;
	public baseplayer uplistplayer;
	public Boolean playing = false;
	public String musicname;
	public File musicpath;

	public uplistlistener(Context context) {

		uplistplayer = new baseplayer(context, musicname, 2);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
			long arg3) {
		play = (TextView) arg1.findViewById(R.id.uplist_play);
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) MySongsAsyncTask.uplistAdapter
				.getItem(arg2);
		musicname = map.get("name");

		play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!playing) {
					Log.i("uplist", "play" + String.valueOf(arg2));
					uplistplayer.start();
					playing = true;
					play.setText("ÔÝÍ£");

				} else {

					uplistplayer.pause();
					playing = false;
					play.setText("²¥·Å");

				}

			}
		});

	}
}

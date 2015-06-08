package com.example.listener;

import java.io.File;
import java.util.Map;

import com.example.daoshiclock.DatabaseHelper;
import com.example.daoshiclock.R;
import com.example.daoshiclock.setbaselist;
import com.example.shared.myshared;
import com.example.thread.UploadThread;
import com.example.daoshiclock.baseplayer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class baselistListener implements OnItemClickListener {
	public String musicname;

	public TextView play;
	public TextView delete;
	public TextView upload;
	public Boolean playing = false;
	public DatabaseHelper database;
	public String basepath;
	public File musicpath;
	public Context context;
	public ListView baselist;
	public Handler handler;
	public String username;

	public baseplayer player;

	private ProgressDialog upload_progress;
	public String ring;

	public baselistListener(String username, Context context, ListView baselist) {
		this.username = username;
		this.context = context;
		this.baselist = baselist;
		basepath = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/Clock/";

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
			long arg3) {

		getdata(context, arg2);
		initview(arg1);

		play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!playing) {
					Log.i("baselist", "play music num" + String.valueOf(arg2));
					player.start();
					play.setText("暂停");

				} else {

					player.pause();
					play.setText("播放");

				}
				playing = !playing;

			}
		});
		upload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.i("baselist", "upload" + String.valueOf(arg2));
				upload_progress = new ProgressDialog(context);
				upload_progress.setMessage("正在向服务器提交请求");
				upload_progress.setTitle("请稍候");
				upload_progress.show();
				UploadThread up_pt = new UploadThread(username, musicpath,
						handler);
				up_pt.start();

			}
		});
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!musicname.equals(ring)) {
					if (musicpath.delete()) {
						Toast.makeText(context, "删除成功", Toast.LENGTH_LONG)
								.show();
						setbaselist set = new setbaselist(username, context,
								baselist);
						set.clear();
						set.getfile();
						set.add();
						set.setadaper();
						set.clicker();

					} else {
						Toast.makeText(context, "删除失败", Toast.LENGTH_LONG)
								.show();

					}

				} else {
					Toast.makeText(context, "当前铃声...不能删除", Toast.LENGTH_LONG)
							.show();

				}
			}
		});

	}

	private void getdata(Context context, int arg2) {

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) setbaselist.musiclist_Adapter
				.getItem(arg2);
		musicname = map.get("name");
		myshared shared = new myshared(context);
		ring = shared.getring();
		musicpath = new File(basepath + musicname);

	}

	private void initview(View arg1) {
		play = (TextView) arg1.findViewById(R.id.musiclist_play);
		delete = (TextView) arg1.findViewById(R.id.musiclist_delete);
		upload = (TextView) arg1.findViewById(R.id.musiclist_upload);

		player = new baseplayer(context, musicname, 1);

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {

				case 0:
					upload_progress.dismiss();
					Toast.makeText(context, "上传失败", Toast.LENGTH_LONG).show();
					break;
				case 1:
					upload_progress.dismiss();
					Toast.makeText(context, "上传成功", Toast.LENGTH_LONG).show();
					break;
				case 3:
					upload_progress.dismiss();
					Toast.makeText(context, "无需重复上传", Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
			}
		};

	}
}

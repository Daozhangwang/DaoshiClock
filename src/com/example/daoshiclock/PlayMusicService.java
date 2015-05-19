package com.example.daoshiclock;

import java.io.File;

import com.example.shared.myshared;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

public class PlayMusicService extends Service {

	private MediaPlayer myplayer;
	@SuppressWarnings("unused")
	private File file;
	public Cursor csong;
	public SQLiteDatabase showdb;
	String path;

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		myplayer = new MediaPlayer();
		String song = getsong();
		Log.i("get ring ", song);
		// String path="/sdcard/Clock/"+song;
		path = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/Clock/" + "s.mp3";

	}

	private String getsong() {

		myshared shared = new myshared(this);
		return shared.getring();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		myplayer.stop();
		super.onDestroy();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub

		super.onStart(intent, startId);
		try {
			myplayer.reset();
			myplayer.setDataSource(path);
			myplayer.prepare();
			myplayer.start();
			Log.i("ringprepare", "cool");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

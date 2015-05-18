package com.example.daoshiclock;

import java.io.File;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class PlayMusicService extends Service {

	private MediaPlayer myplayer;
	@SuppressWarnings("unused")
	private File file;
	public Cursor csong;
	public SQLiteDatabase showdb;

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {
		myplayer = new MediaPlayer();
		String song = getsong();
		Log.i("nowsong", song);
		// String path="/sdcard/Clock/"+song;
		String path = "/sdcard/Clock/s.mp3";
		try {
			myplayer.reset();
			myplayer.setDataSource(path);
			myplayer.prepareAsync();

			Log.i("play", "cool");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.onCreate();
	}

	private String getsong() {

		DatabaseHelper database = new DatabaseHelper(this);
		showdb = database.getWritableDatabase();
		csong = showdb.query("song", null, null, null, null, null, null);
		csong.moveToFirst();
		String showSong = csong.getString(csong.getColumnIndex("song_name"));
		return showSong;
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
		myplayer.start();
		super.onStart(intent, startId);
	}

}

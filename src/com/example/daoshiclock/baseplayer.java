package com.example.daoshiclock;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

public class baseplayer extends MediaPlayer {
	public MediaPlayer player;
	public String filepath;

	public String basepath;

	public baseplayer(Context context, String musicname, int i) {
		basepath = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/Clock/";

		if (i == 1) {
			filepath = basepath + musicname;

		} else if (i == 2) {

			filepath = "http://daoshi58alarm-musicbase.stor.sinaapp.com/it's%20my%20life.mp3";

		}

		Log.i("player ", filepath);
		if (player == null) {

			player = new MediaPlayer();

		} else {

			player.release();
			player.stop();
			player = new MediaPlayer();

		}

	}

	public void start() {

		try {
			player.reset();
			player.setDataSource(filepath);
			player.prepare();
			player.start();
			Log.i("play", filepath + "cool");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void pause() {

		try {

			player.pause();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

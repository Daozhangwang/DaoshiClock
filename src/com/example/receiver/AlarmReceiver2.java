package com.example.receiver;
import java.util.Calendar;


import com.example.activity.AlarmalertActivity;
import com.example.daoshiclock.DatabaseHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AlarmReceiver2 extends BroadcastReceiver {
	public Cursor cu;
	public int today;
	public Calendar c;

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Calendar c = Calendar.getInstance();
		today = c.get(Calendar.DAY_OF_WEEK) - 1;
		Log.i("naozhong2222",  "start");
		DatabaseHelper database = new DatabaseHelper(arg0);
		SQLiteDatabase db = database.getWritableDatabase();
		cu = db.query("weekday2", null, null, null, null, null, null);
		cu.moveToFirst();
		int key = cu.getInt(cu.getColumnIndex("day"));
		int dodo = cu.getInt((int) cu.getColumnIndex("do"));

		if (key == today && dodo == 1) {
			Log.i("naozhong", String.valueOf(key) + "start");
			Intent i = new Intent(arg0,AlarmalertActivity.class);

			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			arg0.startActivity(i);

		}
		while (cu.moveToNext()) {
			key = cu.getInt(cu.getColumnIndex("day"));
			dodo = cu.getInt((int) cu.getColumnIndex("do"));
			// Log.i("day",String.valueOf(key));

			if (key == today && dodo == 1) {
				Log.i("naozhong", String.valueOf(key) + "start");
				Intent i = new Intent(arg0, AlarmalertActivity.class);

				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				arg0.startActivity(i);

			}
		}

	}

}

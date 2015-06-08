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

public class AlarmReceiver3 extends BroadcastReceiver {
	public Cursor cu;
	public int today;
	public Calendar c;
	int dodo;
	int i;

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Log.i("Receiver3", "start");
		c = Calendar.getInstance();
		today = c.get(Calendar.DAY_OF_WEEK) - 1;
		DatabaseHelper database = new DatabaseHelper(arg0);
		SQLiteDatabase db = database.getWritableDatabase();
		Cursor c1 = db.rawQuery("select * from weekday3 where day=?",
				new String[] { String.valueOf(today) });
		if (c1.moveToFirst()) {

			dodo = c1.getInt((int) c1.getColumnIndex("do"));
		}
		if (dodo == 1) {

			Intent i = new Intent(arg0, AlarmalertActivity.class);

			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			arg0.startActivity(i);

		} else {
			Log.i("Receiver3", "not today");
		}

	}

}

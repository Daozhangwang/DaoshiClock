package com.example.listener;

import java.util.Calendar;

import com.example.receiver.AlarmReceiver;
import com.example.receiver.AlarmReceiver2;
import com.example.receiver.AlarmReceiver3;
import com.example.daoshiclock.DatabaseHelper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class Onlistener implements ToggleButton.OnCheckedChangeListener {
	public AlarmManager am;
	public DatabaseHelper database;
	public SQLiteDatabase db;
	public Calendar c;
	public Context getit;
	public Intent intent;
	public int i;
	public Cursor ctime;
	public int hour;
	public int minute;

	public Onlistener(Context getit, int i, DatabaseHelper database) {
		this.database = database;
		
		this.getit = getit;
		this.i = i;
		db = database.getWritableDatabase();

	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		if (arg1) {
			
			c = Calendar.getInstance();
			c.setTimeInMillis(System.currentTimeMillis());
			
			
			
			ctime = db.query("time", null, null, null, null, null, null);
			ctime.moveToFirst();
			
			
			ContentValues cv = new ContentValues();// 实例化ContentValues
			cv.put("time_on", true);// 添加要更改的字段及内容
			String whereClause = "time_number=?";
			String[] whereday = new String[] { String.valueOf(i) };
			db.update("time", cv, whereClause, whereday);
			if (i == 3) {
				intent = new Intent(getit, AlarmReceiver3.class);
				ctime.moveToLast();
			
			} else if (i == 2) {
				intent = new Intent(getit, AlarmReceiver2.class);
				ctime.moveToNext();
			
			} else if (i == 1) {
				intent = new Intent(getit, AlarmReceiver.class);
			

			}
			hour = ctime.getInt(ctime.getColumnIndex("time_hour"));
			minute = ctime.getInt(ctime.getColumnIndex("time_min"));
			c.set(Calendar.HOUR_OF_DAY, hour);
			c.set(Calendar.MINUTE, minute);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			
			PendingIntent sender = PendingIntent.getBroadcast(getit, 1, intent,PendingIntent.FLAG_UPDATE_CURRENT);
			
			am = (AlarmManager) getit.getSystemService(Context.ALARM_SERVICE);
			
			am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), sender);
			
			//am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),24 * 60 * 60 * 1000, sender);

		} else {

			if (i == 3) {
				intent = new Intent(getit, AlarmReceiver3.class);
			} else if (i == 2) {
				intent = new Intent(getit, AlarmReceiver2.class);
			} else if (i == 1) {
				intent = new Intent(getit, AlarmReceiver.class);

			}
			PendingIntent sender = PendingIntent.getBroadcast(getit, 0, intent,
					0);

			AlarmManager am = (AlarmManager) getit
					.getSystemService(Context.ALARM_SERVICE);
			am.cancel(sender);
			ContentValues cv = new ContentValues();// 实例化ContentValues
			cv.put("time_hour", 0);
			cv.put("time_min", 0);
			cv.put("time_on", false);// 添加要更改的字段及内容
			String whereClause = "time_number=?";
			String[] whereday = new String[] { String.valueOf(i) };
			db.update("time", cv, whereClause, whereday);

		}

	}

}

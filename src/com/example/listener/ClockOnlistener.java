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
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class ClockOnlistener implements ToggleButton.OnCheckedChangeListener {
	public AlarmManager am;
	public DatabaseHelper database;
	public SQLiteDatabase db;
	public Calendar c;
	public Calendar cnow;
	public Context context;
	public Intent intent;
	public int i;
	public Cursor ctime;
	public int hour;
	public int minute;
	public int addtime;

	public ClockOnlistener(Context context, int i, DatabaseHelper database) {
		this.database = database;
		this.context = context;
		this.i = i;
		db = database.getWritableDatabase();

	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		if (arg1) {

			c = Calendar.getInstance();
			c.setTimeInMillis(System.currentTimeMillis());
			cnow = Calendar.getInstance();
			cnow.setTimeInMillis(System.currentTimeMillis());

			ctime = db.query("time", null, null, null, null, null, null);
			ctime.moveToFirst();

			ContentValues cv = new ContentValues();// 实例化ContentValues
			cv.put("time_on", true);// 添加要更改的字段及内容
			String whereClause = "time_number=?";
			String[] whereday = new String[] { String.valueOf(i) };
			db.update("time", cv, whereClause, whereday);

			if (i == 3) {
				intent = new Intent(context, AlarmReceiver3.class);
				ctime.moveToLast();

			} else if (i == 2) {
				intent = new Intent(context, AlarmReceiver2.class);
				ctime.moveToNext();

			} else if (i == 1) {
				intent = new Intent(context, AlarmReceiver.class);

			}
			Log.i("On" + String.valueOf(i), "want start receiver");
			hour = ctime.getInt(ctime.getColumnIndex("time_hour"));
			minute = ctime.getInt(ctime.getColumnIndex("time_min"));
			c.set(Calendar.HOUR_OF_DAY, hour);
			c.set(Calendar.MINUTE, minute);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);

			PendingIntent sender = PendingIntent.getBroadcast(context, 1,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
			int hour_now = cnow.get(Calendar.HOUR_OF_DAY);
			int minute_now = cnow.get(Calendar.MINUTE);
			if (hour_now > hour || (hour_now == hour && minute_now >= minute)) {
				addtime = 24 * 60 * 60 * 1000;
				Log.i("add a day?", "yes");

			} else {

				addtime = 0;
				Log.i("add a day?", "no");
			}

			am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

			am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + addtime,
					sender);
			am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis()+addtime,
					24 * 60 * 60 * 1000, sender);

		} else {

			if (i == 3) {
				intent = new Intent(context, AlarmReceiver3.class);
			} else if (i == 2) {
				intent = new Intent(context, AlarmReceiver2.class);
			} else if (i == 1) {
				intent = new Intent(context, AlarmReceiver.class);

			}
			PendingIntent sender = PendingIntent.getBroadcast(context, 0,
					intent, 0);

			AlarmManager am = (AlarmManager) context
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

package com.example.daoshiclock;

import com.example.shared.myshared;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;
import android.widget.ToggleButton;

public class showClock {
	public TextView tv;
	public Cursor ctime;
	public Cursor cday;
	public Cursor cOn;
	public Cursor csong;
	public DatabaseHelper database;
	public int type;
	public SQLiteDatabase showdb;
	public String dayshow_s = "";
	public int numberofdo = 0;
	public String[] weekday = { "周末", "周一", "周二", "周三", "周四", "周五", "周六" };

	public showClock(DatabaseHelper database) {

		this.database = database;
		showdb = null;
		showdb = database.getWritableDatabase();

	}

	public void songshow(TextView tv, Context context) {
		myshared shared = new myshared(context);
		String ring = shared.getring();
		tv.setText(ring);

	}

	public void Onshow(ToggleButton OnButton, int i) {

		cOn = showdb.query("time", null, null, null, null, null, null);
		cOn.moveToFirst();
		if (i == 2) {
			cOn.moveToNext();

		} else if (i == 3) {

			cOn.moveToLast();
		}
		int showOn = cOn.getInt((int) cOn.getColumnIndex("time_on"));
		if (showOn == 1) {
			OnButton.setChecked(true);
		}

	}

	public void timeshow(TextView tv, int j) {
		ctime = showdb.query("time", null, null, null, null, null, null);
		ctime.moveToFirst();
		if (j == 2) {
			ctime.moveToNext();
		} else if (j == 3) {
			ctime.moveToLast();

		}
		int showOn = ctime.getInt((int) ctime.getColumnIndex("time_on"));
		int i_showhour = ctime.getInt(ctime.getColumnIndex("time_hour"));
		int i_showmin = ctime.getInt(ctime.getColumnIndex("time_min"));
		// Log.i("timeshowcrap", String.valueOf(i_showhour));
		// Log.i("timeshowcrap2", String.valueOf(i_showmin));
		if (showOn == 1) {

			String sshowhour = String.valueOf(i_showhour);
			String sshowmin = String.valueOf(i_showmin);

			if (i_showhour < 10) {

				sshowhour = "0" + sshowhour;

			}
			if (i_showmin < 10) {

				sshowmin = "0" + sshowmin;

			}

			tv.setText(sshowhour + ":" + sshowmin);

		}

	}

	public void dayshow(TextView tv, int i) {
		dayshow_s = "";
		ctime = showdb.query("time", null, null, null, null, null, null);
		ctime.moveToFirst();

		if (i == 1) {
			cday = showdb.query("weekday1", null, null, null, null, null, null);

		} else if (i == 2) {
			cday = showdb.query("weekday2", null, null, null, null, null, null);
			ctime.moveToNext();

		} else if (i == 3) {
			cday = showdb.query("weekday3", null, null, null, null, null, null);
			ctime.moveToLast();
		}
		cday.moveToFirst();
		int On = ctime.getInt((int) ctime.getColumnIndex("time_on"));
		int daykey = cday.getInt(cday.getColumnIndex("day"));
		int daydo = cday.getInt((int) cday.getColumnIndex("do"));
		// Log.i(String.valueOf(i)+"onornot", String.valueOf(On));
		// Log.i(String.valueOf(daykey), String.valueOf(daydo));
		if (daydo == 1) {
			numberofdo++;
			dayshow_s = dayshow_s + "周末";
		}
		while (cday.moveToNext()) {
			daykey = cday.getInt(cday.getColumnIndex("day"));
			daydo = cday.getInt((int) cday.getColumnIndex("do"));
			// Log.i(String.valueOf(daykey), String.valueOf(daydo));
			if (daydo == 1) {
				dayshow_s = dayshow_s + weekday[daykey];
				numberofdo++;

			}

		}
		Log.i("number" + String.valueOf(i), String.valueOf(numberofdo));
		int textsize = 28 - 3 * numberofdo;

		if (dayshow_s == "") {

			dayshow_s = "未设置";

		} else if (dayshow_s == "周末周一周二周三周四周五周六") {

			dayshow_s = "全周";

		}
		if (On == 1) {
			tv.setText(dayshow_s);
			tv.setTextSize(textsize);
		}

	}

}

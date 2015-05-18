package com.example.activity;

import java.util.Calendar;

import com.example.daoshiclock.DatabaseHelper;
import com.example.daoshiclock.R;
import com.example.listener.Onlistener;
import com.example.listener.daylistener;
import com.example.listener.timelistener;
import com.example.daoshiclock.showday;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Fragment_Clock extends Fragment {

	public TextView time1;
	public TextView time2;
	public TextView time3;
	public TextView day1;
	public TextView day2;
	public TextView day3;
	public ToggleButton On1;
	public ToggleButton On2;
	public ToggleButton On3;
	public TextView showsong;
	public String songname;
	public Calendar c2;
	public DatabaseHelper database;
	public Cursor cu1;
	public Cursor cu2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_clock, container, false);
		time1 = (TextView) v.findViewById(R.id.tv_showtime_1);
		time2 = (TextView) v.findViewById(R.id.tv_showtime_2);
		time3 = (TextView) v.findViewById(R.id.tv_showtime_3);
		day1 = (TextView) v.findViewById(R.id.tv_choseday_1);
		day2 = (TextView) v.findViewById(R.id.tv_choseday_2);
		day3 = (TextView) v.findViewById(R.id.tv_choseday_3);
		On1 = (ToggleButton) v.findViewById(R.id.bt_On_1);
		On2 = (ToggleButton) v.findViewById(R.id.bt_On_2);
		On3 = (ToggleButton) v.findViewById(R.id.bt_On_3);
		showsong = (TextView) v.findViewById(R.id.showsong);

		return v;

	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initdatabase();

		showday showthis = new showday(new DatabaseHelper(getActivity()));
		showthis.dayshow(day1, 1);
		showthis.dayshow(day2, 2);
		showthis.dayshow(day3, 3);
		showthis.timeshow(time1, 1);
		showthis.timeshow(time2, 2);
		showthis.timeshow(time3, 3);
		showthis.Onshow(On1, 1);
		showthis.Onshow(On2, 2);
		showthis.Onshow(On3, 3);
		showthis.songshow(showsong);
		inittime();
		initday();
		initOn();

	}

	private void initdatabase() {
		database = new DatabaseHelper(getActivity());
		SQLiteDatabase db1 = null;
		SQLiteDatabase db2 = null;
		SQLiteDatabase db3 = null;
		db1 = database.getWritableDatabase();
		db2 = database.getWritableDatabase();
		db3 = database.getWritableDatabase();
		Cursor cusong = db3.query("song", null, null, null, null, null, null);
		Cursor cuweek1 = db1.query("weekday1", null, null, null, null, null,
				null);
		Cursor cuweek2 = db1.query("weekday2", null, null, null, null, null,
				null);
		Cursor cuweek3 = db1.query("weekday3", null, null, null, null, null,
				null);
		cu2 = db2.query("time", null, null, null, null, null, null);
		Log.i("weekday_long", String.valueOf(cuweek1.getCount()));
		Log.i("time_long", String.valueOf(cu2.getCount()));
		if (cusong.getCount() == 0) {
			ContentValues cvsong = new ContentValues();
			cvsong.put("song_name", "it's my life");
			cvsong.put("number", 1);
			db3.insert("song", null, cvsong);
		}
		if (cu2.getCount() == 0) {
			ContentValues cv1 = new ContentValues();
			cv1.put("time_hour", 0);
			cv1.put("time_min", 0);
			cv1.put("time_number", 1);
			cv1.put("time_on", false);
			db2.insert("time", null, cv1);

			ContentValues cv2 = new ContentValues();
			cv2.put("time_hour", 0);
			cv2.put("time_min", 0);
			cv2.put("time_number", 2);
			cv2.put("time_on", false);
			db2.insert("time", null, cv2);

			ContentValues cv3 = new ContentValues();
			cv3.put("time_hour", 0);
			cv3.put("time_min", 0);
			cv3.put("time_number", 3);
			cv3.put("time_on", false);
			db2.insert("time", null, cv3);
		}

		if (cuweek3.getCount() == 0) {
			for (int i = 0; i <= 6; i++) {
				ContentValues cv = new ContentValues();
				cv.put("day", i);
				cv.put("do", false);
				db1.insert("weekday3", null, cv);
			}

		}

		if (cuweek1.getCount() == 0) {
			for (int i = 0; i <= 6; i++) {
				ContentValues cv = new ContentValues();
				cv.put("day", i);
				cv.put("do", false);
				db1.insert("weekday1", null, cv);
			}

		}
		if (cuweek2.getCount() == 0) {
			for (int i = 0; i <= 6; i++) {
				ContentValues cv = new ContentValues();
				cv.put("day", i);
				cv.put("do", false);
				db1.insert("weekday2", null, cv);
			}

		}

	}

	private void initOn() {

		Onlistener Onlistener3 = new Onlistener(getActivity(), 3,
				new DatabaseHelper(getActivity()));
		Onlistener Onlistener2 = new Onlistener(getActivity(), 2,
				new DatabaseHelper(getActivity()));
		Onlistener Onlistener1 = new Onlistener(getActivity(), 1,
				new DatabaseHelper(getActivity()));
		On1.setOnCheckedChangeListener(Onlistener1);
		On2.setOnCheckedChangeListener(Onlistener2);
		On3.setOnCheckedChangeListener(Onlistener3);

	}

	private void inittime() {

		timelistener time3listener = new timelistener(time3,
				new DatabaseHelper(getActivity()), 3);
		timelistener time2listener = new timelistener(time2,
				new DatabaseHelper(getActivity()), 2);
		timelistener time1listener = new timelistener(time1,
				new DatabaseHelper(getActivity()), 1);
		time3.setOnClickListener(time3listener);
		time2.setOnClickListener(time2listener);
		time1.setOnClickListener(time1listener);

	}

	private void initday() {
		c2 = Calendar.getInstance();

		int today = c2.get(Calendar.DAY_OF_WEEK) - 1;
		Log.i("today", String.valueOf(today));

		daylistener day1listener = new daylistener(day1, 1, new DatabaseHelper(
				getActivity()));
		daylistener day2listener = new daylistener(day2, 2, new DatabaseHelper(
				getActivity()));
		daylistener day3listener = new daylistener(day3, 3, new DatabaseHelper(
				getActivity()));

		day1.setOnClickListener(day1listener);
		day2.setOnClickListener(day2listener);
		day3.setOnClickListener(day3listener);

	}

	public void onResume() {
		super.onResume();
		Log.i("Clock", "Resume");
		Cursor csong;
		DatabaseHelper database = new DatabaseHelper(getActivity());
		SQLiteDatabase showdb = database.getWritableDatabase();
		csong = showdb.query("song", null, null, null, null, null, null);
		csong.moveToFirst();
		String bitch = csong.getString(csong.getColumnIndex("song_name"));

		CharSequence fuck = showsong.getText();
		String fucker = (String) fuck;
		if (fucker != bitch) {
			showsong.setText(bitch);

		}

	}

}
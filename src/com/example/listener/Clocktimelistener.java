package com.example.listener;

import java.util.Calendar;

import com.example.daoshiclock.DatabaseHelper;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

public class Clocktimelistener implements View.OnClickListener{
	public TextView tv;
	public DatabaseHelper database;
	public int i;
	public SQLiteDatabase db;
	public Calendar c;
    public Clocktimelistener(TextView tv,DatabaseHelper database,int i){
    	this.tv=tv;
    	this.database=database;
    	this.i=i;
    	db=database.getWritableDatabase();
    	c = Calendar.getInstance();
    	
    }
	@Override
	public void onClick(View v) {
		TimePickerDialog timePickerDialog = new TimePickerDialog(v
				.getContext(),
				new TimePickerDialog.OnTimeSetListener() {

					public void onTimeSet(TimePicker tp, int hourOfDay,
							int minute) {
						// TODO Auto-generated method stub
						String s1 = String.valueOf(hourOfDay);
						String s2 = String.valueOf(minute);
						

						ContentValues cvtime = new ContentValues();// 实例化ContentValues
						cvtime.put("time_hour", hourOfDay);
						cvtime.put("time_min", minute);// 添加要更改的字段及内容
						String whereClause = "time_number=?";
						String[] whereday = new String[] { String.valueOf(i) };
						db.update("time", cvtime, whereClause,
								whereday);

						if (minute < 10) {
							s2 = "0" + s2;

						}
						if (hourOfDay < 10) {
							s1 = "0" + s1;
						}

						tv.setText(s1 + ":" + s2);

					}
				}, c.get(Calendar.HOUR_OF_DAY), c
						.get(Calendar.MINUTE), true);
		timePickerDialog.show();
		
		
	}

}

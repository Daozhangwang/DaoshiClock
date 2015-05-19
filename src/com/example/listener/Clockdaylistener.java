package com.example.listener;

import com.example.daoshiclock.DatabaseHelper;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Clockdaylistener implements View.OnClickListener {

	public boolean[] weekdo = { false, false, false, false, false, false, false };
	public String[] weekday = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
	public TextView day;
	public int i;
	public int numberofdays = 0;
	public String showweekdo;

	public DatabaseHelper database;
	public SQLiteDatabase db;

	public Clockdaylistener(TextView day, int i, DatabaseHelper database) {
		this.day = day;
		this.i = i;
		this.database = database;
		db = database.getWritableDatabase();

	}

	public void onClick(View v) {
		Builder builder = new AlertDialog.Builder(v.getContext());
		builder.setTitle("选择闹钟周期：");
		builder.setMultiChoiceItems(weekday, weekdo,
				new OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1,
							boolean arg2) {
						weekdo[arg1] = arg2;

					}
				});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int arg1) {
				showweekdo = "";
				numberofdays = 0;
				for (int i = 0; i <= 6; i++) {

					if (weekdo[i]) {
						numberofdays++;
						showweekdo += weekday[i] + " ";

					}

				}

				for (int j = 0; j <= 6; j++) {

					Log.i(String.valueOf(j) + "now", String.valueOf(weekdo[j]));
					ContentValues cvday = new ContentValues();// 实例化ContentValues
					cvday.put("do", weekdo[j]);

					String whereClause = "day=?";
					String[] whereday = new String[] { String.valueOf(j) };
					if (i == 1) {
						db.update("weekday1", cvday, whereClause, whereday);
					} else if (i == 2) {
						db.update("weekday2", cvday, whereClause, whereday);

					} else if (i == 3) {
						db.update("weekday3", cvday, whereClause, whereday);

					}

				}

				day.setText(showweekdo);
				day.setTextSize(28 - 3 * numberofdays);
				if (numberofdays == 0) {
					day.setText("未选择");
					day.setTextSize(28);

				}
				if (numberofdays == 7) {
					day.setText("全周");
					day.setTextSize(28);

				}

			}

		});
		builder.create().show();

	}

}

package com.example.daoshiclock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "mydata2.db";
	private static final int version = 1; // 数据库名称

	// 数据库版本

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, version);

	}

	public void onCreate(SQLiteDatabase arg0) {
		String sql_week1 = "create table weekday1(day int not null,do boolean not null)";
		String sql_week2 = "create table weekday2(day int not null,do boolean not null)";
		String sql_week3 = "create table weekday3(day int not null,do boolean not null)";
		String sql_song = "create table song(song_name VARCHAR,number int not null)";
		String sql_user = "create table user(user_name VARCHAR,user_introduce VARCHAR,follower int not null,numberofsongs int not null,attention int not null)";
		String sql_time = "create table time(time_hour int not null,time_min int not null,time_number int not null,time_on boolean not null)";
		arg0.execSQL(sql_time);
		arg0.execSQL(sql_week1);
		arg0.execSQL(sql_week2);
		arg0.execSQL(sql_week3);
		arg0.execSQL(sql_song);
		arg0.execSQL(sql_user);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}

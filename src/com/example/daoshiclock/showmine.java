package com.example.daoshiclock;

import com.example.shared.myshared;

import android.content.Context;
import android.widget.TextView;

public class showmine {

	public TextView introduce;
	public TextView songs;
	public TextView attention;
	public TextView follower;
	public TextView username;
	public myshared s;

	public showmine(TextView username, TextView introduce, TextView songs,
			TextView attention, TextView follower, Context context) {
		this.username = username;
		this.introduce = introduce;
		this.follower = follower;
		this.attention = attention;
		this.songs = songs;
		this.s = new myshared(context);

	}

	public void show() {
		username.setText(s.getusername());
		introduce.setText(s.getintroduce());
		songs.setText(s.getsongs());
		follower.setText(s.getfans());
		attention.setText(s.getattention());

	}

}

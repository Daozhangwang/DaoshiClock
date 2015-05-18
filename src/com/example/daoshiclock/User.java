package com.example.daoshiclock;

import com.example.thread.UserThread;

public class User {
	public String username;
	public String introduce;
	public int sex;
	public String songs;
	public String attention;
	public String fans;

	public User(String username) {

		this.username = username;

	}

	public User() {

	}

	public void attention(String myname) {

	}

	public void getmessage(String myname) {
		UserThread ut = new UserThread(username, myname);
		ut.start();

	}

}

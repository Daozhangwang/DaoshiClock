package com.example.thread;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class zanThread extends Thread {
	public String username;
	public String musicname;

	public HttpClient httpClient;
	public String url;
	public HttpPost httpPost;
	public HttpEntity requestEntity;
	public int res;

	public zanThread(String username, String musicname) {
		this.username = username;
		this.musicname = musicname;
		httpClient = new DefaultHttpClient();

	}

	public void run() {

	}

}

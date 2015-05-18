package com.example.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.activity.UserActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class UserThread extends Thread {

	public String username;
	public String myname;
	public String url;
	public HttpClient httpClient;
	public HttpPost httpPost;
	public HttpEntity requestEntity;

	public String str_songs;
	public String str_attention;
	public String str_fans;
	public String str_introduce;
	public Boolean key;

	public String result;
	TextView introduce;
	TextView songs;
	TextView attention;
	TextView fans;
	Handler handler;

	// public UserThread(String username, TextView introduce, TextView songs,
	// TextView attention, TextView fans) {
	public UserThread(String username, String myname) {
		this.username = username;
		this.myname = myname;
		httpClient = new DefaultHttpClient();

	}

	public void run() {
		url = "http://1.daoshi58alarm.sinaapp.com/user/user_message.php";
		httpPost = new HttpPost(url);

		List<NameValuePair> user_pairs = new ArrayList<NameValuePair>();
		NameValuePair pair_name = new BasicNameValuePair("username", username);
		NameValuePair pair_myname = new BasicNameValuePair("myname", myname);

		user_pairs.add(pair_name);
		user_pairs.add(pair_myname);

		try {

			requestEntity = new UrlEncodedFormEntity(user_pairs);

			httpPost.setEntity(requestEntity);

			try {

				HttpResponse response = httpClient.execute(httpPost);

				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					result = EntityUtils.toString(entity);
					Log.i("User_res", result);
					JSONArray nameList = new JSONArray(result);
					JSONObject oj = nameList.getJSONObject(0);

					str_fans = oj.getString("fans");
					str_attention = oj.getString("attention");
					str_songs = oj.getString("songs");
					str_introduce = oj.getString("introduce");

					if (oj.getInt("key") == 0) {

						key = false;

					} else {

						key = true;
					}

					Log.i("user_json",
							str_fans + " " + str_attention + " " + str_songs
									+ " " + str_introduce + String.valueOf(key));
					/*
					 * introduce.post(new Runnable() {
					 * 
					 * @Override public void run() {
					 * introduce.setText(str_introduce); }
					 * 
					 * }); songs.post(new Runnable() {
					 * 
					 * @Override public void run() { songs.setText(str_songs); }
					 * 
					 * }); fans.post(new Runnable() {
					 * 
					 * @Override public void run() { fans.setText(str_fans); }
					 * 
					 * }); attention.post(new Runnable() {
					 * 
					 * @Override public void run() {
					 * attention.setText(str_attention); }
					 * 
					 * });
					 */

				}
			} catch (Exception e) {
				e.printStackTrace();

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		Message msg = new Message();
		msg.what = 1;
		Bundle bundle = new Bundle();
		bundle.putString("introduce", str_introduce);
		bundle.putString("fans", str_fans);
		bundle.putString("attention", str_attention);
		bundle.putString("songs", str_songs);
		bundle.putBoolean("key", key);
		msg.setData(bundle);
		UserActivity.handler.sendMessage(msg);

	}

}

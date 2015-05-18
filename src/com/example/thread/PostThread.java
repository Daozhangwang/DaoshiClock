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

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class PostThread extends Thread {
	public String username;
	public String password;
	public String url;
	public HttpClient httpClient;
	public HttpPost httpPost;
	public HttpEntity requestEntity;
	public int key;
	public Context context;
    public Handler handler;
    
	public PostThread(String username, String password,Handler handler) {
		this.handler=handler;
		this.username = username;
		this.password = password;
		url = "";
		httpClient = new DefaultHttpClient();

	}

	public void run() {

		url = "http://1.daoshi58alarm.sinaapp.com/user/Login.php";
		httpPost = new HttpPost(url);

		List<NameValuePair> pairs_login = new ArrayList<NameValuePair>();
		NameValuePair pair_name = new BasicNameValuePair("username", username);
		NameValuePair pair_pwd = new BasicNameValuePair("password", password);

		pairs_login.add(pair_name);
		pairs_login.add(pair_pwd);

		try {

			requestEntity = new UrlEncodedFormEntity(pairs_login);

			httpPost.setEntity(requestEntity);

			try {

				HttpResponse response = httpClient.execute(httpPost);

				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					String result = EntityUtils.toString(entity);
				    Log.i("Login return what", result);
					if (result.equals("cool")) {
						 key = 1;
						
						

					} else {
						key = 0;

					}

				}
			} catch (Exception e) {
				e.printStackTrace();

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		
		Message message = Message.obtain(handler, key);
		handler.sendMessage(message);
	}

	

}

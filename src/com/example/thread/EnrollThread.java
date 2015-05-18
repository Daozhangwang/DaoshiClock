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

public class EnrollThread extends Thread {
	public String username;
	public String password;
	public int sex;
	public int age;
	
	public String url;
	public HttpClient httpClient;
	public HttpPost httpPost;
	public HttpEntity requestEntity;
	public int key;
	public Context context;
    public Handler handler;
    
	public EnrollThread(String username, String password,int sex,int age,Handler handler) {
		this.handler=handler;
		this.username = username;
		this.password = password;
		this.age=age;
		this.sex=sex;
		url = "";
		httpClient = new DefaultHttpClient();

	}

	public void run() {

		url = "http://1.daoshi58alarm.sinaapp.com/user/Enroll.php";
		httpPost = new HttpPost(url);

		List<NameValuePair> pairs_enroll = new ArrayList<NameValuePair>();
		NameValuePair pair_name = new BasicNameValuePair("username", username);
		NameValuePair pair_pwd = new BasicNameValuePair("password", password);
		NameValuePair pair_sex = new BasicNameValuePair("sex", String.valueOf(sex));
		NameValuePair pair_age = new BasicNameValuePair("age", String.valueOf(age));

		pairs_enroll.add(pair_name);
		pairs_enroll.add(pair_pwd);
		pairs_enroll.add(pair_sex);
		pairs_enroll.add(pair_age);

		try {

			requestEntity = new UrlEncodedFormEntity(pairs_enroll);

			httpPost.setEntity(requestEntity);

			try {

				HttpResponse response = httpClient.execute(httpPost);

				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					String result = EntityUtils.toString(entity);
					Log.i("return what", result);
					if (result.equals("1")) {
						 key = 1;
						
					} else {
						key = 2;

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

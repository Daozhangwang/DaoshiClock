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

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class introducethread extends Thread {
	public String username;
	public String introduce;
	private String url;
	public HttpClient httpClient;
	public HttpPost httpPost;
	public HttpEntity requestEntity;
	public Handler handler;
	public int key;

	public introducethread(String username, String introduce,Handler handler) {

		this.username = username;
		this.introduce = introduce;
		this.handler=handler;
		url = "";
		httpClient = new DefaultHttpClient();

	}

	public void run() {
		url = "http://1.daoshi58alarm.sinaapp.com/user/introduce.php";
		httpPost = new HttpPost(url);

		List<NameValuePair> pairs_intr = new ArrayList<NameValuePair>();
		NameValuePair pair_name = new BasicNameValuePair("username", username);
		NameValuePair pair_intr = new BasicNameValuePair("introduce", introduce);

		pairs_intr.add(pair_name);
		pairs_intr.add(pair_intr);

		try {

			requestEntity = new UrlEncodedFormEntity(pairs_intr);

			httpPost.setEntity(requestEntity);

			try {

				HttpResponse response = httpClient.execute(httpPost);

				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					String result = EntityUtils.toString(entity);
					Log.i("introduce return what", result);
					if (result.equals("1")) {
						key=1;

					} else {
                        key=2;
					}

				}
			} catch (Exception e) {
				e.printStackTrace();

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		httpClient.getConnectionManager().shutdown();
		Message message = Message.obtain(handler, key);
		handler.sendMessage(message);
		
	}

}

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

import com.example.activity.UserActivity;

import android.os.Message;
import android.util.Log;

public class attentionThread extends Thread {
	public String username;
	public String myname;
	public HttpClient httpClient;
	public String url;
	public HttpPost httpPost;
	public HttpEntity requestEntity;
	public int res;

	public attentionThread(String myname, String username) {
		this.username = username;
		this.myname = myname;
		httpClient = new DefaultHttpClient();

	}

	public void run() {
		url = "http://1.daoshi58alarm.sinaapp.com/user/attention.php";
		httpPost = new HttpPost(url);
		List<NameValuePair> user_pairs = new ArrayList<NameValuePair>();
		NameValuePair pair_username = new BasicNameValuePair("myname", myname);
		NameValuePair pair_myname = new BasicNameValuePair("username", username);

		user_pairs.add(pair_username);
		user_pairs.add(pair_myname);

		try {

			requestEntity = new UrlEncodedFormEntity(user_pairs);

			httpPost.setEntity(requestEntity);

			try {

				HttpResponse response = httpClient.execute(httpPost);

				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					String result = EntityUtils.toString(entity);
					Log.i("Attention_res", result);
					if (result.equals("cutcool") || result.equals("addcool")) {

						res = 1;
					} else {

						res = 2;
					}

				}
			} catch (Exception e) {
				e.printStackTrace();

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		httpClient.getConnectionManager().shutdown();
		Message msg = new Message();
		msg.what = res;
		UserActivity.mhandler.sendMessage(msg);

	}

}

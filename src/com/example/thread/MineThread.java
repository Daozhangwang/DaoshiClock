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

import com.example.shared.myshared;
import android.content.Context;
import android.util.Log;

public class MineThread extends Thread {
	public String username;
	public Context context;
	public String url;
	public HttpClient httpClient;
	public HttpPost httpPost;
	public HttpEntity requestEntity;

	private String attention;
	private String fans;
	private String songs;
	private String introduce;

	public MineThread(String username, Context context) {
		this.username = username;
		this.context = context;

		httpClient = new DefaultHttpClient();

	}

	public void run() {
		url = "http://1.daoshi58alarm.sinaapp.com/user/mine_message.php";
		httpPost = new HttpPost(url);

		List<NameValuePair> pairs_login = new ArrayList<NameValuePair>();
		NameValuePair pair_name = new BasicNameValuePair("username", username);

		pairs_login.add(pair_name);

		try {

			requestEntity = new UrlEncodedFormEntity(pairs_login);

			httpPost.setEntity(requestEntity);

			try {

				HttpResponse response = httpClient.execute(httpPost);

				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					String result = EntityUtils.toString(entity);
					Log.i("Mine_res", result);
					JSONArray nameList = new JSONArray(result);

					JSONObject oj = nameList.getJSONObject(0);

					oj.getString("sex");
					fans = oj.getString("fans");
					attention = oj.getString("attention");
					songs = oj.getString("songs");
					introduce = oj.getString("introduce");
					myshared s1 = new myshared(context);

					s1.setintroduce(introduce);
					s1.setattention(attention);
					s1.setfans(fans);
					s1.setsongs(songs);

					Log.d("Mine_Message", introduce + " " + fans + " "
							+ attention + " " + songs);

				}
			} catch (Exception e) {
				e.printStackTrace();

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}

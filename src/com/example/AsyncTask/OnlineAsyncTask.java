package com.example.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.json.JSONException;
import org.json.JSONObject;

import com.example.daoshiclock.R;
import com.example.daoshiclock.setlist;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class OnlineAsyncTask extends AsyncTask<String, Void, String> {
	public Context context;
	public ListView onlinelist;

	public static SimpleAdapter onlinelistAdapter;
	private ArrayList<HashMap<String, Object>> onlinelistitem;
	private JSONArray onlinelist_array;

	private String music_name;
	private String upper;
	private int likernum;
	private int isliker;
	private String uptime;

	private HttpClient httpClient;
	private HttpPost httpPost;
	private HttpEntity requestEntity;

	public OnlineAsyncTask(ListView onlinelist, Context context) {
		this.context = context;
		this.onlinelist = onlinelist;
		onlinelistitem = new ArrayList<HashMap<String, Object>>();
	}

	protected void onPreExecute() {
		onlinelist.setVisibility(View.GONE);

	}

	@Override
	protected String doInBackground(String... params) {
		String username = params[0];
		String result = "";
		String url = "http://1.daoshi58alarm.sinaapp.com/user/online.php";
		httpPost = new HttpPost(url);
		httpClient = new DefaultHttpClient();

		List<NameValuePair> pairs_uplist = new ArrayList<NameValuePair>();
		NameValuePair pair_name = new BasicNameValuePair("username", username);

		pairs_uplist.add(pair_name);

		try {

			requestEntity = new UrlEncodedFormEntity(pairs_uplist);

			httpPost.setEntity(requestEntity);

			try {

				HttpResponse response = httpClient.execute(httpPost);

				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					result = EntityUtils.toString(entity);
					Log.i("onlinelist_return", result);

				}
			} catch (Exception e) {
				e.printStackTrace();

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return result;
	}

	@Override
	protected void onPostExecute(String result) {

		onlinelist.setVisibility(View.VISIBLE);

		try {
			onlinelist_array = new JSONArray(result);
			for (int i = 0; i < onlinelist_array.length(); i++) {
				JSONObject oj = onlinelist_array.getJSONObject(i);
				music_name = oj.getString("music_name");
				likernum = oj.getInt("likernum");
				isliker = oj.getInt("isliker");
				uptime = oj.getString("uptime");
				upper = oj.getString("upper");

				music_name = music_name.substring(upper.length(),
						music_name.length() - 4);
				uptime = uptime.substring(5, uptime.length());
				uptime = "上传时间:" + uptime;
				HashMap<String, Object> map = new HashMap<String, Object>();

				map.put("music_name", music_name);
				map.put("likernum", likernum);
				if (isliker == 1) {
					map.put("isliker", R.drawable.azan);

				} else {
					map.put("isliker", R.drawable.zan);

				}
				// map.put("isliker", isliker);
				map.put("upper", upper);
				map.put("uptime", uptime);
				onlinelistitem.add(map);
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}

		onlinelistAdapter = new SimpleAdapter(context, onlinelistitem,// 数据源
				R.layout.one_online, new String[] { "music_name", "likernum",
						"upper", "uptime", "isliker" }, new int[] {
						R.id.online_musicname, R.id.online_likenum,
						R.id.online_upper, R.id.online_uptime,
						R.id.online_isliker_img });
		onlinelist.setAdapter(onlinelistAdapter);

		setlist setollist = new setlist(onlinelist, context);
		setollist.onlineclicker();

	}

	@Override
	protected void onCancelled() {

	}

}

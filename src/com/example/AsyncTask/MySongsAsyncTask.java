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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MySongsAsyncTask extends AsyncTask<String, Integer, String> {

	public ListView uplist;
	public Context context;

	private ProgressDialog progress;
	public static SimpleAdapter uplistAdapter;
	private ArrayList<HashMap<String, Object>> uplistitem;
	private JSONArray uplist_array;
	private HttpClient httpClient;
	private HttpPost httpPost;
	private HttpEntity requestEntity;
	public String username;

	public MySongsAsyncTask(Context context, ListView uplist) {

		this.uplist = uplist;
		this.context = context;
		uplistitem = new ArrayList<HashMap<String, Object>>();

	}

	@Override
	protected void onPreExecute() {
		uplist.setVisibility(View.GONE);
		progress = new ProgressDialog(context);
		progress.setMessage("正在加载列表");
		progress.setTitle("请稍候...");
		progress.show();

	}

	@Override
	protected String doInBackground(String... params) {
		String username = params[0];
		this.username = username;
		Log.i("username in AsyncTask", username);
		String result = "";
		String url = "http://1.daoshi58alarm.sinaapp.com/user/uploadlist.php";
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
					Log.i("uplist_return", result);

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

		progress.dismiss();
		uplist.setVisibility(View.VISIBLE);

		try {
			uplist_array = new JSONArray(result);
			for (int i = 0; i < uplist_array.length(); i++) {
				JSONObject oj = uplist_array.getJSONObject(i);
				String name = oj.getString("name");
				//String uploadtime = oj.getString("date");
				String liker = "被喜欢的次数：" + oj.getString("liker");
				name = name.substring(0, name.length() - 4);
				name = name.substring(username.length(), name.length());

				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("name", name);
				map.put("liker", liker);
				uplistitem.add(map);
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}

		uplistAdapter = new SimpleAdapter(context, uplistitem,// 数据源
				R.layout.one_upload, new String[] { "name", "liker" },

				new int[] { R.id.uplist_name, R.id.uplist_liker });
		uplist.setAdapter(uplistAdapter);

	}

	@Override
	protected void onCancelled() {
		progress.dismiss();

	}
}

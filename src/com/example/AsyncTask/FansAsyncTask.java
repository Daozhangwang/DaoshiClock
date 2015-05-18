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

public class FansAsyncTask extends
		AsyncTask<HashMap<String, String>, Void, String> {
	public String username;
	public String result;
	public Context context;
	public ListView fanslist;
	private ProgressDialog progress;
	private String str_type;
	public static SimpleAdapter fanslistAdapter;
	private ArrayList<HashMap<String, Object>> fanslistitem;
	private JSONArray fanslist_array;
	private HttpClient httpClient;
	private HttpPost httpPost;
	private HttpEntity requestEntity;

	public FansAsyncTask(Context context, ListView fanslist) {
		this.fanslist = fanslist;
		this.context = context;

		fanslistitem = new ArrayList<HashMap<String, Object>>();

	}

	protected void onPreExecute() {
		fanslist.setVisibility(View.GONE);
		progress = new ProgressDialog(context);
		progress.setMessage("正在加载列表");
		progress.setTitle("请稍候...");
		progress.show();

	}

	protected String doInBackground(HashMap<String, String>... params) {
		String type = params[0].get("type");
		String username = params[0].get("name");
		this.username = username;
		String result = "";
		String url = "http://1.daoshi58alarm.sinaapp.com/user/fanslist.php";
		httpPost = new HttpPost(url);
		httpClient = new DefaultHttpClient();
		List<NameValuePair> pairs_fans = new ArrayList<NameValuePair>();
		NameValuePair pair_name = new BasicNameValuePair("username", username);
		NameValuePair pair_type = new BasicNameValuePair("type", type);

		pairs_fans.add(pair_name);
		pairs_fans.add(pair_type);

		try {

			requestEntity = new UrlEncodedFormEntity(pairs_fans);

			httpPost.setEntity(requestEntity);

			try {

				HttpResponse response = httpClient.execute(httpPost);

				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					result = EntityUtils.toString(entity);
					Log.i("fanslist return what", result);

				}
			} catch (Exception e) {
				e.printStackTrace();

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return result;
	}

	protected void onPostExecute(String result) {

		progress.dismiss();
		fanslist.setVisibility(View.VISIBLE);

		try {
			fanslist_array = new JSONArray(result);
			for (int i = 0; i < fanslist_array.length(); i++) {
				JSONObject oj = fanslist_array.getJSONObject(i);
				String name = oj.getString("name");
				String key = oj.getString("key");
				if (key.equals("2")) {

					str_type = "互相关注";

				} else if (key.equals("1")) {

					str_type = "已关注";

				} else {

					str_type = "未关注";

				}
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("name", name);
				map.put("type", str_type);
				fanslistitem.add(map);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fanslistAdapter = new SimpleAdapter(context, fanslistitem,// 数据源
				R.layout.one_fans, new String[] { "name", "type" },

				new int[] { R.id.fanslist_name, R.id.fanslist_type });
		fanslist.setAdapter(fanslistAdapter);

	}

	@Override
	protected void onCancelled() {
		progress.dismiss();

	}

}

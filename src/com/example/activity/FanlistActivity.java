package com.example.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import com.example.AsyncTask.FansAsyncTask;
import com.example.daoshiclock.R;
import com.example.daoshiclock.setlist;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.Activity;

public class FanlistActivity extends Activity {
	public ArrayList<HashMap<String, Object>> fanslistitem;
	public String username;
	public String type;
	public TextView fanslist_title;
	public ImageView back;
	public ListView fanslist;
	public static SimpleAdapter fanslistAdapter;
	public static Handler handler;
	public int key = 1;
	public JSONArray fanslist_array;
	public String str_type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fanlist);
		initview();
		getdata();
		initevent();
	}

	private void initevent() {

		setlist setuplist = new setlist(fanslist, this);
		setuplist.fansclicker();
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}

		});

	}

	private void initview() {
		fanslistitem = new ArrayList<HashMap<String, Object>>();
		back = (ImageView) findViewById(R.id.fanslist_back);
		fanslist = (ListView) findViewById(R.id.fanslist);
		fanslist_title = (TextView) findViewById(R.id.fanslist_title);

	}

	@SuppressWarnings("unchecked")
	private void getdata() {

		Bundle bundle = this.getIntent().getExtras();
		username = bundle.getString("username");
		type = bundle.getString("type");
		if (type.equals("fans")) {

			key = 2;
			fanslist_title.setText(username+"听众");

		} else {

			key = 1;
			fanslist_title.setText(username+"关注的人");
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", username);
		map.put("type", String.valueOf(key));
		FansAsyncTask mTask = new FansAsyncTask(this, fanslist);
		mTask.execute(map);

	}
}

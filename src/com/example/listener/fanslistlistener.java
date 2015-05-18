package com.example.listener;

import java.util.Map;

import com.example.AsyncTask.FansAsyncTask;
import com.example.activity.UserActivity;
import com.example.daoshiclock.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class fanslistlistener implements OnItemClickListener {
	public TextView username;
	public String name;
	public Context context;

	public fanslistlistener(Context context) {

		this.context = context;

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		username = (TextView) arg1.findViewById(R.id.fanslist_name);
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) FansAsyncTask.fanslistAdapter
				.getItem(arg2);
		name = map.get("name");

		username.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("username", name);
				intent.putExtras(bundle);
				intent.setClass(context, UserActivity.class);
				context.startActivity(intent);
			}
		});
	}

}

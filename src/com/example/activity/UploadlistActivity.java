package com.example.activity;

import com.example.AsyncTask.MySongsAsyncTask;
import com.example.daoshiclock.R;
import com.example.daoshiclock.setlist;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.app.Activity;

public class UploadlistActivity extends Activity {
	public ListView uplist;
	public ImageView back;

	public String username;

	public MySongsAsyncTask mTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_uploadlist);
		initview();
		getdata();
		initevent();

	}

	private void getdata() {
		Bundle bundle = this.getIntent().getExtras();
		username = bundle.getString("username");
		MySongsAsyncTask mTask = new MySongsAsyncTask(this, uplist);
		mTask.execute(username);

	}

	private void initevent() {

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		setlist setuplist = new setlist(uplist, this);
		setuplist.upclicker();

	}

	private void initview() {
		back = (ImageView) findViewById(R.id.uploadlist_back);
		uplist = (ListView) findViewById(R.id.uploadlist);

	}
}

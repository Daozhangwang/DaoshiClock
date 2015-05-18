package com.example.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.daoshiclock.DatabaseHelper;
import com.example.activity.InputActivity;
import com.example.daoshiclock.IsLogin;
import com.example.daoshiclock.R;
import com.example.daoshiclock.showmine;
import com.example.thread.MineThread;

public class Fragment_mine extends Fragment {
	public TextView username;
	public TextView introduce;
	public TextView introduce2;
	public TextView songs;
	public TextView follower;
	public TextView attention;
	public Button quit;
	public DatabaseHelper database;
	public Cursor cu;
	public IsLogin islogin;
	String name;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_mine, container, false);
		username = (TextView) v.findViewById(R.id.username);
		introduce = (TextView) v.findViewById(R.id.introduce);
		introduce2 = (TextView) v.findViewById(R.id.introduce2);
		songs = (TextView) v.findViewById(R.id.numberofupload);
		follower = (TextView) v.findViewById(R.id.follower);
		attention = (TextView) v.findViewById(R.id.attention);
		quit = (Button) v.findViewById(R.id.quit);
		return v;

	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		updateshared();
		showmine showmine = new showmine(introduce, songs, attention, follower,
				getActivity());
		showmine.show();
		initevent();

	}

	private void initevent() {
		follower.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("type", "fans");
				bundle.putString("username", name);
				intent.putExtras(bundle);
				intent.setClass(getActivity(), FanlistActivity.class);
				startActivity(intent);

			}
		});
		attention.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("type", "star");
				bundle.putString("username", name);
				intent.putExtras(bundle);
				intent.setClass(getActivity(), FanlistActivity.class);
				startActivity(intent);

			}
		});
		quit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				islogin.setlogin(null);
				Intent intent = new Intent();
				intent.setClass(getActivity(), LoginActivity.class);
				startActivity(intent);
				getActivity().finish();

			}
		});
		songs.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("username", name);
				intent.putExtras(bundle);
				intent.setClass(getActivity(), UploadlistActivity.class);
				startActivity(intent);
			}

		});
		introduce2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), InputActivity.class);
				startActivity(intent);

			}
		});
		introduce.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), InputActivity.class);
				startActivity(intent);
			}
		});

	}

	private void updateshared() {
		islogin = (IsLogin) getActivity().getApplication();
		name = ((MainActivity) getActivity()).getusername();

		MineThread mine_th = new MineThread(name, getActivity());
		mine_th.start();
		username.setText(name);

	}

	public void onResume() {
		super.onResume();
		Log.i("mine", "Resume");
		showmine showmine = new showmine(introduce, songs, attention, follower,
				getActivity());
		showmine.show();
	}

}

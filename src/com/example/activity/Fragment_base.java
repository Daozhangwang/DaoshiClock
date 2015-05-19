package com.example.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.daoshiclock.DatabaseHelper;
import com.example.daoshiclock.R;
import com.example.daoshiclock.setmusicname;
import com.example.shared.myshared;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Fragment_base extends Fragment {
	public ListView baselist;
	public List<String> musiclist;
	public HashMap<String, Object> map;
	public static SimpleAdapter musiclist_Adapter;
	public SQLiteDatabase db;
	public DatabaseHelper database;
	public ViewPager mainpager;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_base, container, false);
		baselist = (ListView) v.findViewById(R.id.locatelist);
		musiclist = new ArrayList<String>();
		mainpager = ((MainActivity) getActivity()).getpager();
		return v;

	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		database = new DatabaseHelper(getActivity());
		db = database.getWritableDatabase();

	}

	private void initlistener() {

		baselist.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int arg2, long id) {
				// 定义AlertDialog.Builder对象，当长按列表项的时候弹出确认删除对话框
				AlertDialog.Builder builder = new Builder(getActivity());
				builder.setMessage("设为闹钟铃声?");
				builder.setTitle("提示");

				builder.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						@SuppressWarnings("unchecked")
						Map<String, String> map = (Map<String, String>) setmusicname.musiclist_Adapter
								.getItem(arg2);
						String ring = map.get("name");
						Log.i("LongClick...setring", ring);
						myshared shared = new myshared(getActivity());
						shared.setring(ring);

					}
				});

				builder.setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});

				builder.create().show();
				return false;
			}
		});

	}

	public void onStart() {
		super.onStart();
		setmusicname set = new setmusicname(
				((MainActivity) getActivity()).getusername(), getActivity(),
				baselist);
		set.setname();
		set.add();

		set.setadaper();
		set.clicker();

		initlistener();
		Log.i("base", "start");

	}

}

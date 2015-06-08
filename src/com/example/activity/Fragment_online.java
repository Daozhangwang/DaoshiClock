package com.example.activity;

import com.example.AsyncTask.OnlineAsyncTask;
import com.example.daoshiclock.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Fragment_online extends Fragment {
	public EditText edit_search;
	public Button btn_search;
	public ListView online_list;

	public String username;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_online, container, false);
		edit_search = (EditText) v.findViewById(R.id.edit_online_search);
		btn_search = (Button) v.findViewById(R.id.btn_online_search);
		online_list = (ListView) v.findViewById(R.id.online_list);
		return v;

	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getdata();
		

	}

	private void getdata() {
		username = ((MainActivity) getActivity()).getusername();
		OnlineAsyncTask mTask = new OnlineAsyncTask(online_list, getActivity());
		mTask.execute(username);

	}

}

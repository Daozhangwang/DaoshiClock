package com.example.activity;

import com.example.daoshiclock.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment_online extends Fragment {
	public String search_s;
	public Button search_bt;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_online, container, false);  
		search_bt=(Button)v.findViewById(R.id.online_search);
		
		return v;	
		
		 
		
	}	
	
	 public void onActivityCreated(Bundle savedInstanceState) {
		 super.onActivityCreated(savedInstanceState);
		
		 
		
	
     }
	

		

}

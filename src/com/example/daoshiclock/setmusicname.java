package com.example.daoshiclock;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.listener.musicOnItemClickListener;

import android.content.Context;
import android.os.Environment;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class setmusicname {
	public List<String> namelist;
	public HashMap<String, Object> map;
	public ArrayList<HashMap<String, Object>> musicitem;
	public Context context;
	public static SimpleAdapter musiclist_Adapter;
	public ListView baselist;
    public String username;
	public setmusicname(String username,Context context, ListView baselist) {
	    this.username=username;
		this.context = context;
		this.baselist = baselist;
		this.namelist = new ArrayList<String>();
		this.musicitem = new ArrayList<HashMap<String, Object>>();

	}

	public void setname() {
		File root = Environment.getExternalStorageDirectory();
		File path = new File(root.getPath() + "/Clock");
		File[] files = path.listFiles();
		if (files != null) {// ���ж�Ŀ¼�Ƿ�Ϊ�գ�����ᱨ��ָ��
			for (File file : files) {
				String fileName = file.getName();
				namelist.add(fileName);

			}

		}

	}

	public void clear() {

		namelist.clear();

	}

	public void add() {

		for (int i = 0; i < namelist.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("name", namelist.get(i));
			musicitem.add(map);

		}
      
	}

	public void setadaper() {
		musiclist_Adapter = new SimpleAdapter(context, musicitem,
				R.layout.one_music,
				// ��̬������ImageItem��Ӧ������
				new String[] { "name" },

				new int[] { R.id.musiclist_name });

		baselist.setAdapter(musiclist_Adapter);

	}

	public void clicker() {
		baselist.setOnItemClickListener(new musicOnItemClickListener(username,context,
				baselist, namelist));

	}

}

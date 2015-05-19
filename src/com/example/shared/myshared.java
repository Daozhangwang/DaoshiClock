package com.example.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class myshared {

	public SharedPreferences pref;
	public Editor editor;

	public myshared(Context context) {

		pref = context.getSharedPreferences("mymessage", Context.MODE_PRIVATE);
		editor = pref.edit();
	}

	public void setring(String ring) {

		editor.putString("ring", ring);
		editor.commit();

	}

	public String getring() {

		return pref.getString("ring", "s.mp3");
	}

	public void setfans(String fans) {

		editor.putString("fans", fans);
		editor.commit();

	}

	public String getfans() {

		return pref.getString("fans", "0");

	}

	public void setsongs(String songs) {

		editor.putString("songs", songs);
		editor.commit();

	}

	public String getsongs() {

		return pref.getString("songs", "0");

	}

	public void setattention(String attention) {

		editor.putString("attention", attention);
		editor.commit();

	}

	public String getattention() {

		return pref.getString("attention", "0");

	}

	public void setintroduce(String introduce) {

		editor.putString("introduce", introduce);
		editor.commit();

	}

	public String getintroduce() {

		return pref.getString("introduce", "a big guy");

	}

}

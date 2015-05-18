package com.example.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class user_shared {

	public SharedPreferences pref;
	public Editor editor;

	public user_shared(Context context) {

		pref = context.getSharedPreferences("user_message",
				Context.MODE_PRIVATE);
		editor = pref.edit();
	}

	public void setfans(String fans) {

		editor.putString("fans", fans);
		editor.commit();

	}

	public void setsongs(String songs) {

		editor.putString("songs", songs);
		editor.commit();

	}

	public void setattention(String attention) {

		editor.putString("attention", attention);
		editor.commit();

	}

	public void setintroduce(String introduce) {

		editor.putString("introduce", introduce);
		editor.commit();

	}

	public String getsongs() {

		return pref.getString("songs", "0");

	}

	public String getfans() {

		return pref.getString("fans", "0");

	}

	public String getattention() {

		return pref.getString("attention", "0");

	}

	public String getintroduce() {

		return pref.getString("introduce", "a big guy");

	}

}

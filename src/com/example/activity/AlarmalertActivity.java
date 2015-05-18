package com.example.activity;



import com.example.daoshiclock.PlayMusicService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AlarmalertActivity extends Activity {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		Log.i("activity","cool");
		Intent mIntent = new Intent(AlarmalertActivity.this, PlayMusicService.class);
		startService(mIntent);
		// 闹钟显示画面
		new AlertDialog.Builder(AlarmalertActivity.this)
				.setTitle("设置的闹钟响了")
				.setMessage("设置的闹钟响了")
				.setPositiveButton("关闭闹钟",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent intent1 = new Intent(AlarmalertActivity.this,
										PlayMusicService.class);
								stopService(intent1);
								AlarmalertActivity.this.finish();
							}
						}).show();
	}
	
	
	
	
	
	
	
	
	

}

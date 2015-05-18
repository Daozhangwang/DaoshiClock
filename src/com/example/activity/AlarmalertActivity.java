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
		// ������ʾ����
		new AlertDialog.Builder(AlarmalertActivity.this)
				.setTitle("���õ���������")
				.setMessage("���õ���������")
				.setPositiveButton("�ر�����",
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

package com.example.receiver;



import java.util.Calendar;

import com.example.activity.AlarmalertActivity;
import com.example.daoshiclock.DatabaseHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
	
	 public Cursor cu1;
	 public int today;
	 public Calendar c;
	 
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		 Log.i("Receiver1","start");
		 c = Calendar.getInstance();  
		 today = c.get(Calendar.DAY_OF_WEEK)-1;
		 
		 DatabaseHelper  database = new DatabaseHelper(arg0);
         SQLiteDatabase db = database.getWritableDatabase();
         
         cu1 = db.query("weekday1",null,null,null,null,null,null);
         cu1.moveToFirst();
 		 int key = cu1.getInt(cu1.getColumnIndex("day")); 
 		 int dodo=cu1.getInt((int)cu1.getColumnIndex("do")); 
 		
 		 if(key==today&&dodo==1){
         	Log.i("ring",String.valueOf(key)+"start");
            Intent i = new Intent(arg0, AlarmalertActivity.class);
     		
     		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
     		
     		arg0.startActivity(i);
         	
         }
         while (cu1.moveToNext()) {  
              key = cu1.getInt(cu1.getColumnIndex("day")); 
              dodo=cu1.getInt((int)cu1.getColumnIndex("do")); 
            //  Log.i("day",String.valueOf(key));
           
             if(key==today&&dodo==1){
            	 Log.i("naozhong",String.valueOf(key)+"start");
            	 Intent i = new Intent(arg0, AlarmalertActivity.class);
          		
          		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          		
          		arg0.startActivity(i);
             	
             }
         }       
         
         
         
            
     

	}

}

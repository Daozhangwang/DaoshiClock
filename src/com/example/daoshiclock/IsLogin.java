package com.example.daoshiclock;

import android.app.Application;

public class IsLogin extends Application {
	 private String username;  
     
	    public String getlogin(){  
	        return this.username;  
	    }  
	    public void setlogin(String c){  
	        this.username= c;  
	    }  
	    @Override  
	    public void onCreate(){  
	        username=null;  
	        super.onCreate();  
	    }  
}

package com.example.daoshiclock;


import com.example.activity.Fragment_Clock;
import com.example.activity.Fragment_base;
import com.example.activity.Fragment_mine;
import com.example.activity.Fragment_online;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter {

	

	    Fragment[]  frgs=new Fragment[4];  
	    public MainPagerAdapter(FragmentManager fm) {  
	        super(fm);  
	        frgs[0]=new Fragment_Clock();  
	        frgs[1]=new Fragment_base();  
	        frgs[2]=new Fragment_online();
	        frgs[3]=new Fragment_mine();  
	    }  

	    @Override  
	    public Fragment getItem(int position) {  
	        return frgs[position];  
	    }  

	    @Override  
	    public int getCount() {  
	        return 4;  
	    }  

}
